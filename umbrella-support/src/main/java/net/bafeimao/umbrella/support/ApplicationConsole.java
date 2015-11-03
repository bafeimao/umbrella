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

package net.bafeimao.umbrella.support;

/**
 * Created by gukaitong(29283212@qq.com) on 2015/11/3.
 *
 * @author gukaitong
 * @since 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.StringTokenizer;

public class ApplicationConsole implements Runnable {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConsole.class);

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("����: q|exit:�˳�����. help: �鿴����!");

            String read = scanner.nextLine();

            // �������ر�exit�˳�ʱ�������¼�
            if ("q".equals(read) || "exit".equals(read)) {
                scanner.close();
                System.exit(0);
                break;
            }

            if ("help".equals(read)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Commands:\n");
                sb.append("stats: �鿴��ǰ������ͳ����Ϣ");
                sb.append("print role <id>: ��ӡ��ɫ��Ϣ");
                System.out.println();
            }

            try {
                if (read != null && read.startsWith("print ")) {
                    StringTokenizer tokenizer = new StringTokenizer(read);
                    tokenizer.nextToken();
                    String roleId = tokenizer.nextToken();
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("{}", e);
            }

        }
    }
}