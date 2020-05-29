package com.iber.portal.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *         @author ngh
 *         AES128 算法
 *         <p/>
 *         CBC 模式
 *         <p/>
 *         PKCS7Padding 填充模式
 *         <p/>
 *         CBC模式需要添加一个参数iv
 *         <p/>
 *         介于java 不支持PKCS7Padding，只支持PKCS5Padding 但是PKCS7Padding 和 PKCS5Padding 没有什么区别
 *         要实现在java端用PKCS7Padding填充，需要用到bouncycastle组件来实现
 */
public class AES {

    private static final Logger LOGGER = LoggerFactory.getLogger(AES.class);
    // 算法名称
    private static final String KEY_ALGORITHM = "AES";
    // 加解密算法/模式/填充方式
    private static final String ALGORITHM_STR = "AES/CBC/PKCS5Padding";
    private static Key key;
    private static final Cipher CIPHER;

    static {
        try {
            // 初始化cipher
            CIPHER = Cipher.getInstance(ALGORITHM_STR);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private static final byte[] iv = "kissxiexin990918".getBytes();

    private static boolean init(String keyStr) {
        if (keyStr == null || keyStr.isEmpty()) {
            return false;
        }
        byte[] keyBytes;
        try {
            keyBytes = keyGenForSerial(keyStr);
        } catch (Exception e) {
            LOGGER.error("init 失败!",e);
            return false;
        }
        return init(keyBytes);
    }

    private static boolean init(byte[] keys) {
        if (keys == null || keys.length <= 0) {
            return false;
        }
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keys.length % base != 0) {
            int groups = keys.length / base + (keys.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(key, 0, temp, 0, keys.length);
            keys = temp;
        }
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keys, KEY_ALGORITHM);
        return true;
    }

    /**
     * 加密方法
     *
     * @param content 要加密的字符串
     * @param keyStr  加密密钥
     * @return
     */
    public static byte[] encrypt(byte[] content, String keyStr) {
        init(keyStr);
        return initEncrypt(content);
    }

    /**
     * 加密方法
     *
     * @param content 要加密的字符串
     * @param keys    加密密钥
     * @return
     */
    public static byte[] encrypt(byte[] content, byte[] keys) {
        init(keys);
        return initEncrypt(content);
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyStr        解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] encryptedData, String keyStr) {
        init(keyStr);
        return initdecrypt(encryptedData);
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keys          解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] encryptedData, byte[] keys) {
        init(keys);
        return initdecrypt(encryptedData);
    }

    private static byte[] initEncrypt(byte[] content) {
        byte[] encryptedText;
        try {
            CIPHER.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            encryptedText = CIPHER.doFinal(content);
        } catch (Exception e) {
            LOGGER.error("encrypt 失败!",e);
            throw new RuntimeException(e);
        }
        return encryptedText;
    }

    private static byte[] initdecrypt(byte[] encryptedData) {
        byte[] encryptedText;
        try {
            CIPHER.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            encryptedText = CIPHER.doFinal(encryptedData);
        } catch (Exception e) {
            LOGGER.error("decrypt 失败!",e);
            throw new RuntimeException(e);
        }
        return encryptedText;
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        hexStr = hexStr.replaceAll("[^\\w]+", "");
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    private static byte[] keyGenForSerial(String elpn) {
        if (elpn != null && elpn.length() > 8) {
            elpn = elpn.toLowerCase();
            elpn = elpn + elpn.substring(elpn.length() - (16 - elpn.length()), elpn.length());
            try {
                byte[] temp = elpn.getBytes("UTF-8");
                byte[] pwd = new byte[16];
                for (int i = 0; i < 16; i++) {
                    pwd[i] = (byte) (temp[i] | iv[i]);
                }
                return pwd;
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("keyGenForSerial 失败!",e);
            }
        }
        return null;
    }


    public static void main(String[] args) {
        byte[] password = keyGenForSerial("021AB3D00E3B");
        try {
            System.out.println("密码：" + new String(password, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
       //加密
        String content = "BEC5";
        byte[] encryptResult = encrypt(content.getBytes(), "{{ssz{exyo;{0}3z".getBytes());

        String encryptResultStr = parseByte2HexStr(encryptResult);
        System.out.println("加密后：" + encryptResultStr);
        //解密
        byte[] decryptResult = decrypt(parseHexStr2Byte("FB3EA4E0E9F672EB9B1793C087582B72"),  "{{ssz{exyo;{0}3z".getBytes());
        System.out.println("解密后：" + new String(decryptResult));
    }
}