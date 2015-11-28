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

package net.bafeimao.umbrella.support.server;

import com.google.common.base.Preconditions;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * Created by bafeimao on 2015/11/2.
 *
 * @author bafeimao
 * @since 1.0
 */
public class ApplicationConfig extends PropertiesConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    private String configPath;

    public ApplicationConfig(String configPath) {
        Preconditions.checkNotNull("configPath is null.");

        // Loads configurations from specified file
        try {
            String fileName = ApplicationConfig.class.getResource(configPath).getFile();
            this.load(fileName);
        } catch (Exception e) {
            LOGGER.error("加载配置文件出错:{}", e);
        }
    }

    /**
     * Prints configurations
     */
    public void print() {
        LOGGER.info("Application Configurations:");

        for (Iterator<String> iterator = this.getKeys(); iterator.hasNext(); ) {
            String key = iterator.next();
            LOGGER.debug("{}：{}", key, this.getProperty(key));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        try {
            for (Iterator<String> iterator = this.getKeys(); iterator.hasNext(); ) {
                String key = iterator.next();
                sb.append(String.format("%s => %s\n", key, getProperty(key)));
            }
        } catch (Exception e) {
            LOGGER.error("{}", e);
        }

        return sb.toString();
    }
}
