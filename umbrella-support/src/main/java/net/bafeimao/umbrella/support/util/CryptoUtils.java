package net.bafeimao.umbrella.web.util;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by ktgu on 15/6/28.
 */
public class CryptoUtils {
    private static final String MD5_ALGORITHM_NAME = "MD5";
    private static final String SHA_ALGORITHM_NAME = "SHA";
    private static final String DES_ALGORITHM_NAME = "DES";
    private static final String AES_ALGORITHM_NAME = "AES";

    public static byte[] md5(String message) {
        return md5(message.getBytes());
    }

    public static byte[] md5(byte[] input) {
        // 经计算出来的消息摘要（128bit）
        MessageDigest md = getMessageDigest(MD5_ALGORITHM_NAME);
        return md.digest(input);
    }

    public static String md5AsHex(String message) {
        return md5AsHex(message.getBytes());
    }

    public static String md5AsHex(byte[] bytes) {
        return bytes2HexString(md5(bytes));
    }

    public static byte[] sha(byte[] input) {
        return getMessageDigest(SHA_ALGORITHM_NAME).digest(input);
    }

    public static byte[] sha(String message) {
        return sha(message.getBytes());
    }

    public static String shaAsHex(byte[] input) {
        return bytes2HexString(sha(input));
    }

    public static String shaAsHex(String message) {
        return shaAsHex(message.getBytes());
    }



    public static byte[] aesEncrypt(String message, String password) {
        return aesEncrypt(message.getBytes(), password);
    }

    public static byte[] aesEncrypt(byte[] input, String password) {
        return doAes(Cipher.ENCRYPT_MODE, input, password);
    }

    public static byte[] aesDecrypt(byte[] input, String password) {
        return doAes(Cipher.DECRYPT_MODE, input, password);
    }

    private static byte[] doAes(int mode, byte[] input, String password) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance(AES_ALGORITHM_NAME);
            keygen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = keygen.generateKey();

            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES_ALGORITHM_NAME);

            Cipher cipher = Cipher.getInstance(AES_ALGORITHM_NAME);
            cipher.init(Cipher.DECRYPT_MODE, key);

            return cipher.doFinal(input);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 二进制字节流转十六进制字符串（每4位二进制换算得到1位16进制，该16进制用大写字符表示：0-F）
     *
     * @param bytes 要转化的二进制字节流
     * @return 返回转化后的十六进制（字符表示，0-f）
     */
    public static String bytes2HexString(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                buffer.append("0");
            }
            buffer.append(Integer.toHexString(digital));
        }
        return buffer.toString();
    }

    public static byte[] desEncrypt(byte[] input, String password) {
        return doDes(Cipher.ENCRYPT_MODE, input, password);
    }

    public static byte[] desEncrypt(String message, String password) {
        return desEncrypt(message.getBytes(), password);
    }

    public static byte[] desDecrypt(byte[] input , String password) {
        return doDes(Cipher.DECRYPT_MODE, input, password);
    }

    private static byte[] doDes(int mode, byte[] input, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(mode, securekey, random);
            return cipher.doFinal(input);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据名称创建对应的消息摘要对象
     *
     * @param algorithm 算法名称
     * @return 返回消息摘要对象
     */
    private static MessageDigest getMessageDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("找不到名称为: \"" + algorithm + "\"的消息摘要算法", ex);
        }
    }

}
