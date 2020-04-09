package cn.com.agree.utils;

import org.junit.jupiter.api.Test;

class EncryptUtilsTest {

    private String pwd1 = "agree123";
    private String md5md5_pwd1 = "5d8fef862dd02c3035c715a522dab50a";//����MD5��� ACCOUNT_PWD
    private String md5_pwd1 = "1da390d3baecdfabab7bc1f145594965";//һ��MD5��� MD5_PWD
    private String vpn_pwd1 = "{MD5}HaOQ07rs36ure8HxRVlJZQ==";//VPN���� MD5��BASE64


    @Test
    void getMD5() {
        String result;
        System.out.println("MD5(" + pwd1 + ")=" + (result = EncryptUtils.getMD5(pwd1)));
        assert result.equals(md5_pwd1);
        System.out.println("MD5(" + md5_pwd1 + ")=" + (result = EncryptUtils.getMD5(md5_pwd1)));
        assert result.equals(md5md5_pwd1);
    }

    @Test
    void encoderByMd5() {
    }

    @Test
    void encrypt() {
        String result;
        System.out.println(result = EncryptUtils.Encrypt(md5_pwd1, "MD5"));
        assert result.equals(vpn_pwd1);
    }

    @Test
    void toBytes() {
    }

    @Test
    void verifySHA() {
    }
}