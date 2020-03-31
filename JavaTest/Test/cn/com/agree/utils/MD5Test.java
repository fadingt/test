package cn.com.agree.utils;

import org.junit.jupiter.api.Test;

class MD5Test {
    private String param1;
//    private String expected;
    public MD5Test(){
        String pwd = EncryptUtils.Encrypt("1d15cda352d9c34947891c337c68a4e1", "MD5");
        System.out.println(pwd);
        System.out.println(pwd.equals("{MD5}HRXNo1LZw0lHiRwzfGik4Q=="));
    }

    @Test
    void getMD5() {
        String input = "agree123";
        String output = EncryptUtils.getMD5(input);
        System.out.println(output);
        assert output.equals("1da390d3baecdfabab7bc1f145594965");
    }

    @Test
    void encoderByMd5() {
        String input = "agree123";
        String output = EncryptUtils.EncoderByMd5(input);
    }
}