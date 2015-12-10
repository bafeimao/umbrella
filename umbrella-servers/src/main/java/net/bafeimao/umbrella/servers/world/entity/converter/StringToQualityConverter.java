/*
 * Copyright 2002-2015 by bafeimao.net, The umbrella Project
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

package net.bafeimao.umbrella.servers.world.entity.converter;

import com.google.common.base.Converter;
import com.google.common.primitives.Doubles;
import net.bafeimao.umbrella.servers.world.entity.enums.Quality;

import javax.annotation.Nullable;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/12/9.
 * <p/>
 * 将指定的字符串值转换为指定的枚举类型值
 * <p/>
 * 如果要转换的值时数值类型，那么就用值比较，反之，使用枚举名称比较（不区分大小写）
 *
 * @author gukaitong
 * @since 1.0
 */
public class StringToQualityConverter extends Converter<String, Quality> {
    @Override
    @Nullable
    protected Quality doForward(String s) {
        Double d = Doubles.tryParse(s);
        if (d != null) {
            int val = d.intValue();
            for (Quality element : Quality.values()) {
                if (element.getValue() == val) {
                    return element;
                }
            }
        } else {
            for (Quality element : Quality.values()) {
                if (s.equalsIgnoreCase(element.toString())) {
                    return element;
                }
            }
        }
        return null;
    }

    @Override
    protected String doBackward(Quality quality) {
        return quality == null ? null : quality.toString();
    }
}