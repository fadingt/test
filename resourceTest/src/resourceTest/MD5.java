package resourceTest;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class MD5 {
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(getMD5("z13833545277"));
		System.out.println(EncoderByMd5("z13833545277"));
		System.out.println(EncoderByMd5("0123456789"));
	}

	public static String getMD5(String str) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes());
		return new BigInteger(1, md.digest()).toString(16);
	}
	public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		Encoder en = Base64.getEncoder();
		String newstr = new String( en.encode(md5.digest(str.getBytes("utf-8"))));
		return newstr;
	}

}
