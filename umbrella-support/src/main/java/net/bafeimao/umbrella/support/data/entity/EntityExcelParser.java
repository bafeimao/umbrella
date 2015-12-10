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

package net.bafeimao.umbrella.support.data.entity;

import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import net.bafeimao.umbrella.annotation.IgnoreMapping;
import net.bafeimao.umbrella.annotation.PrintExecutionTime;
import org.apache.poi.ss.usermodel.*;
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
public class EntityExcelParser extends AbstractEntityParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityExcelParser.class);
    private Map<String, Map<String, Integer>> sheetColumnIndexesMap = new HashMap<String, Map<String, Integer>>();

    /**
     * 从指定路径的Excel文件中读取第一个Sheet并将每一行数据都转换为相应的对象实例,对象类型由参数clazz决定
     * <p/>
     * TODO 实现对{@link PrintExecutionTime}处理的AOP逻辑，打印出方法执行时间
     */
    @Override
    @PrintExecutionTime
    public <E> LinkedList<E> parse(Class<E> entityClass) throws EntityParseException {
        LinkedList<E> entities = null;
        InputStream inputStream = null;

        try {
            ExcelMapping mapping = entityClass.getAnnotation(ExcelMapping.class);
            String fileName = "D:\\" + mapping.file();
            String sheetName = mapping.sheet();

            inputStream = new FileInputStream(fileName); // 实例化一个FileInputStream用于读取Excel文件内容流
            Workbook wb = WorkbookFactory.create(inputStream); // 创建一个Workbook对象,用以操作excel文件中的sheet

            if (wb != null && wb.getNumberOfSheets() > 0) {
                Sheet sheet = wb.getSheet(sheetName);

                // 第一行默认是标题栏，所有不符合的都会被忽略
                if (sheet.getRow(0) != null) {
                    entities = parse0(entityClass, sheet);
                }
            }
        } catch (Exception e) {
            LOGGER.error("{}", e);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                LOGGER.error("{}", e);
            }
        }

        return entities;
    }

    @Override
    public void registerConverter(Class<?> dataType, Converter<?, ?> converter) {
        convertersByType.put(dataType, converter);
    }

    private <E> LinkedList<E> parse0(Class<E> entityClass, Sheet sheet) throws EntityParseException {
        LinkedList<E> retList = new LinkedList<E>();

        try {
            // 数据行是从第三行开始的，第一行是列名(中文），第二行是列名（英文)
            for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
                LOGGER.info("Parsing {} with data row #{} ...", entityClass.getSimpleName(), rowNum + 1);

                E instance = entityClass.newInstance();
                for (Field field : getEntityFields(entityClass)) {
                    if (field.isAnnotationPresent(IgnoreMapping.class)) {
                        continue;
                    }

                    field.setAccessible(true);

                    int colNum = getColumnIndex(sheet, field.getName());
                    if (colNum != -1) {
                        Cell cell = sheet.getRow(rowNum).getCell(colNum);
                        if (cell != null) {
                            try {
                                String cellValue = this.getCellValue(cell);

                                LOGGER.debug("Assigning {}.{} = {} ({}))", entityClass.getSimpleName()
                                        , field.getName(), cellValue, field.getType().getSimpleName());

                                this.assignFieldValue(instance, field, cellValue);
                            } catch (IllegalAccessException e) {
                                LOGGER.error("{}", e);
                            } catch (IllegalArgumentException e) {
                                LOGGER.error("{}", e);
                            } catch (NullPointerException e) {
                                LOGGER.error("{}", e);
                            } catch (ExceptionInInitializerError e) {
                                LOGGER.error("{}", e);
                            } catch (Exception e) {
                                throw new EntityParseException("为字段：("
                                        + "name:" + field.getName()
//                                        + ", type:" + fieldType.getSimpleName()
//                                        + ", value:" + cellValue
                                        + ", entity:" + instance.getClass().getSimpleName() + ")设值时发生异常:" + e.getMessage(), e);
                            }
                        }
                    }
                }
                retList.add(instance);
            }
        } catch (Exception e) {
            LOGGER.error("{}", e);
        }

        return retList;
    }

    private void assignFieldValue(Object instance, Field field, String cellValue) throws Exception {
        Object fieldValue = null;

        if (field.getType().equals(String.class)) {
            fieldValue = cellValue;
        } else {
            Converter<String, ?> converter = getConverter(field);

            if (converter == null) {
                throw new Exception(String.format("No converter found for field : s%' with type :'s%'",
                        field.getName(), field.getType().getSimpleName()));
            }

            fieldValue = converter.convert(cellValue);
        }

        field.set(instance, fieldValue);
    }

    private String getCellValue(Cell cell) {
        Preconditions.checkNotNull("cell");

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
        return retVal == null ? null : retVal.toString();
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
