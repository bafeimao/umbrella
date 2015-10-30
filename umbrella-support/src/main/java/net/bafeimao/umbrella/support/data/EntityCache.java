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

import java.util.*;

/**
 * Created by bafeimao on 2015/10/28.
 */
public class EntityCache<T extends DataEntity<?>> {
    private Map<Class<?>, List<T>> entitiesMap = new HashMap<Class<?>, List<T>>();
    private EntityParser entityParser = new ExcelEntityParser();
    private String excelDir = "D:\\workspace\\ScriptData\\";

    public List<T> load(Class<T> entityClass) {
        List<T> entities = entitiesMap.get(entityClass);

        if (entities == null) {
            SheetMeta sheetMeta = entityClass.getAnnotation(SheetMeta.class);
            entities = entityParser.parse(entityClass, excelDir + sheetMeta.file(), sheetMeta.name());
        }

        return entities;
    }
}