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

package net.bafeimao.umbrella.servers.world.test;

import com.fasterxml.jackson.core.type.TypeReference;
import net.bafeimao.umbrella.support.util.JsonUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/11.
 *
 * @author gukaitong
 * @since 1.0
 */
public class MiscTests {

    @Test
    public void test12() {
        String json = "[[1000002,500,100],[1000003,10,101],[1000004,100,-1]]";

        List<List<Integer>> elements = JsonUtil.toBean(json, new TypeReference<List<List<Integer>>>() {
        });
        for (List<Integer> element : elements) {
            System.out.println(element);
        }

        List<List<Integer>> elements1 = JsonUtil.toBean(json, ArrayList.class);
        for (List<Integer> element : elements1) {
            System.out.println(element);
        }

        System.out.println(elements);
    }
}
