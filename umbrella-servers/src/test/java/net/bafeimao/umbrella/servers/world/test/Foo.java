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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/30.
 *
 * @author gukaitong
 * @since 1.0
 */
public class Foo {

    List<Bar> numbers = new ArrayList<Bar>();

    public List<Bar> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Bar> numbers) {
        this.numbers = numbers;
    }

    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.setNumbers(Arrays.asList(new Bar(), new Bar()));

        for (int i = 0; i < foo.getNumbers().size(); i++) {
            Bar bar = foo.getNumbers().get(i);
            System.out.println("member:" + bar);
        }
    }

    static class Bar {
        public Bar() {}

        private Integer id = 0;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
