package net.bafeimao.umbrella.support.test;

import net.bafeimao.umbrella.support.data.ExcelDataReader;
import net.bafeimao.umbrella.support.data.ExcelEntityParser;
import net.bafeimao.umbrella.support.data.HeroEntity;
import net.bafeimao.umbrella.support.data.SelectEvent;
import org.junit.Test;

import java.util.List;

/**
 * Created by Administrator on 2015/10/30.
 */
public class ExcelEntityParserTests {

    @Test
    public void test1() {
//        ExcelDataReader reader = new ExcelDataReader("D:\\workspace\\ScriptData");
//        reader.read();

        ExcelEntityParser entityParser = new ExcelEntityParser();
        List<SelectEvent> list = entityParser.parse(SelectEvent.class, "D:\\workspace\\ScriptData\\四魂之旅事件配置.xls", "选择事件");
        System.out.println(list);
    }
}
