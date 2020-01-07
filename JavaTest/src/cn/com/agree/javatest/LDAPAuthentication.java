package cn.com.agree.javatest;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
  
public class LDAPAuthentication {
    private final String URL = "ldap://127.0.0.1:389/";
    private final String BASEDN = "ou=Tester,dc=alexia,dc=cn";  // �����Լ���������޸�
    private final String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private LdapContext ctx = null;
    private final Control[] connCtls = null;
  
    private void LDAP_connect() {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY);
        env.put(Context.PROVIDER_URL, URL + BASEDN);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
          
        String root = "cn=manager,dc=alexia,dc=cn";  // ���������Լ�����޸�
        env.put(Context.SECURITY_PRINCIPAL, root);   // ����Ա
        env.put(Context.SECURITY_CREDENTIALS, "123456");  // ����Ա����
         
        try {
            ctx = new InitialLdapContext(env, connCtls);
            System.out.println( "��֤�ɹ�" ); 
             
        } catch (javax.naming.AuthenticationException e) {
            System.out.println("��֤ʧ�ܣ�");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("��֤����");
            e.printStackTrace();
        }
         
        if (ctx != null) {
            try {
                ctx.close();
            }
            catch (NamingException e) {
                e.printStackTrace();
            }
 
        }
    }
  
    private String getUserDN(String uid) {
        String userDN = "";
        LDAP_connect();
        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> en = ctx.search("", "uid=" + uid, constraints);
            if (en == null || !en.hasMoreElements()) {
                System.out.println("δ�ҵ����û�");
            }
            // maybe more than one element
            while (en != null && en.hasMoreElements()) {
                Object obj = en.nextElement();
                if (obj instanceof SearchResult) {
                    SearchResult si = (SearchResult) obj;
                    userDN += si.getName();
                    userDN += "," + BASEDN;
                } else {
                    System.out.println(obj);
                }
            }
        } catch (Exception e) {
            System.out.println("�����û�ʱ�����쳣��");
            e.printStackTrace();
        }
  
        return userDN;
    }
  
    public boolean authenricate(String UID, String password) {
        boolean valide = false;
        String userDN = getUserDN(UID);
  
        try {
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, userDN);
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
            ctx.reconnect(connCtls);
            System.out.println(userDN + " ��֤ͨ��");
            valide = true;
        } catch (AuthenticationException e) {
            System.out.println(userDN + " ��֤ʧ��");
            System.out.println(e.toString());
            valide = false;
        } catch (NamingException e) {
            System.out.println(userDN + " ��֤ʧ��");
            valide = false;
        }
  
        return valide;
    }
     
    public static void main(String[] args) {
        LDAPAuthentication ldap = new LDAPAuthentication();
 
        if(ldap.authenricate("gygtest", "jmwang") == true){
 
            System.out.println( "���û���֤�ɹ�" );
 
        }
    }
}