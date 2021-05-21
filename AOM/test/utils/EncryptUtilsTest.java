package utils;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

class EncryptUtilsTest {
    //        agree123
//    ldap md5(base64)  {MD5}HaOQ07rs36ure8HxRVlJZQ==
//    login md5()    1da390d3baecdfabab7bc1f145594965
//    adminlogin md5(md5()) 5d8fef862dd02c3035c715a522dab50a
    private String pwd;

    public EncryptUtilsTest() {
        this.pwd = "agree123";
    }

    @Test
    void getMD5() {
        System.out.println(EncryptUtils.getMD5(pwd));
    }

    @Test
    void encoderByMd5() {
//        System.out.println(EncryptUtils.getEncodedMD5(this.pwd));
//        assert EncryptUtils.getEncodedMD5(this.pwd).equals("HaOQ07rs36ure8HxRVlJZQ==");
//        YhJzhqo7cDIxKIDtdurKew==
        String password = "99wghJd4GasTo5Oh97N9Qg==";
        password = new BigInteger(1, java.util.Base64.getDecoder().decode(password.getBytes())).toString(16);
        System.out.println(password);
//        System.out.println(getMD5(password));


    }

    @Test
    void verifySHA() {
//        LDAPUtils ldap = new LDAPUtils();
//        ldap.connect();
//        ldap.searchInformation("", "", "uid=A3248");
        String ldapwd;
        ldapwd="hyvnN40uXEt0fyVHFExtxQ==";
        String a=new BigInteger(1,java.util.Base64.getDecoder().decode(ldapwd.getBytes())).toString(16);
        System.out.println("一次MD5加密结果: "+a);
        System.out.println("两次MD5加密结果: "+EncryptUtils.getMD5(a));
    }

}