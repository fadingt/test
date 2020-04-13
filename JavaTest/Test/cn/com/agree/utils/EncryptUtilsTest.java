package cn.com.agree.utils;

import org.junit.jupiter.api.Test;

class EncryptUtilsTest {

    private String pwd1 = "agree123";
    private String md5md5_pwd1 = "5d8fef862dd02c3035c715a522dab50a";//md5(md5(pwd1)) ACCOUNT_PWD
    private String md5_pwd1 = "1da390d3baecdfabab7bc1f145594965";//“ªmd5(pwd1) MD5_PWD
    private String md5base64_pwd1 = "{MD5}HaOQ07rs36ure8HxRVlJZQ==";//VPN√‹¬Î md5(BASE64)


    @Test
    void getMD5() {
        String result;
        System.out.println("MD5(" + pwd1 + ")=" + (result = EncryptUtils.getMD5(pwd1)));
        assert result.equals(md5_pwd1);
        System.out.println("MD5(" + md5_pwd1 + ")=" + (result = EncryptUtils.getMD5(md5_pwd1)));
        assert  result.equals(md5md5_pwd1);
        EncryptUtils.getMD5(null);
    }

    @Test
    void encoderByMd5() {
        String result;
        System.out.println(result=EncryptUtils.EncoderByMd5(pwd1));
        assert "{MD5}".concat(result).equals(md5base64_pwd1);
        EncryptUtils.EncoderByMd5(null);
    }

    @Test
    void encrypt() {
        EncryptUtils.Encrypt(null,"MD5");
        String result;
        System.out.println(result = EncryptUtils.Encrypt(md5_pwd1, "MD5"));
        assert result.equals(md5base64_pwd1);
        System.out.println(EncryptUtils.Encrypt(null,"MD5"));
    }

    @Test
    void toBytes() {
    }

    @Test
    void verifySHA() {
    }
}