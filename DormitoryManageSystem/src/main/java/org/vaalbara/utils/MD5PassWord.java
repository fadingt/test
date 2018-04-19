package org.vaalbara.utils;

import org.vaalbara.bean.Admin;
import org.vaalbara.bean.Student;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ZhangW on 2018/3/14.
 */
public class MD5PassWord {
    static MessageDigest messageDigest = null;
    public static String MD5Util(String Pass){
        try {
//            创建MD5
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.update(Pass.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String sPassMD5 = new BigInteger(1,messageDigest.digest()).toString(16);
        return sPassMD5;
    }
    public static Student MD5Pass(Student student){
        String sPassMD5 = MD5Util(student.getsPass());
        student.setsPass(sPassMD5);
        return student;
    }

    public static Admin MD5AdminPass(Admin admin){
        String  aPassMD5= MD5Util(admin.getaPass());
        admin.setaPass( aPassMD5);
        return admin;
    }
}
