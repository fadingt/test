package utils;


import config.LDAPConfig;
import dao.UserDao;
import dao.impl.HibernateUserDaoImpl;
import domain.paasaom.Org;
import domain.paasaom.User;

import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;


/**
 * utils to sync users and organization
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/jndi/ldap/exceptions.html">How LDAP Error Codes Map to JNDI Exceptions(RFC 2251)</a>
 */
public class LDAPUtils {
    private LDAPConfig config;
    private LdapContext ctx;
    private final Control[] connCtls = null;
    private Map<String, String> de_map;
    private UserDao userDao = new HibernateUserDaoImpl();

    public void connect() {
        if(this.config==null){
            String sql;
            try {
                InputStream ldapStream = LDAPUtils.class.getClassLoader().getResourceAsStream("ldap_produce.properties");
                InputStream sqlStream = LDAPUtils.class.getClassLoader().getResourceAsStream("org.sql");
                this.config = new LDAPConfig(ldapStream);
                sql = FileUtils.readFile(sqlStream);
                this.de_map = userDao.getOrgMap(sql);
            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        Hashtable<String, String> env = new Hashtable<>();
        String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
        env.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY);
        env.put(Context.PROVIDER_URL, config.getURL() + config.getBASEDN());
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, config.getRoot());
        env.put(Context.SECURITY_CREDENTIALS, config.getRootpwd());
        try {
            ctx = new InitialLdapContext(env, null);
            System.out.println(this.config.getURL() + " connected.");
        } catch (javax.naming.AuthenticationException e) {
            System.out.println(this.config.getURL() + " connect failed.Please check LDAPConfig");
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

    public boolean isConnect() {
        return ctx != null;
    }

    public void close() {
        if (ctx != null) {
            try {
                ctx.close();
                System.out.println(this.config.getURL() + " disconnected.");
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
                e.printStackTrace();
            }
        }
    }

    /**
     * @param uid 员工工号
     * @return userDN
     */
    public String getUserDN(String uid) {
        StringBuilder userDN = new StringBuilder();
        if (!isConnect()) {
            connect();
        }
        try {
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> en = ctx.search("", "uid=" + uid, controls);
            while (en != null && en.hasMoreElements()) {
                SearchResult searchResult = en.nextElement();
                if (searchResult != null) {
                    userDN.append(searchResult.getName()).append(",").append(this.config.getBASEDN());
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
            connect();
        }
        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            if (!pathParent.equals("")){
                pathParent = pathParent.substring(1);
            }
            NamingEnumeration<SearchResult> en = ctx.search(pathParent, "ou=" + ouName, constraints);

            if (en == null || !en.hasMoreElements()) {
//                todo
                System.out.println("no ldap user");
            }
            // maybe more than one element
            while (en != null && en.hasMoreElements()) {
                SearchResult searchResult = en.nextElement();
                if (searchResult != null) {
                    ouDN.append(searchResult.getName()).append(",").append(this.config.getBASEDN());
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return ouDN.toString();
    }

    public boolean authenricate(String UID, String password) {
        if (!isConnect()) {
            connect();
        }
        boolean valide;
        String userDN;
        userDN = getUserDN(UID);
        try {
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, userDN);
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
            ctx.reconnect(connCtls);
            valide = true;
            reConnectRoot(this.config);
        } catch (NamingException e) {
            valide = false;
            e.printStackTrace();
        }
        System.out.println(UID + " authenricate " + (valide ? "success" : "fail"));
        return valide;
    }

    /**
     * @param dn ldapUser distinctName eg:uid=A6853,ou=xx,ou=xx
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

    public boolean deleteAllUsers(String dir) {
        if (!isConnect()) {
            connect();
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
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> en = ctx.search("", "objectClass=organizationalUnit", constraints);
            // maybe more than one element
            while (en != null && en.hasMoreElements()) {
                SearchResult obj = en.nextElement();
                if (obj != null) {
                    String dn = obj.getName();
                    if (dn.contains(dir)) {
                        deps.add(dn);
                    }
                }
            }
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
                iterator.remove();
//                System.out.println("delete ou success! dn:" + dn);
            } catch (ContextNotEmptyException e) {
//	            e.printStackTrace();
            } catch (NamingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (deps.size() > 0) {
            deleteOUEntry(deps);
        }
    }

    public boolean deleteAllDeps(String dir) {
        if (!isConnect()) {
            connect();
        }
        ArrayList<String> deps = getOUNames(dir);
        if (deps != null) {
            deleteOUEntry(deps);
        }

        return true;
    }

    public boolean createDepartment(String path, int index) {
        if (!isConnect()) {
            connect();
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
            if ("".equals(getDepartmentDN(ouName, pathParent.toString()))) {
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
            connect();
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

            if ("".equals(getDepartmentDN(ouName, pathParent.toString()))) {
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
     * @param user UserObject which should contain:usercode password orgcode
     * @return true if success
     */
    public boolean addUser(User user) {
        if (!isConnect()) {
            connect();
        }
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

        String mailBox = "";
        if (user.getMailbox() != null) {
            mailBox = user.getMailbox();
        }
        String uid = user.getUsercode();
        byte[] bytes = new BigInteger(user.getPassword(), 16).toByteArray();
        String pwd = "{MD5}" + new String(java.util.Base64.getEncoder().encode(bytes));
        String cn = user.getUsername();
        String path = new HibernateUserDaoImpl().parseOrgPath(user);
//        init org
        // TODO: 12/2/2020  以往使用user.getOrgname()以赞同科技开头 Org.getFullorgname()不是 需重写 parseOrgPath()等方法
        createDepartment(userDao.parseOrgPath(Org.getFullorgname(user.getOrgcode())), 0);
        try {
            if ("".equals(getUserDN(uid))) {
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
                attrsbu.put("mail", mailBox);
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
                System.out.println(user.getUsername() + "-" + user.getUsercode() + " exists");
                return false;
            }
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean modifyInformation(String dn, String attrName, String attrValue) {
        try {
            ModificationItem[] mods = new ModificationItem[1];
            /* 修改属性 */
            Attribute attr0 = new BasicAttribute(attrName, attrValue);
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr0);
//            mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attr0);
//            /*删除属性*/
            // Attribute attr0 = new BasicAttribute("description",
            // "陈轶");
            // mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
            // attr0);
            /*添加属性*/
            //Attribute attr0 = new BasicAttribute(attrName, attrValue);
            //mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attr0);
            /*修改属性*/
            ctx.modifyAttributes(dn, mods);
            return true;
        } catch (NamingException e) {
            System.err.println(e);
            return false;
        }
    }

    /**
     * @param base
     * @param scope  ：搜索范围:<br>
     *               0本节点: {@link SearchControls#OBJECT_SCOPE}<br>
     *               1单层:{@link SearchControls#ONELEVEL_SCOPE}<br>
     *               2遍历:{@link SearchControls#SUBTREE_SCOPE}
     * @param filter ：指定子节点(格式为"(objectclass=*)",*是指全部，你也可以指定某一特定类型的树节点)
     */
    public void searchInformation(String base, String scope, String filter) {
//        String base = this.config.getBASEDN();
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
                    NamingEnumeration<?> values = attribute.getAll();
                    // Another NamingEnumeration object, this time to iterate through attribute values.
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

    /**
     * sc.setReturningAttributes()不定制属性，将返回所有的属性集
     * @param base
     * @param filter
     * @param attr
     * @return
     *
     */
    public String getAttributeValue(String base, String filter, String attr) {
        SearchControls sc = new SearchControls();
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        sc.setReturningAttributes(new String[]{attr});
        NamingEnumeration<SearchResult> ne;
        try {
            ne = ctx.search(base, filter, sc);
            while (ne.hasMore()) {
                SearchResult sr = ne.next();
                Attributes attributes = sr.getAttributes();
                Attribute attribute = attributes.get(attr);
                if (attribute == null) {
                    return null;
                }
                Object oneVal = attribute.get();
                return oneVal instanceof String ? (String) oneVal : new String((byte[]) oneVal);
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAttributeType(String base, String filter) {
//        ctx.search();
        SearchControls sc = new SearchControls();
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration<SearchResult> ne;
        List<String> list = new ArrayList<>();
        try {
            ne = ctx.search(base, filter, sc);
            while (ne.hasMore()) {
                SearchResult sr = ne.next();
                Attributes attributes = sr.getAttributes();
                NamingEnumeration<?> attrTypes = attributes.getIDs();
                while (attrTypes.hasMore()) {
                    list.add((String) attrTypes.next());
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String searchUser(String usercode, String attributeStr) {
        String[] arr = new String[]{attributeStr};
        Map<String, String> map = searchUser(usercode, arr);
        if (map == null) {
            return null;
        }
        return map.get(attributeStr);
    }

    /**
     * filter = "(objectClass=organizationalPerson)" SearchControls.SUBTREE_SCOPE
     *
     * @param usercode     工号
     * @param attributeStr null时不定制属性，将返回所有的属性集
     * @return ldap organizationalPerson user信息
     */
    public Map<String, String> searchUser(String usercode, String[] attributeStr) {
        if ("".equals(this.getUserDN(usercode))) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        String base = this.getUserDN(usercode).replace(",dc=agree,dc=com", "");
        final String filter = "(objectClass=organizationalPerson)";
        NamingEnumeration<SearchResult> searchResult;
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(attributeStr);
        try {
            searchResult = ctx.search(base, filter, controls);
            while (searchResult.hasMore()) {
                SearchResult sr = searchResult.next();
                Attributes attributes = sr.getAttributes();
                NamingEnumeration<? extends Attribute> ane = attributes.getAll();
                while (ane.hasMore()) {
                    Attribute attribute = ane.next();
                    String attrType = attribute.getID();
                    NamingEnumeration<?> values = attribute.getAll();
                    while (values.hasMore()) {
                        Object oneVal = values.nextElement();
                        if (oneVal instanceof String) {
                            result.put(attrType, (String) oneVal);
                        } else {
                            result.put(attrType, new String((byte[]) oneVal));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateUserPassword(String uid, String pwd) {
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
        return false;
    }

    /**
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
        userDN = (getUserDN(user.getUsercode()).split("," + this.config.getBASEDN())[0]);
        if ("".equals(userDN)) {
            return false;
        }
        try {
            ctx.destroySubcontext(userDN);
            System.out.println("Delete user:" + userDN);
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateUsers(List<User> userlist) {
        int count = 0;
        boolean result;
        for (User user : userlist) {
            result = updateUser(user);
            if (!result) {
                System.out.println("update " + user.getUsername() + " failed!");
            } else {
                count++;
            }
        }
        System.out.println("total update " + count + " users");
    }

    public boolean updateUser(User user) {
        return deleteUser(user) & addUser(user);
    }
}