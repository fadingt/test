package cn.com.agree.utils;

import java.io.File;
import java.io.IOException;
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

import cn.com.agree.config.LDAPConfig;
import cn.com.agree.dao.UserDao;
import cn.com.agree.dao.UserDaoImpl;
import cn.com.agree.domain.User;


public class LDAPUtils {
    private String BASEDN; // 根据自己情况进行修改 dc=agree,dc=com
    private LdapContext ctx;
    private final Control[] connCtls = null;
    private Map<String, String> de_map;
    private UserDao userDao = new UserDaoImpl();

    public LDAPUtils() {
        String sql;
        try {
            sql = JDBCUtils.makeSQL(new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\org.sql"));
            this.de_map = userDao.getOrgMap(sql);
            this.BASEDN = "dc=agree,dc=com";
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void connect(LDAPConfig config) {
        Hashtable<String, String> env = new Hashtable<>();
        String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
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

    public boolean isConnect() {
        return ctx != null;
    }

    public void closeContext() {
        if (ctx != null) {
            try {
                ctx.close();
                System.out.println("断开连接成功");
            } catch (NamingException e) {
                e.printStackTrace();
            }

        }
    }

    public void reConnectRoot(LDAPConfig config) {
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

    public String getUserDN(String uid) {
        StringBuilder userDN = new StringBuilder();
        if (!isConnect()) {
            //TODO 自定义异常类
            System.out.println("getUserDN()时 LDAP未连接");
            return userDN.toString();
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
                SearchResult searchResult = en.nextElement();
                if (searchResult != null) {
                    userDN.append(searchResult.getName()).append(",").append(BASEDN);
                } else {
                    //todo
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return userDN.toString();
    }

    private String getDepartmentDN(String ouName, String pathParent) {
        StringBuilder ouDN = new StringBuilder();
        if (!isConnect()) {
            return ouDN.toString();
        }
        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            if (!pathParent.equals(""))
                pathParent = pathParent.substring(1);
            NamingEnumeration<SearchResult> en = ctx.search(pathParent, "ou=" + ouName, constraints);

            if (en == null || !en.hasMoreElements()) {
//                todo
                System.out.println("未找到该用户");
            }
            // maybe more than one element
            while (en != null && en.hasMoreElements()) {
                SearchResult searchResult = en.nextElement();
                if (searchResult != null) {
                    ouDN.append(searchResult.getName()).append(",").append(BASEDN);
                } else {
//                    todo
                    //System.out.println(obj);
                }
            }
        } catch (Exception e) {
            System.out.println("查找用户时产生异常。");
            e.printStackTrace();
        }

        return ouDN.toString();
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
     * @param dn user distinctName without BASEDN eg:uid=A6853,ou=xx,ou=xx，ou=赞同
     */
    public boolean deleteUser(String dn) {
        try {
            ctx.destroySubcontext(dn);
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
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
                SearchResult obj = en.nextElement();
                if (obj != null) {
                    String dn = obj.getName();
                    if (dn.contains(dir)) {
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
        ArrayList<String> deps = new ArrayList<>();
        try {
            String ou = "";
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> en = ctx.search("", "objectClass=organizationalUnit", constraints);
            // maybe more than one element
            while (en != null && en.hasMoreElements()) {
                SearchResult obj = en.nextElement();
                if (obj != null) {
                    String dn = obj.getName();
                    if (dn.contains(dir)) {
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
        StringBuilder pathParent = new StringBuilder();
        String ouName;
        String ouNo;
        try {
            String[] paths = path.split(",");
            if (index >= paths.length) {
                return true;
            }

            for (int i = paths.length - index; i < paths.length; i++) {
                pathParent.append(",ou=").append(de_map.get(paths[i].substring(3)));
            }

            ouNo = paths[paths.length - 1 - index].substring(3);
            ouName = de_map.get(ouNo);

            if (getDepartmentDN(ouName, pathParent.toString()).equals("")) {
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
        StringBuilder pathParent = new StringBuilder();
        String ouName;
        String ouNo;
        try {
            String[] paths = path.split(",");
            if (index >= paths.length) {
                return true;
            }

            for (int i = paths.length - index; i < paths.length; i++) {
                pathParent.append(",ou=").append(de_map.get(paths[i].substring(3)));
            }

            ouNo = paths[paths.length - 1 - index].substring(3);
            ouName = de_map.get(ouNo);

            if (getDepartmentDN(ouName, pathParent.toString()).equals("")) {
                BasicAttributes attrsbu = new BasicAttributes();
                BasicAttribute objclassSet = new BasicAttribute("objectclass");
                objclassSet.add("top");
                objclassSet.add("organizationalUnit");
                attrsbu.put(objclassSet);
                attrsbu.put("ou", ouName);
//                			attrsbu.put("userPassword", pwd);
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

    /**
     * @param user
     * @return true if success
     */
    public boolean addUser(User user) {
        if (user.getUsercode() == null) {
            System.out.println("usercode is null");
            return false;
        } else if (user.getPassword() == null) {
            System.out.println("user password is null");
            return false;
        } else if (user.getOrgcode() == null) {
            System.out.println("user orgcode is null");
            return false;
        }
        String uid = user.getUsercode();
        String pwd = EncryptUtils.Encrypt(user.getPassword(), "MD5");
        String cn = user.getUsername();
        String path = new UserDaoImpl().parseOrgPath(user);
        if (!isConnect()) {
            return false;
        }
        try {
            if (getUserDN(uid).equals("")) {
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
                StringBuilder pathParent = new StringBuilder();
                for (String s : paths) {
                    pathParent.append(",ou=").append(de_map.get(s.substring(3)));
                }
                String userDN = "uid=" + uid + pathParent;
                ctx.createSubcontext(userDN, attrsbu);

                return true;
            } else {
                System.out.println(user.getUsername()+"-"+user.getUsercode()+" exists");
                return false;
            }
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

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
     * @param scope  ：搜索范围:<br>
     *               0本节点: {@link SearchControls#OBJECT_SCOPE}<br>
     *               1单层:{@link SearchControls#ONELEVEL_SCOPE}<br>
     *               2遍历:{@link SearchControls#SUBTREE_SCOPE}
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
        NamingEnumeration<SearchResult> ne;
        try {
            ne = ctx.search(base, filter, sc);
//            Use the NamingEnumeration object to cycle through the result set.
            int index = 0;
            while (ne.hasMore()) {
                index++;
                System.out.println("index=" + index);
                SearchResult sr = ne.next();
                String name = sr.getName();
                if (base != null && !base.equals("")) {
                    System.out.println("entry: " + name + "," + base);
                } else {
                    System.out.println("entry: " + name);
                }

                Attributes attributes = sr.getAttributes();
                NamingEnumeration<? extends Attribute> ane = attributes.getAll();
                while (ane.hasMore()) {
                    Attribute attribute = ane.next();
                    String attrType = attribute.getID();
                    NamingEnumeration values = attribute.getAll();
                    Vector vals = new Vector();
                    // Another NamingEnumeration object, this time
                    // to iterate through attribute values.
                    while (values.hasMore()) {
                        Object oneVal = values.nextElement();
                        if (oneVal instanceof String) {
                            System.out.println(attrType + ": " + oneVal);
                        } else {
                            System.out.println(attrType + ": " + new String((byte[]) oneVal));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
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

    /**
     * 重命名节点
     *
     * @param oldDN
     * @param newDN
     * @return true if success
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

    public String replacePath(String path) {//ou=0,ou=20000,ou=30833,ou=30835,ou=30843
        StringBuilder ret = new StringBuilder();
        String[] paths = path.substring(1).split("\\|");
        for (String s : paths) {
            ret.insert(0, "ou=" + de_map.get(s) + ",");
        }
        return ret.toString();
    }

    public boolean deleteUser(User user) {
        String userDN;
        if (user.getUsercode() == null || "".equals(user.getUsercode())) {
            return false;
        }
        userDN = (getUserDN(user.getUsercode()).split("," + BASEDN)[0]);
        if ("".equals(userDN)) {
            return false;
        }
        try {
            ctx.destroySubcontext(userDN);
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws IOException, SQLException {
        UserDao userDao = new UserDaoImpl();
        LDAPUtils ldap = new LDAPUtils();
        LDAPConfig config = new LDAPConfig(new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\ldap.properties"));
//        LDAPConfig config = new LDAPConfig(new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\ldap_produce.properties"));
        ldap.connect(config);
//        ldap.deleteAllUsers("ou=赞同");
//        ldap.deleteAllDeps("ou=赞同");
//        List<String> usercodeList = new ArrayList<>();
//        usercodeList.add("A4521");
//        usercodeList.add("A2988");
//        List<User> userlist;
//        userlist = userDao.getUserListBYUsercode(usercodeList);

        String sql = JDBCUtils.makeSQL(new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\user.sql"));
        String conditions = " WHERE  userid >= 617007";
        sql = sql + conditions;
        List<User> userlist = userDao.getUserList(sql);

        ldap.updateUsers(ldap, userlist);
        ldap.closeContext();
    }

    public void updateUsers(LDAPUtils ldap, List<User> userlist) {
        int count = 0;
        boolean result;
        for (User user : userlist) {
            result = updateUser(ldap, user);
            if (!result) {
                System.out.println("update " + user.getUsername() + " failed!");
            } else {
                count++;
            }
        }
        System.out.println("total update " + count + " users");
    }

    public boolean updateUser(LDAPUtils ldap, User user) {
        if (user.getOrgcode() == null || user.getUsercode() == null) {
            return false;
        }
        UserDao userDao = new UserDaoImpl();
        ldap.deleteUser(user);
        String path = userDao.parseOrgPath(user);
        ldap.createDepartment(path, 0);
        return ldap.addUser(user);
    }
}