package cn.com.agree.domain;

import cn.com.agree.config.LDAPConfig;
import cn.com.agree.utils.EncryptUtils;
import cn.com.agree.utils.LDAPUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/*
* //删除用户
//		if( ldap.deleteUser(user) ) {
//			System.out.println(user+"用户删除成功");
//
//		}else {
//			System.out.println(user+"用户删除失败");
//
//		}

//添加用户
//		if( ldap.addUser(cn,LDAPHelper.Encrypt(pwd,"SHA-1"),user,description) ) {
//			System.out.println(user+"用户添加成功");
//
//		}else {
//			System.out.println(user+"用户添加失败");
//
//		}

//修改用户属性
//	if( ldap.modifyInformation("uid=wangzhe","telephoneNumber","22222222222") ) {
//		System.out.println(user+"用户属性修改成功");
//
//	}else {
//		System.out.println(user+"用户属性修改失败");
//
//	}s

//查找用户
//		ldap.searchInformation("","","ou=赞同");
//		try {
//			ldap.Ldapbyuserinfo("A2073");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

*/
class LDAPUtilsTest {
    private LDAPUtils ldap;
    private User user;
    private String password;
    LDAPUtilsTest() throws IOException {
        String resourcePath = "D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource";
        String child = "ldap_produce.properties";
//        String child = "ldap.properties";
        LDAPConfig config = new LDAPConfig(new File(resourcePath,child));
        this.ldap = new LDAPUtils();
        this.ldap.connect(config);
        this.user = new User();
        this.password = "agree123";
        this.user.setUsercode("A6853");
        this.user.setOrgcode("");
        this.user.setPassword(EncryptUtils.getMD5(password));
    }

    @Test
    void renameEntry(){
    }

    @Test
    void authenricateTest() {
        ldap.authenricate(this.user.getUsercode(),this.password);
    }

    @Test
    void deleteUser() {
    }

    @Test
    void deleteAllUsers() {
        this.ldap.addUser(user);
//        this.ldap.deleteAllUsers("ou=赞同");
        assert !ldap.authenricate(this.user.getUsercode(),this.password);
    }

    @Test
    void getOUNames() {
    }

    @Test
    void deleteAllDeps() {
    }

    @Test
    void createDepartment() {
    }

    @Test
    void addUser() {
    }

    @Test
    void modifyInformation() {
    }

    @Test
    void searchInformation() {
//        this.ldap.searchInformation("","","ou=赞同");
        this.ldap.searchInformation("","","(objectClass=organizationalPerson)");
    }
    @Test
    void searchUser(){
        this.ldap.searchUser("ou=信息技术部,ou=赞同科技,ou=赞同","(objectClass=organizationalPerson)");
    }

    @Test
    void parseControls() {
    }

    @Test
    void ldapbyuserinfo() {
    }

    @Test
    void updateUserPassword() {
    }

    @Test
    void fillMD5() {
    }

    @Test
    void encrypt() {
    }

    @Test
    void createTree() {
    }

    @Test
    void createTreeByExcel() {
    }

    @Test
    void replacePath() {
    }

    @Test
    void parsePath() {
    }
}