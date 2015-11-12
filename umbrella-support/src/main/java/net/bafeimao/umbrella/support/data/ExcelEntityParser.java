/*
 * Copyright 2002-2015 by bafeimao.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.bafeimao.umbrella.support.data;


import net.bafeimao.umbrella.support.util.JsonUtil;
import net.bafeimao.umbrella.support.util.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 从Excel中逐行读取数据并将每行都转化为相应的对象
 *
 * @author bafeimao
 */
public class ExcelEntityParser implements EntityParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelEntityParser.class);
    private Map<Class<?>, List<Field>> entityFieldsMap = new HashMap<Class<?>, List<Field>>();
    private Map<String, Map<String, Integer>> sheetColumnIndexesMap = new HashMap<String, Map<String, Integer>>();

    /**
     * 从指定路径的Excel文件中读取第一个Sheet并将每一行数据都转换为相应的对象实例,对象类型由参数clazz决定
     */
    @Override
    public <E extends DataEntity<?>> LinkedList<E> parse(Class<E> clazz, String fileName, String sheetName) {
        if (clazz == null || fileName == null)
            return null;

        long startTime = System.nanoTime();
        LinkedList<E> list = null;
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName); // 实例化一个FileInputStream用于读取Excel文件内容流
            Workbook wb = WorkbookFactory.create(inputStream); // 创建一个Workbook对象,用以操作excel文件中的sheet

            if (wb != null && wb.getNumberOfSheets() > 0) {
                Sheet sheet = wb.getSheet(sheetName);

                // 第一行默认是标题栏，所有不符合的都会被忽略
                if (sheet.getRow(0) != null) {
                    list = tryParse(clazz, sheet);
                }
            }
        } catch (Exception e) {
            LOGGER.error("{}", e);
        } finally {
            // 不管执行结束还是异常终止,最终都要关闭文件读取流对象
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error("{}", e);
            }
        }

        LOGGER.debug("[OK] 读取并解析Excel文件{}({} rows)共耗时:{}纳秒", fileName, list.size(), System.currentTimeMillis() - startTime);

        return list;
    }

    private <E extends DataEntity<?>> LinkedList<E> tryParse(Class<E> entityClass, Sheet sheet) {
        LinkedList<E> retList = new LinkedList<E>();

        try {
            List<Field> fields = this.getEntityFields(entityClass);
            int lastRowNum = sheet.getLastRowNum();

            for (int rowNum = 3; rowNum < lastRowNum; rowNum++) {
                int colNum;
                E instance = entityClass.newInstance();
                for (Field field : fields) {
                    try {
                        if ((colNum = getColumnIndex(sheet, field.getName())) != -1) {
                            field.setAccessible(true);
                            setFieldValue(instance, field, sheet.getRow(rowNum).getCell(colNum));
                        }
                    } catch (Exception e) {
                        LOGGER.error("{}", e);
                    }

                }
                retList.add(instance);
            }
        } catch (Exception e) {
            LOGGER.error("{}", e);
        }

        return retList;
    }

    private void setFieldValue(Object instance, Field field, Cell cell) throws IllegalAccessException {
        Class<?> fieldType = field.getType();
        Object cellValue = getCellValue(cell);
        String value = cellValue == null ? "" : cellValue.toString();

        LOGGER.debug("field:[{}, {}, {}]", field.getName(), fieldType.getSimpleName(), value);

        if (fieldType == Integer.class || fieldType == int.class) {
            field.set(instance, StringUtils.isEmpty(value) ? 0 : Double.valueOf(value).intValue());
        } else if (fieldType == Short.class || fieldType == short.class) {
            field.set(instance, StringUtils.isEmpty(value) ? 0 : Double.valueOf(value).shortValue());
        } else if (fieldType == Byte.class || fieldType == byte.class) {
            field.set(instance, StringUtils.isEmpty(value) ? 0 : Double.valueOf(value).byteValue());
        } else if (fieldType == Long.class || fieldType == long.class) {
            field.set(instance, StringUtils.isEmpty(value) ? 0 : Double.valueOf(value).longValue());
        } else if (fieldType == Float.class || fieldType == float.class) {
            field.set(instance, StringUtils.isEmpty(value) ? 0 : Double.valueOf(value).floatValue());
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            field.set(instance, StringUtils.isEmpty(value) ? false : Double.valueOf(value).intValue() == 1);
        } else if (fieldType == Date.class) {
            field.set(instance, StringUtils.isEmpty(value) ? null : DateTime.parse(value).toDate());
        } else if (fieldType == List.class) {
            field.set(instance, StringUtils.isEmpty(value) ? null : JsonUtil.toBean(value, ArrayList.class));
        } else {
            field.set(instance, value == null ? StringUtils.EMPTY : value.trim());
        }
    }

    private Object getCellValue(Cell cell) {
        if (cell == null) {
            throw new IllegalArgumentException("cell should not be null.");
        }

        Object retVal = null;

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_FORMULA:
                // Get the type of Formula
                switch (cell.getCachedFormulaResultType()) {
                    case Cell.CELL_TYPE_STRING:
                        retVal = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        retVal = cell.getNumericCellValue();
                        break;
                    default:
                }
                // retVal = formulaEval.evaluate(cell).formatAsString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell))
                    retVal = cell.getDateCellValue();
                else
                    retVal = cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                retVal = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_STRING:
                retVal = cell.getStringCellValue();
                break;
            default:
                retVal = null;
        }

        return retVal;
    }

    private List<Field> getEntityFields(Class<?> entityClass) {
        List<Field> retList = entityFieldsMap.get(entityClass);
        if (retList == null) {
            do {
                Field[] fields = entityClass.getDeclaredFields();
                for (Field field : fields) {
                    retList.add(field);
                }
            } while ((entityClass = entityClass.getSuperclass()) != null); // 到父类中查找
        }
        return retList;
    }

    private int getColumnIndex(Sheet sheet, String colName) {
        Map<String, Integer> columnIndexesMap = sheetColumnIndexesMap.get(sheet.getSheetName());

        if (columnIndexesMap == null) {
            columnIndexesMap = new HashMap<String, Integer>();
            Row titleRow = sheet.getRow(1);
            int colNum = titleRow.getLastCellNum();
            for (int i = titleRow.getFirstCellNum(); i < colNum; i++) {
                if (titleRow.getCell(i) != null) {
                    columnIndexesMap.put(titleRow.getCell(i).getStringCellValue(), i);
                }
            }
            sheetColumnIndexesMap.put(sheet.getSheetName(), columnIndexesMap);
        }

        Integer index = columnIndexesMap.get(colName);
        return index == null ? -1 : index;
    }
}
