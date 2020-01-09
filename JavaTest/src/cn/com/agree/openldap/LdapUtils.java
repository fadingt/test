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
    public static Map<String,String> organizaCNName = new Hashtable<>();//组织机构中文名称
 
    //protected final static Logger logger = LoggerFactory.getLogger(LdapUtils.class);
    private static LdapContext dc = null;
	private static final Control[] connCtls = null;
//    private static final String LDAP_URL = "ldap://192.168.180.247:389/";// LDAP访问地址
    private static final String LDAP_URL = "ldap:/192.9.200.247：389/";//LDAP测试地址
    private static final String root = "dc=agree,dc=com";// LDAP的根节点的DC
    private static final String ADMINUSERNAME = "cn=root,dc=agree,dc=com";  // 用户名,就是dn，例如（uid=student1,ou=class1,ou=school1,dc=*,dc=com）
    private static final String ADMINPASSWORD = "123456";// 密码
 
    //base   ：根节点"dc=*,dc=com"
    //scope  ：搜索范围,分为"base"(本节点),"one"(单层),""(遍历)
    //filter ：指定子节点(格式为"(objectclass=*)",*是指全部，也可以制定具体类型 比如objectClass=organizationalUnit，查询的是部门信息
    //查询用户信息
    private static final String SEARCH_USER_BASE ="ou=赞同,dc=agree,dc=com";
    private static final String SEARCH_USER_SCOPE ="";//为空，则遍历当前选的条目里满足filter的所有类型的信息
//    private static final String SEARCH_USER_FILTER ="(objectClass=organizationalUnit)";//要加括号，查询属性名为objectClass，值为organizationalUnit
    private static final String SEARCH_USER_FILTER ="uid=A2073";//要加括号，查询属性名为objectClass，值为organizationalUnit
 
    /**
     * 初始化连接
     */
    public static void init() {
        Hashtable env = new Hashtable();
 
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, LDAP_URL + root);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ADMINUSERNAME);
        env.put(Context.SECURITY_CREDENTIALS, ADMINPASSWORD);
        try {
            dc = new InitialLdapContext(env,connCtls);// 初始化上下文
            //logger.info("初始化成功");
        } catch (javax.naming.AuthenticationException e) {
            //logger.error("初始化失败:"+e.getMessage());
        } catch (Exception e) {
            //logger.error("初始化出错:"+e.getMessage());
        }
    }
 
    /**
     * 关闭Ldap连接
     */
    public static void close() {
        if (dc != null) {
            try {
                dc.close();
            } catch (NamingException e) {
                //logger.error("Ldap服务关闭异常:"+e.getMessage());
            }
        }
    }
 
 
    /**
     *查询ldat服务器获取所有用户数据
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
        sc.setReturningAttributes(null); // 不定制属性，将返回所有的属性集
        NamingEnumeration ne = null;
        LdaptUserResult ldaptUserResult = null;
        List<LdaptUserResult> list = new ArrayList<LdaptUserResult>();
        try {
//            ne = dc.search(new LdapName(SEARCH_USER_BASE), SEARCH_USER_FILTER, sc);
            ne = dc.search("", SEARCH_USER_FILTER, sc);
            while (ne.hasMore()) {
                ldaptUserResult = new LdaptUserResult();
                SearchResult sr = (SearchResult) ne.next();
 
                String name = sr.getNameInNamespace();//这里的name可以获取当前条目的全路径
 
 
 
                Attributes at = sr.getAttributes();
                NamingEnumeration ane = at.getAll();
                while (ane.hasMore()) {
                    Attribute attr = (Attribute) ane.next();
                    String attrType = attr.getID();//attrType对应的是属性名
                    NamingEnumeration values = attr.getAll();
 
                    // Another NamingEnumeration object, this time
                    // to iterate through attribute values.
                    while (values.hasMore()) {
                        Object oneVal = values.nextElement();
                        System.out.println(attrType + ": "
                                + (String) oneVal);
                        //组装成对象
                        combinationUser(ldaptUserResult,attrType,oneVal);
                    }
                }
                list.add(ldaptUserResult);
            }
        } catch (Exception nex) {
            //logger.error("查询ldpat用户数据错误:"+nex.getMessage());
 
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
