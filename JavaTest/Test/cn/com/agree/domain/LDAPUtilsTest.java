package cn.com.agree.domain;

import org.junit.jupiter.api.Test;

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

//����У��
//		try {
//		LDAPHelper.verifySHA("{SHA}fCIvspJ9goryL1khNOiTJIBjfA0=","12345678");
//	} catch (NoSuchAlgorithmException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	System.out.println("���ܺ������"+LDAPHelper.Encrypt(pwd, "SHA-1"));
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