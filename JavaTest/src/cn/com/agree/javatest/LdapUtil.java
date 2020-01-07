package cn.com.agree.javatest;


//import com.angma.mes.jagybarcode.manager.controller.plan.machine.MachineShopTaskCreateController;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

/**
 * �û���½��֤,LDAP������֤��ͨ��LDAP���û����и���
 * 
 * @author xlj
 * @date 2015.07.10
 */
public class LdapUtil {

	private static DirContext ctx;

	// LDAP�������˿�Ĭ��Ϊ389
	private static final String LDAP_URL = "ldap://127.0.0.1:389";

	// ROOT���ݴ˲���ȷ���û���֯����λ��
	private static final String LDAP_PRINCIPAL = "OU=CMA Users,DC=changan-mazda,DC=com,DC=cn";

	// LDAP����
	private static final String LDAP_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

	private static Logger logger = Logger.getLogger("cn.com.agree.javatest");

	/**** ���� ****/
	public static void main(String[] args) {
		LdapUtil.getLoginContext();
		LdapUtil.addUserLdap("10000", "123456");
		LdapUtil.updatePasswordLdap("10000", "1234567");
		LdapUtil.deleteUserLdap("10000");
	}

	// ͨ������LDAP���������û�������֤������LDAP����
	public static DirContext getLoginContext() {
		String account = "zhangsan"; // ģ���û���
		String password = "123456"; // ģ������
		for (int i = 0; i < 5; i++) { // ��֤����
			Hashtable env = new Hashtable();
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_CREDENTIALS, password);
			// cn=�����ĸ���֯�ṹ���ƣ�ou=ĳ����֯�ṹ�����µȼ�λ�ñ��
			env.put(Context.SECURITY_PRINCIPAL, "cn=" + account + ", ou=Level0" + i + "00," + LDAP_URL);
			env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_PRINCIPAL);
			env.put(Context.PROVIDER_URL, LDAP_FACTORY);
			try {
				// ����LDAP������֤
				ctx = new InitialDirContext(env);
				System.out.println("��֤�ɹ�");
				logger.info("��" + account + "���û��ڡ�" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "����½ϵͳ�ɹ�");
			} catch (javax.naming.AuthenticationException e) {
				System.out.println("��֤ʧ��");
			} catch (NamingException err) {
				logger.info("--------->>��" + account + "���û���֤ʧ�ܡ�" + i + "����");
			} catch (Exception e) {
				System.out.println("��֤����");
				e.printStackTrace();
			}
		}
		return ctx;
	}

	// �������û���������м����㷨����֤
	public static boolean verifySHA(String ldappw, String inputpw) throws NoSuchAlgorithmException {

		// MessageDigest �ṩ����ϢժҪ�㷨���� MD5 �� SHA���Ĺ��ܣ�����LDAPʹ�õ���SHA-1
		MessageDigest md = MessageDigest.getInstance("SHA-1");

		// ȡ�������ַ�
		if (ldappw.startsWith("{SSHA}")) {
			ldappw = ldappw.substring(6);
		} else if (ldappw.startsWith("{SHA}")) {
			ldappw = ldappw.substring(5);
		}

		// ����BASE64
		byte[] ldappwbyte = Base64.decode(ldappw);
		byte[] shacode;
		byte[] salt;

		// ǰ20λ��SHA-1���ܶΣ�20λ�����������ʱ���������
		if (ldappwbyte.length <= 20) {
			shacode = ldappwbyte;
			salt = new byte[0];
		} else {
			shacode = new byte[20];
			salt = new byte[ldappwbyte.length - 20];
			System.arraycopy(ldappwbyte, 0, shacode, 0, 20);
			System.arraycopy(ldappwbyte, 20, salt, 0, salt.length);
		}

		// ���û������������ӵ�ժҪ������Ϣ
		md.update(inputpw.getBytes());
		// �����������ӵ�ժҪ������Ϣ
		md.update(salt);

		// ��SSHA�ѵ�ǰ�û�������м���
		byte[] inputpwbyte = md.digest();

		// ����У����
		return MessageDigest.isEqual(shacode, inputpwbyte);
	}

	// ����û�
	public static boolean addUserLdap(String account, String password) {
		boolean success = false;
		try {
			ctx = LdapUtil.getLoginContext();
			BasicAttributes attrsbu = new BasicAttributes();
			BasicAttribute objclassSet = new BasicAttribute("objectclass");
			objclassSet.add("person");
			objclassSet.add("top");
			objclassSet.add("organizationalPerson");
			objclassSet.add("inetOrgPerson");
			attrsbu.put(objclassSet);
			attrsbu.put("sn", account);
			attrsbu.put("uid", account);
			attrsbu.put("userPassword", password);
			ctx.createSubcontext("cn=" + account + ",ou=People", attrsbu);
			ctx.close();
			return true;
		} catch (NamingException ex) {
			try {
				if (ctx != null) {
					ctx.close();
				}
			} catch (NamingException namingException) {
				namingException.printStackTrace();
			}
			logger.info("--------->>����û�ʧ��");
		}
		return false;
	}

	// �޸�����
	public static boolean updatePasswordLdap(String account, String password) {
		boolean success = false;
		try {
			ctx = LdapUtil.getLoginContext();
			ModificationItem[] modificationItem = new ModificationItem[1];
			modificationItem[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", password));
			ctx.modifyAttributes("cn=" + account + ",ou=People", modificationItem);
			ctx.close();
			return true;
		} catch (NamingException ex) {
			try {
				if (ctx != null) {
					ctx.close();
				}
			} catch (NamingException namingException) {
				namingException.printStackTrace();
			}
			logger.info("--------->>�޸�����ʧ��");
		}
		return success;
	}

	// ɾ���û�
	public static boolean deleteUserLdap(String account) {
		try {
			ctx = LdapUtil.getLoginContext();
			ctx.destroySubcontext("cn=" + account);
		} catch (Exception ex) {
			try {
				if (ctx != null) {
					ctx.close();
				}
			} catch (NamingException namingException) {
				namingException.printStackTrace();
			}
			logger.info("--------->>ɾ���û�ʧ��");
			return false;
		}
		return true;
	}

	// �ر�LDAP����������
	public static void closeCtx() {
		try {
			ctx.close();
		} catch (NamingException ex) {
			logger.info("--------->> �ر�LDAP����ʧ��");
		}
	}
}