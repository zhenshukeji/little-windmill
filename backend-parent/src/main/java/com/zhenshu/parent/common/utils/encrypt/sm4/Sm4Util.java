package com.zhenshu.parent.common.utils.encrypt.sm4;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;

/**
 * sm4加密算法工具类
 *
 * @explain sm4加密、解密与加密结果验证 可逆算法
 */
public class Sm4Util {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String ENCODING = "UTF-8";
    public static final String ALGORITHM_NAME = "SM4";
    /**
     * 加密算法/分组加密模式/分组填充方式
     * PKCS5Padding-以8个字节为一组进行分组加密
     * 定义分组加密模式使用：PKCS5Padding
     */
    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/NoPadding";
    /**
     * 128-32位16进制；256-64位16进制
     */
    public static final int DEFAULT_KEY_SIZE = 128;

    /**
     * 生成ECB暗号
     *
     * @param mode 模式
     * @param key
     * @return
     * @throws Exception
     * @explain ECB模式（电子密码本模式：Electronic codebook）
     */
    private static Cipher generateEcbCipher(int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(Sm4Util.ALGORITHM_NAME_ECB_PADDING, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        cipher.init(mode, sm4Key);
        return cipher;
    }

    /**
     * 自动生成密钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @explain
     */
    public static byte[] generateKey() throws Exception {
        return generateKey(DEFAULT_KEY_SIZE);
    }


    //加密******************************************

    /**
     * @param keySize
     * @return
     * @throws Exception
     * @explain 系统产生秘钥
     */
    public static byte[] generateKey(int keySize) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
        kg.init(keySize, new SecureRandom());
        return kg.generateKey().getEncoded();
    }

    /**
     * sm4加密
     *
     * @param hexKey   16进制密钥（忽略大小写）
     * @param paramStr 待加密字符串
     * @return 返回16进制的加密字符串
     * @throws Exception
     * @explain 加密模式：ECB 密文长度不固定，会随着被加密字符串长度的变化而变化
     */
    public static String encryptEcb(String hexKey, String paramStr) throws Exception {
        // 16进制字符串-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // String-->byte[] 进行填充
        byte[] srcData = paramStr.getBytes(ENCODING);
        // 加密后的数组
        byte[] cipherArray = encryptEcbPadding(keyData, srcData);
        // byte[]-->hexString
        return ByteUtils.toHexString(cipherArray);
    }

    /**
     * 使用 ISO9797M1Padding填充模式 sm4加密
     *
     * @param hexKey   16进制密钥（忽略大小写）
     * @param paramStr 待加密字符串
     * @return 返回16进制的加密字符串
     * @throws Exception
     * @explain 加密模式：ECB 密文长度不固定，会随着被加密字符串长度的变化而变化
     */
    public static String encryptIsoEcb(String hexKey, String paramStr) throws Exception {
        // 16进制字符串-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // String-->byte[] 进行填充
        byte[] srcData = paramStr.getBytes(ENCODING);

        int dataLength = srcData.length;
        int length = 16 - dataLength % 16;
        int newLength = dataLength + length;
        byte[] padData = new byte[newLength];
        System.arraycopy(srcData, 0, padData, 0, dataLength);

        // 加密后的数组
        byte[] cipherArray = encryptEcbPadding(keyData, padData);
        // byte[]-->hexString
        return ByteUtils.toHexString(cipherArray);
    }

    /**
     * 加密模式之Ecb
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptEcbPadding(byte[] key, byte[] data) throws Exception {
        //声称Ecb暗号,通过第二个参数判断加密还是解密
        Cipher cipher = generateEcbCipher(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    //解密****************************************

    /**
     * sm4解密
     *
     * @param hexKey     16进制密钥
     * @param cipherText 16进制的加密字符串（忽略大小写）
     * @return 解密后的字符串
     * @throws Exception
     * @explain 解密模式：采用ECB
     */
    public static String decryptEcb(String hexKey, String cipherText) throws Exception {
        // hexString-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // hexString-->byte[]
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        // 解密
        byte[] srcData = decryptEcbPadding(keyData, cipherData);
        // byte[]-->String
        return new String(srcData, ENCODING);
    }


    /**
     * 使用ISO9797M1Padding填充模式 sm4解密
     *
     * @param hexKey     16进制密钥
     * @param cipherText 16进制的加密字符串（忽略大小写）
     * @return 解密后的字符串
     * @throws Exception
     * @explain 解密模式：采用ECB
     */
    public static String decryptIsoEcb(String hexKey, String cipherText) throws Exception {
        // hexString-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // hexString-->byte[]
        byte[] cipherData = ByteUtils.fromHexString(cipherText);

        // 解密
        byte[] srcData = decryptEcbPadding(keyData, cipherData);
        int size = srcData.length;
        // 查找到没有0的位置
        for (int i = srcData.length - 1; i >= 0; i--) {
            if (srcData[i] == 0) {
                size = i;
            } else {
                break;
            }
        }
        // 裁剪给新数组
        byte[] result = new byte[size];
        System.arraycopy(srcData, 0, result, 0, size);

        // byte[]-->String
        return new String(result, ENCODING);
    }

    /**
     * 解密
     *
     * @param key
     * @param cipherText
     * @return
     * @throws Exception
     * @explain
     */
    public static byte[] decryptEcbPadding(byte[] key, byte[] cipherText) throws Exception {
        //生成Ecb暗号,通过第二个参数判断加密还是解密
        Cipher cipher = generateEcbCipher(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    /**
     * 校验加密前后的字符串是否为同一数据
     *
     * @param hexKey     16进制密钥（忽略大小写）
     * @param cipherText 16进制加密后的字符串
     * @param paramStr   加密前的字符串
     * @return 是否为同一数据
     * @throws Exception
     * @explain
     */
    public static boolean verifyEcb(String hexKey, String cipherText, String paramStr) throws Exception {
        // hexString-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // 将16进制字符串转换成数组
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        // 解密
        byte[] decryptData = decryptEcbPadding(keyData, cipherData);
        // 将原字符串转换成byte[]
        byte[] srcData = paramStr.getBytes(ENCODING);
        // 判断2个数组是否一致
        return Arrays.equals(decryptData, srcData);
    }


}
