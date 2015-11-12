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

package net.bafeimao.umbrella.support.util;

/**
 * Created by ktgu on 15/6/28.
 */
public class StringUtils {
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    public static final String EMPTY = "";

    /**
     * 二进制字节流转十六进制字符串（每4位二进制换算得到1位16进制，该16进制用大写字符表示：0-f）
     *
     * @param bytes 要转化的二进制字节流
     * @return 返回转化后的十六进制（字符表示，0-f）
     */
    public static String bytes2Hex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        final StringBuilder hexBuilder = new StringBuilder(2 * bytes.length);
        for (final byte b : bytes) {
            hexBuilder.append(HEX_CHARS[(b & 0xF0) >> 4]).append(HEX_CHARS[b & 0x0F]);
        }

        return hexBuilder.toString();
    }

    public static byte[] hex2Bytes(byte[] input) {
        if ((input.length % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }

        byte[] bytes = new byte[input.length / 2];
        for (int n = 0; n < input.length; n += 2) {
            String s = new String(input, n, 2);
            bytes[n / 2] = (byte) Integer.parseInt(s, 16);
        }

        return bytes;
    }


    public static void main(String[] args) {
        String s1 = StringUtils.bytes2Hex("aaa".getBytes());
        System.out.print(s1);
    }

    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     * 裁剪头部和尾部空格
     */
    public static String trim(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 裁剪头部空格
     */
    public static String ltrim(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * 裁剪尾部空格
     */
    public static String rtrim(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static boolean isEmpty(String str) {
        return (str == null || "".equals(str));
    }
}
