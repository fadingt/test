package cn.com.agree.utils;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.ContextNotEmptyException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

import cn.com.agree.config.LDAPConfig;
import cn.com.agree.domain.UserDO;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class LDAPUtils {
    private String BASEDN = null; // 根据自己情况进行修改
    private final String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    private LdapContext ctx = null;
    private final Control[] connCtls = null;
    private Map<String, String> de_map;
    private static int pageNo = 0;
//    private String allUsersFileName = null;

    public LDAPUtils() throws IOException, SQLException, ClassNotFoundException {
        String sql = JDBCUtils.makeSQL(new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\org.sql"));
        this.de_map = JDBCUtils.getOrgMap(sql);
        this.BASEDN = "dc=agree,dc=com";
    }

/*    public LDAPHelper(String deMapFileName, String allUsersFileName, boolean bHAS_HEADER_FLAG) {
        ExcelRead.setHAS_HEADER_FLAG(bHAS_HEADER_FLAG);
        getDeMapInfo(deMapFileName);
        this.allUsersFileName = allUsersFileName;
    }*/

    private void LDAP_connect(LDAPConfig config) {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY);
        env.put(Context.PROVIDER_URL, config.getURL() + config.getBASEDN());
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, config.getRoot()); // 管理员
        env.put(Context.SECURITY_CREDENTIALS, config.getRootpwd()); // 管理员密码
        try {
            ctx = new InitialLdapContext(env, null);
            System.out.println("连接成功");
        } catch (javax.naming.AuthenticationException e) {
            System.out.println("连接失败:" + config);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("连接出错");
            e.printStackTrace();
        }

    }


    private boolean isConnect() {
        if (ctx == null) {
            return false;
        }
        return true;
    }

    private void closeContext() {
        if (ctx != null) {
            try {
                ctx.close();
                System.out.println("断开连接成功");
            } catch (NamingException e) {
                e.printStackTrace();
            }

        }
    }

    private void reConnectRoot(LDAPConfig config) {
        if (isConnect()) {
            try {
                ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, config.getRoot());
                ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, config.getRootpwd());
                ctx.reconnect(connCtls);
            } catch (NamingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private String getUserDN(String uid) {
        String userDN = "";
        if (!isConnect()) {
            //TODO 自定义异常类
            System.out.println("getUserDN()时 LDAP未连接");
            return userDN;
        }
        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> en = ctx.search("", "uid=" + uid, constraints);
/*            if (en == null || !en.hasMoreElements()) {
                //TODO catch exception
                System.out.println("未找到用户uid=" + uid);
            }*/
            // maybe more than one element
            while (en != null && en.hasMoreElements()) {
                Object obj = en.nextElement();
                if (obj instanceof SearchResult) {
                    SearchResult si = (SearchResult) obj;
                    userDN += si.getName();
                    userDN += "," + BASEDN;
                } else {
                    //todo
                }
            }
        } catch (NamingException e) {
            //TODO
            e.printStackTrace();
        }
        return userDN;
    }

    private String getDepartmentDN(String ouName, String pathParent) {
        String ouDN = "";
        if (!isConnect()) {
            return ouDN;
        }
        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            if (pathParent != "")
                pathParent = pathParent.substring(1);
            NamingEnumeration<SearchResult> en = ctx.search(pathParent, "ou=" + ouName, constraints);

            if (en == null || !en.hasMoreElements()) {
                //System.out.println("未找到该用户");
            }
            // maybe more than one element
            while (en != null && en.hasMoreElements()) {
                Object obj = en.nextElement();
                if (obj instanceof SearchResult) {
                    SearchResult si = (SearchResult) obj;
                    ouDN += si.getName();
                    ouDN += "," + BASEDN;
                } else {
                    //System.out.println(obj);
                }
            }
        } catch (Exception e) {
            System.out.println("查找用户时产生异常。");
            e.printStackTrace();
        }

        return ouDN;
    }

    public boolean authenricate(String UID, String password) {
        boolean valide = false;
        if (!isConnect()) {
            return valide;
        }
//		LDAP_connect();
        String userDN = getUserDN(UID);

        try {
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, userDN);
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
            ctx.reconnect(connCtls);
            System.out.println(userDN + " 验证通过");
            valide = true;
            reConnectRoot(new LDAPConfig(new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\ldap.properties")));//切换回管理员用户
        } catch (AuthenticationException e) {
            System.out.println(userDN + " 验证失败");
            System.out.println(e.toString());
            valide = false;
        } catch (NamingException e) {
            System.out.println(userDN + " 验证失败");
            valide = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
//		closeContext();
        return valide;
    }

    /**
     * 删除用户
     *
     * @param dn
     */
    public boolean deleteUser(String dn) {
        try {
            ctx.destroySubcontext("uid=" + dn);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in deleteUser()" + dn + ":\t" + e);
        }
        return false;
    }


    /**
     * 删除指定目录下所有用户
     *
     * @param dir
     */
    public boolean deleteAllUsers(String dir) {
        if (!isConnect()) {
            return false;
        }

        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration<SearchResult> en = ctx.search("", "objectClass=inetOrgPerson", constraints);

            int count = 0;
            // maybe more than one element
            while (en != null && en.hasMoreElements()) {
                Object obj = en.nextElement();
                if (obj instanceof SearchResult) {
                    SearchResult si = (SearchResult) obj;
                    String dn = si.getName();
                    if (dn.indexOf(dir) != -1) {
                        count++;
                        ctx.destroySubcontext(dn);
                    }
                }
            }
            System.out.println("deleteAllUsers() count=" + count);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in delete():" + e);
        }

        return false;
    }


    public ArrayList<String> getOUNames(String dir) {
        ArrayList<String> deps = new ArrayList<String>();
        try {
            String ou = "";
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> en = ctx.search("", "objectClass=organizationalUnit", constraints);
            // maybe more than one element
            while (en != null && en.hasMoreElements()) {
                Object obj = en.nextElement();
                if (obj instanceof SearchResult) {
                    SearchResult si = (SearchResult) obj;
                    String dn = si.getName();
                    if (dn.indexOf(dir) != -1) {
//						System.out.println("dn:"+dn);
                        deps.add(dn);
                    }
                }
            }
//			System.out.println("count="+count);
            return deps;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void deleteOUEntry(ArrayList<String> deps) {
        Iterator<String> iterator = deps.iterator();
        while (iterator.hasNext()) {
            String dn = iterator.next();
            try {
                ctx.destroySubcontext(dn);
                iterator.remove();// 推荐使用
//                System.out.println("delete ou success! dn:" + dn);
            } catch (ContextNotEmptyException e) {
//	            e.printStackTrace();
            } catch (NamingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (deps.size() > 0) {
            deleteOUEntry(deps);//递归
        }
    }

    /**
     * 删除指定目录下所有部门
     *
     * @param dir
     */
    public boolean deleteAllDeps(String dir) {
        if (!isConnect()) {
            return false;
        }

        ArrayList<String> deps = getOUNames(dir);
        if (deps != null) {
            deleteOUEntry(deps);
        }

        return true;
    }

    public boolean createDepartment(String path, int index) {
        if (!isConnect()) {
            return false;
        }
        String pathParent = "";
        String ouName = "";
        String ouNo = "";
        try {
            String[] paths = path.split(",");
            if (index >= paths.length) {
                return true;
            }

            for (int i = paths.length - index; i < paths.length; i++) {
                pathParent += ",ou=" + de_map.get(paths[i].substring(3));
            }

            ouNo = paths[paths.length - 1 - index].substring(3);
            ouName = de_map.get(ouNo);

            if (getDepartmentDN(ouName, pathParent) == "") {
                BasicAttributes attrsbu = new BasicAttributes();
                BasicAttribute objclassSet = new BasicAttribute("objectclass");
                objclassSet.add("top");
                objclassSet.add("organizationalUnit");
                attrsbu.put(objclassSet);
                attrsbu.put("ou", ouName);
//                attrsbu.put("userPassword", pwd);
//                String description = ouName;
                attrsbu.put("description", ouNo);
                String ouDN = "ou=" + ouName + pathParent;
                System.out.println("ouDN" + ouDN + "\tattrsbu");
                ctx.createSubcontext(ouDN, attrsbu);
            }
            int newIndex = index + 1;
            createDepartment(path, newIndex);
            return true;

        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean SyncDept2Ldap(String path, int index) {
        if (!isConnect()) {
            return false;
        }
        String pathParent = "";
        String ouName = "";
        String ouNo = "";
        try {
            String[] paths = path.split(",");
            if (index >= paths.length) {
                return true;
            }

            for (int i = paths.length - index; i < paths.length; i++) {
                pathParent += ",ou=" + de_map.get(paths[i].substring(3));
            }

            ouNo = paths[paths.length - 1 - index].substring(3);
            ouName = de_map.get(ouNo);

            if (getDepartmentDN(ouName, pathParent) == "") {
                BasicAttributes attrsbu = new BasicAttributes();
                BasicAttribute objclassSet = new BasicAttribute("objectclass");
                objclassSet.add("top");
                objclassSet.add("organizationalUnit");
                attrsbu.put(objclassSet);
                attrsbu.put("ou", ouName);
                //			attrsbu.put("userPassword", pwd);
//				String description = ouName;
                attrsbu.put("description", ouNo);
                String ouDN = "ou=" + ouName + pathParent;
                ctx.createSubcontext(ouDN, attrsbu);
            }
            int newIndex = index + 1;
            createDepartment(path, newIndex);
            return true;

        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean addUser(String path, String pwd, String cn, String uid) {
        try {
            if (!isConnect()) {
                return false;
            }
            if (getUserDN(uid) == "") {
                BasicAttributes attrsbu = new BasicAttributes();
                BasicAttribute objclassSet = new BasicAttribute("objectclass");
                //objclassSet.add("employeeNumber");
                objclassSet.add("person");
                objclassSet.add("top");
                objclassSet.add("organizationalPerson");
                objclassSet.add("inetOrgPerson");
                attrsbu.put(objclassSet);
                attrsbu.put("sn", cn);
                attrsbu.put("cn", cn);
                attrsbu.put("displayName", cn);
                attrsbu.put("uid", uid);
                attrsbu.put("userPassword", pwd);
                attrsbu.put("description", uid);

                //	        // int UF_ACCOUNTDISABLE = 0x0002;
                //	        int UF_PASSWD_NOTREQD = 0x0020;
                //	        // int UF_PASSWD_CANT_CHANGE = 0x0040;
                //	        int UF_NORMAL_ACCOUNT = 0x0200;
                //	        int UF_DONT_EXPIRE_PASSWD = 0x10000;
                //	        // int UF_PASSWORD_EXPIRED = 0x800000;

                //	        attrsbu.put("givenName", "aaa");
                //	        attrsbu.put("displayName", "bbb");
                //	        attrsbu.put("mail", "gaolei@agree.com.cn");
                //	        attrsbu.put("userPrincipalName", "fancionwang@wilcom.com.cn");
                //	        attrsbu.put("sAMAccountName", "N2073");
                //	        attrsbu.put("employeeID", "N2073");
                //	        attrsbu.put("msDS-SupportedEncryptionTypes", "0");

                /** 设置传真 */
                //	        attrsbu.put("facsimileTelephoneNumber", "gaolei.fax.wiocom.com.cn");
                //	        /** 寻呼机 */
                //	        attrsbu.put("pager", "****");
                //	        /** ip电话 */
                //	        attrsbu.put("ipPhone", "****");
                //	        /** 家庭电话 */
                //	        attrsbu.put("homePhone", "********");
                //	        /** 移动电话 */
                //	        attrsbu.put("telephoneNumber", "***********");

                /** 设置账户信息 */
                //	        attrsbu.put("userAccountControl",
                //	                Integer.toString(UF_DONT_EXPIRE_PASSWD + UF_NORMAL_ACCOUNT + UF_PASSWD_NOTREQD));

                String[] paths = path.split(",");
                String pathParent = "";
                for (int i = 0; i < paths.length; i++) {
                    pathParent += ",ou=" + de_map.get(paths[i].substring(3));
                }

                String userDN = "uid=" + uid + pathParent;
                ctx.createSubcontext(userDN, attrsbu);

                return true;
            } else {
                return true;
            }
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean addUser(UserDO user) {
        String uid = user.getUserid() + "";
        String pwd = user.getPassword();
        String cn = "cn";
        String path = user.getOrgcode();
        try {
            if (!isConnect()) {
                return false;
            }
            if (getUserDN(uid) == "") {
                BasicAttributes attrsbu = new BasicAttributes();
                BasicAttribute objclassSet = new BasicAttribute("objectclass");
                objclassSet.add("person");
                objclassSet.add("top");
                objclassSet.add("organizationalPerson");
                objclassSet.add("inetOrgPerson");
                attrsbu.put(objclassSet);
                attrsbu.put("sn", cn);
                attrsbu.put("cn", cn);
                attrsbu.put("displayName", cn);
                attrsbu.put("uid", uid);
                attrsbu.put("userPassword", pwd);
                attrsbu.put("description", uid);
                String[] paths = path.split(",");
                String pathParent = "";
                for (int i = 0; i < paths.length; i++) {
                    pathParent += ",ou=" + de_map.get(paths[i].substring(3));
                }

                String userDN = "uid=" + uid + pathParent;
                ctx.createSubcontext(userDN, attrsbu);

                return true;
            } else {
                return true;
            }
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 修改
     *
     * @return
     */
    public boolean modifyInformation(String dn, String attrName, String attrValue) {
        try {
            System.out.println("updating...\n");
            ModificationItem[] mods = new ModificationItem[1];
            /* 修改属性 */
            Attribute attr0 = new BasicAttribute(attrName, attrValue);
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr0);
            /* 删除属性 */
            // Attribute attr0 = new BasicAttribute("description",
            // "陈轶");
            // mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
            // attr0);
            /* 添加属性 */
            //Attribute attr0 = new BasicAttribute(attrName, attrValue);
            //mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attr0);
            /* 修改属性 */
            ctx.modifyAttributes(dn, mods);
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * @param base   ：根节点(在这里是"dc=agree,dc=com")
     * @param scope  ：搜索范围,分为"base"(本节点),"one"(单层),""(遍历)
     * @param filter ：指定子节点(格式为"(objectclass=*)",*是指全部，你也可以指定某一特定类型的树节点)
     */
    public void searchInformation(String base, String scope, String filter) {
        SearchControls sc = new SearchControls();
        if (scope.equals("base")) {
            sc.setSearchScope(SearchControls.OBJECT_SCOPE);
        } else if (scope.equals("one")) {
            sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        } else {
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        }

        //sc.setReturningAttributes(null); // 不定制属性，将返回所有的属性集
        NamingEnumeration<SearchResult> ne = null;
        try {
            ne = ctx.search(base, filter, sc);
            // Use the NamingEnumeration object to cycle through
            // the result set.
            int index = 0;
            while (ne.hasMore()) {
                index++;
                System.out.println("index=" + index);
                SearchResult sr = (SearchResult) ne.next();
                String name = sr.getName();
                if (base != null && !base.equals("")) {
                    System.out.println("entry: " + name + "," + base);
                } else {
                    System.out.println("entry: " + name);
                }

                Attributes at = sr.getAttributes();
                NamingEnumeration ane = at.getAll();
                while (ane.hasMore()) {
                    Attribute attr = (Attribute) ane.next();
                    String attrType = attr.getID();
                    NamingEnumeration values = attr.getAll();
                    Vector vals = new Vector();
                    // Another NamingEnumeration object, this time
                    // to iterate through attribute values.
                    while (values.hasMore()) {
                        Object oneVal = values.nextElement();
                        if (oneVal instanceof String) {
                            System.out.println(attrType + ": "
                                    + (String) oneVal);
                        } else {
                            System.out.println(attrType + ": "
                                    + new String((byte[]) oneVal));
                        }
                    }
                }
            }
        } catch (Exception nex) {
            System.err.println("Error: " + nex.getMessage());
            nex.printStackTrace();
        }
    }

    static byte[] parseControls(Control[] controls) throws NamingException {
        byte[] cookie = null;
        if (controls != null) {
            for (int i = 0; i < controls.length; i++) {
                if (controls[i] instanceof PagedResultsResponseControl) {

                    PagedResultsResponseControl prrc =
                            (PagedResultsResponseControl) controls[i];

                    cookie = prrc.getCookie();

                    System.out.println(">>Next Page \n" + pageNo++);
                }

            }
        }
        return (cookie == null) ? new byte[0] : cookie;
    }

    /**
     * 查询
     *
     * @throws IOException
     * @throws NamingException
     */
    public void Ldapbyuserinfo(String userName) throws IOException, NamingException {
        // Create the search controls
        SearchControls searchCtls = new SearchControls();
        // Specify the search scope
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        // specify the LDAP search filter
//        String searchFilter = "uid=" + userName;
        String searchFilter = "(objectClass=*)";
        // Specify the Base for the search 搜索域节点
        String searchBase = "";
        int totalResults = 0;
//        String returnedAtts[] = { "url", "whenChanged", "employeeID", "name",
//                "userPrincipalName", "physicalDeliveryOfficeName",
//                "departmentNumber", "telephoneNumber", "homePhone", "mobile",
//                "department", "sAMAccountName", "whenChanged", "mail" }; // 定制返回属性
        String returnedAtts[] = {"displayName", "description"}; // 定制返回属性

        searchCtls.setReturningAttributes(returnedAtts); // 设置返回属性集

//         searchCtls.setReturningAttributes(null); // 不定制属性，将返回所有的属性集
        searchCtls.setCountLimit(10000);
//         System.out.println("searchCtls.getCountLimit()="+searchCtls.getCountLimit());

        int index = 0;
        int pageSize = 10;
        byte[] cookie = null;
        //Request the paged results control
        Control[] ctls = new Control[]{new PagedResultsControl(pageSize, Control.CRITICAL)};
        ctx.setRequestControls(ctls);

        try {
            do {
                NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter,
                        searchCtls);
                if (answer == null || answer.equals(null)) {
                    System.out.println("answer is null");
                } else {
                    System.out.println("answer not null");
                }
                while (answer.hasMoreElements()) {
                    SearchResult sr = (SearchResult) answer.next();
                    System.out
                            .println("************************************************");
                    System.out.println("index=" + index++);
                    System.out.println("getname=" + sr.getName());
                    Attributes Attrs = sr.getAttributes();
                    if (Attrs != null) {
                        try {

                            for (NamingEnumeration ne = Attrs.getAll(); ne
                                    .hasMore(); ) {
                                Attribute Attr = (Attribute) ne.next();
                                //                            System.out.print(""
                                //                                    + Attr.getID().toString());
                                // 读取属性值
                                for (NamingEnumeration e = Attr.getAll(); e
                                        .hasMore(); totalResults++) {
                                    String user = e.next().toString(); // 接受循环遍历读取的userPrincipalName用户属性
                                    //                                System.out.println(user);
                                }
                                // System.out.println(" ---------------");
                                // // 读取属性值
                                // Enumeration values = Attr.getAll();
                                // if (values != null) { // 迭代
                                // while (values.hasMoreElements()) {
                                // System.out.println(" 2AttributeValues="
                                // + values.nextElement());
                                // }
                                // }
                                // System.out.println(" ---------------");
                            }
                        } catch (NamingException e) {
                            System.err.println("Throw Exception : " + e);
                        }
                    }
                }
                // examine the response controls

                cookie = parseControls(ctx.getResponseControls());
                // pass the cookie back to the server for the next page

                ctx.setRequestControls(new Control[]{new
                        PagedResultsControl(pageSize, cookie, Control.CRITICAL)});

            } while ((cookie != null) && (cookie.length != 0));

            System.out.println("Number: " + totalResults);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Throw Exception : " + e);
        }
    }

    // 修改密码
    public boolean updateUserPassword(String uid, String pwd) {
        boolean success = false;
        try {
            ModificationItem[] modificationItem = new ModificationItem[1];
            modificationItem[0] = new ModificationItem(
                    DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(
                    "userPassword", pwd));
            ctx.modifyAttributes("uid=" + uid, modificationItem);
            return true;
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        return success;
    }

    public static String fillMD5(String md5) {
        return md5.length() == 32 ? md5 : fillMD5("0" + md5);
    }

    public static String Encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance(encName);
//             md.update(bt);
            //System.out.println("md5:"+md.digest().toString());
//             String md5=new BigInteger(1, md.digest()).toString(16);
//             md5 = fillMD5(md5);
//            System.out.println("strSrc:" + strSrc);

            //strDes = Base64.encode(md.digest());
            strDes = Base64.encode(toBytes(strSrc));

//            System.out.println("strDes:" + strDes);
            if (encName.substring(0, 3) == "MD5") {
                strDes = "{MD5}" + strDes;
            } else {
                strDes = "{SHA}" + strDes;
            }
            //strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            System.out.println("签名失败！");
            return null;
        }
        return strDes;
    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String bytes2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    // 将输入用户和密码进行加密算法后验证
    public static boolean verifySHA(String ldappw, String inputpw) throws NoSuchAlgorithmException {

        // MessageDigest 提供了消息摘要算法，如 MD5 或 SHA，的功能，这里LDAP使用的是SHA-1
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        // 取出加密字符
        if (ldappw.startsWith("{SSHA}")) {
            ldappw = ldappw.substring(6);
        } else if (ldappw.startsWith("{SHA}")) {
            ldappw = ldappw.substring(5);
        }

        // 解码BASE64
        byte[] ldappwbyte = Base64.decode(ldappw);
        byte[] shacode;
        byte[] salt;

        // 前20位是SHA-1加密段，20位后是最初加密时的随机明文
        if (ldappwbyte.length <= 20) {
            shacode = ldappwbyte;
            salt = new byte[0];
        } else {
            shacode = new byte[20];
            salt = new byte[ldappwbyte.length - 20];
            System.arraycopy(ldappwbyte, 0, shacode, 0, 20);
            System.arraycopy(ldappwbyte, 20, salt, 0, salt.length);
        }

        // 把用户输入的密码添加到摘要计算信息
        md.update(inputpw.getBytes());
        // 把随机明文添加到摘要计算信息
        md.update(salt);

        // 按SSHA把当前用户密码进行计算
        byte[] inputpwbyte = md.digest();

        // 返回校验结果
        return MessageDigest.isEqual(shacode, inputpwbyte);
    }

    /**
     * 重命名节点
     *
     * @param oldDN
     * @param newDN
     * @return
     */
    public boolean renameEntry(String oldDN, String newDN) {
        try {
            ctx.rename(oldDN, newDN);
            return true;
        } catch (NamingException ne) {
            System.err.println("Error: " + ne.getMessage());
            return false;
        }
    }

/*    private void getDeMapInfo(String fileName) {
        if (de_map == null)
            de_map = ExcelRead.readDEMapFromExcel(fileName);
//    		de_map = ExcelRead.readDEMapFromExcel("e:\\eclipse\\doc\\bumen_0903.xls");
//		    de_map_reverse = new HashMap<>();
//    		for(Entry<String, String> en : de_map.entrySet()){
////		    	System.out.println(en.getKey()+ "=" + en.getValue());
//		    	de_map_reverse.put(en.getValue(), en.getKey());
//		    }
    }*/

    public void sync2Ldap(List<UserDO> userlist) {
        int count = 1;
        for (UserDO user : userlist) {
            String path = parsePath(user.getOrgcode());
            this.createDepartment(path, 0);
//            TODO this.addUser(USER user)
            boolean bAdd = this.addUser(path, LDAPUtils.Encrypt(user.getPassword(), "MD5"), user.getUsername(), user.getUserid() + "");
            if (!bAdd) break;
//            TODO LOG IF addUser unsecussessfully
            count++;
        }
        System.out.println("sync2Ldap add user:"+count);
    }

    public String replacePath(String path) {//ou=0,ou=20000,ou=30833,ou=30835,ou=30843
        String ret = "";
        String[] paths = path.substring(1).split("\\|");
        for (int i = 0; i < paths.length; i++) {
            //System.out.println(paths[i]);
            ret = "ou=" + de_map.get(paths[i]) + "," + ret;
//            ret = "ou="+paths[i]+","+ret;
        }
        return ret;
    }

    public String parsePath(String path) {//|0|20000|30833|30835|30843|
        String ret = "";
        String[] paths = path.substring(1).split("\\|");
        for (int i = 0; i < paths.length; i++) {
            //System.out.println(paths[i]);
//            ret = "ou="+de_map.get(paths[i])+","+ret;            
            ret = "ou=" + paths[i] + "," + ret;
        }
        return ret;
    }

    // 测试
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, NamingException {
//    	String URL = "ldap://192.168.180.247:389/";
//        String deMapFileName = "C:\\Users\\Administrator\\Downloads\\org20191227.xls";
//        String allUsersFileName = "C:\\Users\\Administrator\\Downloads\\user20191227.xls";
//        boolean bHAS_HEADER_FLAG = true;
//        boolean bFirst = true;
        LDAPUtils ldap = new LDAPUtils();
//        LDAPHelper ldap = new LDAPHelper(deMapFileName, allUsersFileName, bHAS_HEADER_FLAG);
        LDAPConfig config = new LDAPConfig(new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\ldap.properties"));
        ldap.LDAP_connect(config);
//        System.out.println("first delete all");
//        ldap.deleteAllUsers("ou=赞同");
//        ldap.deleteAllDeps("ou=赞同");
//        System.out.println("second createTree");
//        String sql = new JDBCUtils().makeSQL(new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\user.sql"));
//        ldap.sync2Ldap(JDBCUtils.getUserList(sql));

        if (ldap.authenricate("-2", "agree123.com")) {
            System.out.println("test1 认证成功");
        }
//        if (ldap.authenricate("13787", "agree123")) {
//            System.out.println("田田 认证成功");
//        }
//        if (ldap.authenricate("-2","")){
//            System.out.println("-2");
//        }
//        ldap.closeContext();
    }
}