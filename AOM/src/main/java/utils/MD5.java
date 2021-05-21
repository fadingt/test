package utils;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class MD5 {
	public static void main(String[] args) throws IOException{
		System.out.println("please input a String:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputStr = br.readLine().trim();
        System.out.println(getMD5(getMD5(inputStr)));
        System.out.println(EncoderByMd5(EncoderByMd5(inputStr)));
    }

	public static String getMD5(String str) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		md.update(str.getBytes());
		return new BigInteger(1, md.digest()).toString(16);
	}

	public static String EncoderByMd5(String str){
	    if(str==null){throw new RuntimeException("String cannot be null");}
        MessageDigest md5 = null;
        String newstr = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            Encoder en = Base64.getEncoder();
            newstr = new String(en.encode(md5.digest(str.getBytes("utf-8"))));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newstr;
	}

}
