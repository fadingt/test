package cn.com.agree.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptUtils {
    public static void main(String[] args) {
        System.out.println(Encrypt("1d15cda352d9c34947891c337c68a4e1","MD5"));
        System.out.println(getMD5("1d15cda352d9c34947891c337c68a4e1"));
        System.out.println(EncoderByMd5("1d15cda352d9c34947891c337c68a4e1"));
    }
    public static String getMD5(String str) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("MD5ǩ��ʧ��");
            //todo log error
            return null;
        }
    }

    public static String EncoderByMd5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            Base64.Encoder en = Base64.getEncoder();
            String newstr = new String(en.encode(md5.digest(str.getBytes("utf-8"))));
            return newstr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //todo log error
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //todo log error
            return null;
        }
    }

    public static String Encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;
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
            if (encName.substring(0, 3) == "MD5") {
                strDes = "{MD5}" + strDes;
            } else {
                strDes = "{SHA}" + strDes;
            }
            //strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            System.out.println("ǩ��ʧ�ܣ�");
            return null;
        }
        return strDes;
    }

    /**
     * ��16�����ַ���ת��Ϊbyte[]
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
     * ��byteתΪ16����
     *
     * @param bytes
     * @return
     */
    private static String bytes2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1�õ�һλ�Ľ��в�0����
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    // �������û���������м����㷨����֤
    public static boolean verifySHA(String ldappw, String inputpw) throws NoSuchAlgorithmException {

        // MessageDigest �ṩ����ϢժҪ�㷨���� MD5 �� SHA���Ĺ��ܣ�����LDAPʹ�õ���SHA-1
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        // ȡ�������ַ�
        if (ldappw.startsWith("{SSHA}")) {
            ldappw = ldappw.substring(6);
        } else if (ldappw.startsWith("{SHA}")) {
            ldappw = ldappw.substring(5);
        }

        // ����BASE64
        byte[] ldappwbyte = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.decode(ldappw);
        byte[] shacode;
        byte[] salt;

        // ǰ20λ��SHA-1���ܶΣ�20λ�����������ʱ���������
        if (ldappwbyte.length <= 20) {
            shacode = ldappwbyte;
            salt = new byte[0];
        } else {
            shacode = new byte[20];
            salt = new byte[ldappwbyte.length - 20];
            System.arraycopy(ldappwbyte, 0, shacode, 0, 20);
            System.arraycopy(ldappwbyte, 20, salt, 0, salt.length);
        }

        // ���û������������ӵ�ժҪ������Ϣ
        md.update(inputpw.getBytes());
        // �����������ӵ�ժҪ������Ϣ
        md.update(salt);

        // ��SSHA�ѵ�ǰ�û�������м���
        byte[] inputpwbyte = md.digest();

        // ����У����
        return MessageDigest.isEqual(shacode, inputpwbyte);
    }
}
