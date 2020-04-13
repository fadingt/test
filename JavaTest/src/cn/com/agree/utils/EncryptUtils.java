package cn.com.agree.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptUtils {
    public static String getMD5(String str) {
        MessageDigest md;
        if (str == null) return null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("MD5签名失败");
            //todo log error
            return null;
        }
    }

    public static String EncoderByMd5(String str) {
        MessageDigest md5;
        if (str == null) return null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            Base64.Encoder en = Base64.getEncoder();
            return new String(en.encode(md5.digest(str.getBytes(StandardCharsets.UTF_8))));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //todo log error
            return null;
        }
    }

/*    public static String BASE64(String strSrc) {
        if (strSrc == null) return null;
        return com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(toBytes(strSrc));
    }*/

    public static String Encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes;
//        todo null pointer error
        if (strSrc == null) return null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance(encName);
//             md.update(bt);
            //System.out.println("md5:"+md.digest().toString());
//             String md5=new BigInteger(1, md.digest()).toString(16);
//             md5 = fillMD5(md5);
//            System.out.println("strSrc:" + strSrc);

            //strDes = Base64.encode(md.digest());
            strDes = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(toBytes(strSrc));

//            System.out.println("strDes:" + strDes);
            if (encName.substring(0, 3).equals("MD5")) {
                strDes = "{MD5}" + strDes;
            } else {
                strDes = "{SHA}" + strDes;
            }
            //strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            System.out.println("签名失败！");
            return null;
        }
        return strDes;
    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
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

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String bytes2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
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
