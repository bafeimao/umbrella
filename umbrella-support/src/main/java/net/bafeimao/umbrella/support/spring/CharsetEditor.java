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

package net.bafeimao.umbrella.support.spring;

import java.beans.PropertyEditorSupport;
import java.nio.charset.Charset;

/**
 * Created by bafeimao on 2015/11/2.
 *
 * @author bafeimao
 * @since 1.0
 */
public class CharsetEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text != null && text != "") {
            this.setValue(Charset.forName(text));
        } else {
            this.setValue(Charset.forName("utf-8"));
        }
    }
}