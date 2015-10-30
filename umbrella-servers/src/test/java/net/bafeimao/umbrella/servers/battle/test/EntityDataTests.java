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

package net.bafeimao.umbrella.servers.battle.test;

import net.bafeimao.umbrella.servers.battle.entity.SelectEvent;
import net.bafeimao.umbrella.support.data.ExcelEntityParser;
import org.junit.Test;

import java.util.List;

/**
 * Created by bafeimao on 2015/10/30.
 *
 * @author bafeimao
 * @since 1.0
 */
public class EntityDataTests {

    @Test
    public void test1() {
        ExcelEntityParser entityParser = new ExcelEntityParser();
        List<SelectEvent> list = entityParser.parse(SelectEvent.class, "D:\\workspace\\ScriptData\\四魂之旅事件配置.xls", "选择事件");
        System.out.println(list);
    }
}
