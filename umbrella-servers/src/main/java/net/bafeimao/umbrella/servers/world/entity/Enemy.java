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

package net.bafeimao.umbrella.servers.world.entity;

import com.google.common.base.MoreObjects;
import net.bafeimao.umbrella.annotation.Index;
import net.bafeimao.umbrella.annotation.SupportQuery;
import net.bafeimao.umbrella.servers.world.entity.converter.QualityConverter;
import net.bafeimao.umbrella.servers.world.entity.enums.Quality;
import net.bafeimao.umbrella.support.data.entity.DataEntity;
import net.bafeimao.umbrella.support.data.entity.ExcelMapping;
import net.bafeimao.umbrella.support.data.entity.ValueConverter;

/**
 * Created by bafeimao on 2015/10/28.
 */
@ExcelMapping(file = "enemy.xls", sheet = "enemy")
@SupportQuery
public class Enemy extends DataEntity<Long> {
    @Index
    protected String name;
    @Index
    protected int type;
    @Index
    protected int grade;
    @Index
    protected int hp;
    @Index
    @ValueConverter(QualityConverter.class)
    protected Quality quality;
//    @Index
//    protected List<Integer> skills;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("grade", grade)
                .add("name", name)
                .add("type", type)
                .add("quality", quality)
//                .add("skills", skills)
                .toString();
    }
}
