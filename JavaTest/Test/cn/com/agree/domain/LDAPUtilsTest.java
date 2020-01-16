package cn.com.agree.domain;

import org.junit.jupiter.api.Test;

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

//密码校验
//		try {
//		LDAPHelper.verifySHA("{SHA}fCIvspJ9goryL1khNOiTJIBjfA0=","12345678");
//	} catch (NoSuchAlgorithmException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	System.out.println("加密后的密码"+LDAPHelper.Encrypt(pwd, "SHA-1"));
//	System.exit(0);

//		ldap.closeContext();
* */
class LDAPUtilsTest {

    @Test
    void authenricate() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void deleteAllUsers() {
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