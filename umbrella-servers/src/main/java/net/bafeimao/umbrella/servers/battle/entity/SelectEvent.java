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

package net.bafeimao.umbrella.servers.battle.entity;

import net.bafeimao.umbrella.support.data.DataEntity;

import java.util.List;

/**
 * Created by Administrator on 2015/10/28.
 */
public class SelectEvent extends DataEntity<Long> {
    private List option1;
    private int scope;

    public List<?> getOption1() {
        return option1;
    }

    public void setOption1(List<?> option1) {
        this.option1 = option1;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }
}
