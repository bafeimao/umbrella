package net.bafeimao.umbrella.support.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.*;

/**
 * Created by Administrator on 2015/10/27.
 */
public class ExcelDataReader {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelDataReader.class);

    public Map<String, EntityDescriptor> descriptorsMap;
    private String entityPackage = HeroEntity.class.getPackage().getName();

    public String dataPath;

    public ExcelDataReader(String dataPath) {
        this.dataPath = dataPath;
    }

    public void read(Class<?> entityClass) {

    }

    public void read() {

        File file = new File("D:\\workspace\\ScriptData\\四魂之旅事件配置.xls");
        this.read(file);

//        File[] files = getExcelFiles();
//        for (File file : files) {
//            this.read(file);
//        }
    }

    private void ensureDescriptorsMap() {
        if (this.descriptorsMap == null) {
            this.descriptorsMap = new HashMap<String, EntityDescriptor>();

            initDescriptors();
        }
    }

    private void initDescriptors() {

    }

    private void read(File file) {

    }

    private File[] getExcelFiles() {
        File file = new File(dataPath);
        if (!file.exists()) {
            LOGGER.info("Directory is not exist.");
            return null;
        }

        return file.listFiles();
    }

    private void readSheet(String sheetName) {
    }

//    private boolean isModified(File file) {
//        ExcelFileDescriptor descriptor = descriptorsMap.get(file.getName());
//        return file.lastModified() > descriptor.getLastModified();
//    }
//
//    class ExcelSheetDescriptor {
//        private ExcelFileDescriptor fileDescriptor;
//
//        public ExcelSheetDescriptor(ExcelFileDescriptor fileDescriptor) {
//            this.fileDescriptor = fileDescriptor;
//        }
//
//        public ExcelFileDescriptor getFileDescriptor() {
//            return fileDescriptor;
//        }
//    }
//
//    class ExcelFileDescriptor {
//        private String name;
//        private Map<Class<?>, String> entitySheetMap = new HashMap<Class<?>, String>();
//        private long lastModified;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public long getLastModified() {
//            return lastModified;
//        }
//
//        public void setLastModified(long lastModified) {
//            this.lastModified = lastModified;
//        }
//    }


    class EntityDescriptor {
        private Class<?> entityClass;
        private String fileName;
        private long lastModified;
        private String sheetName;
    }

}
