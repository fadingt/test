package cn.com.agree.domain;

import cn.com.agree.config.LDAPConfig;
import cn.com.agree.utils.EncryptUtils;
import cn.com.agree.utils.LDAPUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/*
* //ɾ���û�
//		if( ldap.deleteUser(user) ) {
//			System.out.println(user+"�û�ɾ���ɹ�");
//
//		}else {
//			System.out.println(user+"�û�ɾ��ʧ��");
//
//		}

//����û�
//		if( ldap.addUser(cn,LDAPHelper.Encrypt(pwd,"SHA-1"),user,description) ) {
//			System.out.println(user+"�û���ӳɹ�");
//
//		}else {
//			System.out.println(user+"�û����ʧ��");
//
//		}

//�޸��û�����
//	if( ldap.modifyInformation("uid=wangzhe","telephoneNumber","22222222222") ) {
//		System.out.println(user+"�û������޸ĳɹ�");
//
//	}else {
//		System.out.println(user+"�û������޸�ʧ��");
//
//	}s

//�����û�
//		ldap.searchInformation("","","ou=��ͬ");
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
//        this.ldap.deleteAllUsers("ou=��ͬ");
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
//        this.ldap.searchInformation("","","ou=��ͬ");
        this.ldap.searchInformation("","","(objectClass=organizationalPerson)");
    }
    @Test
    void searchUser(){
        this.ldap.searchUser("ou=��Ϣ������,ou=��ͬ�Ƽ�,ou=��ͬ","(objectClass=organizationalPerson)");
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