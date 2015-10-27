package net.bafeimao.umbrella.support.util;

/**
 * Created by ktgu on 15/6/28.
 */
public class StringUtils {
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

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
}
