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

package net.bafeimao.umbrella.support.server;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/10.
 *
 * @author gukaitong
 * @since 1.0
 */
public class SessionStats {
    private long readBytes;
    private long writtenBytes;
    private long readMessages;
    private long writtenMessages;

    public long getReadBytes() {
        return readBytes;
    }

    public void setReadBytes(long readBytes) {
        this.readBytes = readBytes;
    }

    public long getReadMessages() {
        return readMessages;
    }

    public void setReadMessages(long readMessages) {
        this.readMessages = readMessages;
    }

    public long getWrittenBytes() {
        return writtenBytes;
    }

    public void setWrittenBytes(long writtenBytes) {
        this.writtenBytes = writtenBytes;
    }

    public long getWrittenMessages() {
        return writtenMessages;
    }

    public void setWrittenMessages(long writtenMessages) {
        this.writtenMessages = writtenMessages;
    }
}
