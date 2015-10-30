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

package net.bafeimao.umbrella.support.test;

import net.bafeimao.umbrella.support.data.ExcelEntityParser;
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
