package cn.com.agree.openldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.LdapName;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
 
import static java.util.Objects.nonNull;
 
/**
 * Created by ccl on 2018/6/20.
 */
public class LdapUtils {
    public static Map<String,String> organizaCNName = new Hashtable<>();//��֯������������
 
    //protected final static Logger logger = LoggerFactory.getLogger(LdapUtils.class);
    private static LdapContext dc = null;
	private static final Control[] connCtls = null;
//    private static final String LDAP_URL = "ldap://192.168.180.247:389/";// LDAP���ʵ�ַ
    private static final String LDAP_URL = "ldap:/192.9.200.247��389/";//LDAP���Ե�ַ
    private static final String root = "dc=agree,dc=com";// LDAP�ĸ��ڵ��DC
    private static final String ADMINUSERNAME = "cn=root,dc=agree,dc=com";  // �û���,����dn�����磨uid=student1,ou=class1,ou=school1,dc=*,dc=com��
    private static final String ADMINPASSWORD = "123456";// ����
 
    //base   �����ڵ�"dc=*,dc=com"
    //scope  ��������Χ,��Ϊ"base"(���ڵ�),"one"(����),""(����)
    //filter ��ָ���ӽڵ�(��ʽΪ"(objectclass=*)",*��ָȫ����Ҳ�����ƶ��������� ����objectClass=organizationalUnit����ѯ���ǲ�����Ϣ
    //��ѯ�û���Ϣ
    private static final String SEARCH_USER_BASE ="ou=��ͬ,dc=agree,dc=com";
    private static final String SEARCH_USER_SCOPE ="";//Ϊ�գ��������ǰѡ����Ŀ������filter���������͵���Ϣ
//    private static final String SEARCH_USER_FILTER ="(objectClass=organizationalUnit)";//Ҫ�����ţ���ѯ������ΪobjectClass��ֵΪorganizationalUnit
    private static final String SEARCH_USER_FILTER ="uid=A2073";//Ҫ�����ţ���ѯ������ΪobjectClass��ֵΪorganizationalUnit
 
    /**
     * ��ʼ������
     */
    public static void init() {
        Hashtable env = new Hashtable();
 
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, LDAP_URL + root);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ADMINUSERNAME);
        env.put(Context.SECURITY_CREDENTIALS, ADMINPASSWORD);
        try {
            dc = new InitialLdapContext(env,connCtls);// ��ʼ��������
            //logger.info("��ʼ���ɹ�");
        } catch (javax.naming.AuthenticationException e) {
            //logger.error("��ʼ��ʧ��:"+e.getMessage());
        } catch (Exception e) {
            //logger.error("��ʼ������:"+e.getMessage());
        }
    }
 
    /**
     * �ر�Ldap����
     */
    public static void close() {
        if (dc != null) {
            try {
                dc.close();
            } catch (NamingException e) {
                //logger.error("Ldap����ر��쳣:"+e.getMessage());
            }
        }
    }
 
 
    /**
     *��ѯldat��������ȡ�����û�����
     */
    public static List<LdaptUserResult> searchInformation() {
 
        SearchControls sc = new SearchControls();
        if (SEARCH_USER_SCOPE.equals("base")) {
            sc.setSearchScope(SearchControls.OBJECT_SCOPE);
        } else if (SEARCH_USER_SCOPE.equals("one")) {
            sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        } else {
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        }
        sc.setReturningAttributes(null); // ���������ԣ����������е����Լ�
        NamingEnumeration ne = null;
        LdaptUserResult ldaptUserResult = null;
        List<LdaptUserResult> list = new ArrayList<LdaptUserResult>();
        try {
//            ne = dc.search(new LdapName(SEARCH_USER_BASE), SEARCH_USER_FILTER, sc);
            ne = dc.search("", SEARCH_USER_FILTER, sc);
            while (ne.hasMore()) {
                ldaptUserResult = new LdaptUserResult();
                SearchResult sr = (SearchResult) ne.next();
 
                String name = sr.getNameInNamespace();//�����name���Ի�ȡ��ǰ��Ŀ��ȫ·��
 
 
 
                Attributes at = sr.getAttributes();
                NamingEnumeration ane = at.getAll();
                while (ane.hasMore()) {
                    Attribute attr = (Attribute) ane.next();
                    String attrType = attr.getID();//attrType��Ӧ����������
                    NamingEnumeration values = attr.getAll();
 
                    // Another NamingEnumeration object, this time
                    // to iterate through attribute values.
                    while (values.hasMore()) {
                        Object oneVal = values.nextElement();
                        System.out.println(attrType + ": "
                                + (String) oneVal);
                        //��װ�ɶ���
                        combinationUser(ldaptUserResult,attrType,oneVal);
                    }
                }
                list.add(ldaptUserResult);
            }
        } catch (Exception nex) {
            //logger.error("��ѯldpat�û����ݴ���:"+nex.getMessage());
 
        }
 
        return list;
    }
 
 
 
    private static void combinationUser(LdaptUserResult ldaptUserResult, String attrType,Object oneVal) {
        if(nonNull(oneVal)){
            switch (attrType){
                case "displayName":
                    ldaptUserResult.setDisplayName((String) oneVal);
                    break;
                case "uid":
                    ldaptUserResult.setUid((String) oneVal);
                    break;
                case "description":
                    ldaptUserResult.setDescription((String) oneVal);
                    break;
                default:
            }
        }
    }
 
 
    public static void main(String[] args) {
        LdapUtils.init();
        List<LdaptUserResult> list = LdapUtils.searchInformation();
        System.out.println("list.size()="+list.size());
        
        LdapUtils.close();
    }
}
