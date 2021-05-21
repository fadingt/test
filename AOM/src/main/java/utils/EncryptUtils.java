package utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptUtils {
    /**
     * @return md5 encrypted hex String
     * @see java.security.MessageDigest#digest
     */
    public static String getMD5(String str) {
        if (str == null) {
            return null;
        }
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes(StandardCharsets.UTF_8));
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * md5(base64) MD5加密结果转换16进制字符串 然后对字符串byte[] BASE64 encode
     *
     * @return String
     */
    public static String getEncodedMD5(String str) {
        if (str == null) {
            return null;
        }
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            return new String(Base64.getEncoder().encode(md5.digest(str.getBytes(StandardCharsets.UTF_8))));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    @Deprecated
    public static String encrypt(String strSrc, String encName) {
        if (strSrc == null){
            return null;
        }
        String strDes;
        strDes = new String(java.util.Base64.getEncoder().encode(new BigInteger(strSrc, 16).toByteArray()));
//        strDes = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(new BigInteger(strSrc, 16).toByteArray());
        return "{" + encName + "}" + strDes;
    }

    /**
     * @see BigInteger#toByteArray()
     */
    @Deprecated
    public static byte[] toBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    // 将输入用户和密码进行加密算法后验证
    public static boolean verifySHA(String ldappw, String inputpw) throws NoSuchAlgorithmException {
        // MessageDigest 提供了消息摘要算法，如 MD5 或 SHA，的功能，这里LDAP使用的是SHA-1
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        // 取出加密字符
        if (ldappw.startsWith("{SSHA}")) {
            ldappw = ldappw.substring(6);
        } else if (ldappw.startsWith("{SHA}")) {
            ldappw = ldappw.substring(5);
        }

        // 解码BASE64
        byte[] ldappwbyte = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.decode(ldappw);
        byte[] shacode;
        byte[] salt;

        // 前20位是SHA-1加密段，20位后是最初加密时的随机明文
        if (ldappwbyte.length <= 20) {
            shacode = ldappwbyte;
            salt = new byte[0];
        } else {
            shacode = new byte[20];
            salt = new byte[ldappwbyte.length - 20];
            System.arraycopy(ldappwbyte, 0, shacode, 0, 20);
            System.arraycopy(ldappwbyte, 20, salt, 0, salt.length);
        }

        // 把用户输入的密码添加到摘要计算信息
        md.update(inputpw.getBytes());
        // 把随机明文添加到摘要计算信息
        md.update(salt);

        // 按SSHA把当前用户密码进行计算
        byte[] inputpwbyte = md.digest();

        // 返回校验结果
        return MessageDigest.isEqual(shacode, inputpwbyte);
    }
}
