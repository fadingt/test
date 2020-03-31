package cn.com.agree.utils;

import cn.com.agree.config.LDAPConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LDAPUtilsTest {
    @BeforeAll
    public static void runBeforeTestMethod() throws SQLException, IOException, ClassNotFoundException {
        LDAPUtils ldap = new LDAPUtils();
        LDAPConfig config = new LDAPConfig(new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\ldap.properties"));
//        LDAPConfig config = new LDAPConfig(new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\ldap_produce.properties"));
//        ldap.LDAP_connect(config);
    }
    @Test
    void authenricate() {

    }

    @Test
    void deleteAllDeps() {
    }

    @Test
    void createDepartment() {
    }

    @Test
    void syncDept2Ldap() {
    }

    @Test
    void addUser() {
    }

    @Test
    void testAddUser() {
    }

    @Test
    void sync2Ldap() {
    }

}