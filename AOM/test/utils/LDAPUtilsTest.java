package utils;

import dao.UserDao;
import dao.impl.HibernateUserDaoImpl;
import domain.paasaom.User;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

class LDAPUtilsTest {
    private LDAPUtils ldap;
    private User user;
    private String password;

    public LDAPUtilsTest() {
        this.ldap = new LDAPUtils();
        this.user = new User();
        this.password = "agree123";
        this.user.setUsercode("A6853");
        this.user.setOrgcode("");
        this.user.setPassword(EncryptUtils.getMD5(password));
    }

    @Test
    void authenricate() {
        ldap.connect();
        assert ldap.authenricate("A6853", "agree123");
        ldap.close();
    }

    @Test
    void deleteAllUsers() {
        this.ldap.connect();
//        this.ldap.deleteAllUsers("ou=赞同");
        this.ldap.close();
        assert !ldap.authenricate(this.user.getUsercode(), this.password);
    }

    @Test
//        删除禁用人员(离职一个月)
    void deleteUser() {
        UserDao userDao = new HibernateUserDaoImpl();
        HashMap<String, Object> userProp = new HashMap<>();
        userProp.put("status", 0);
        List<User> users = userDao.listUserByProperty(userProp);
//        ldap.connect();
        for (User user : users) {
//            ldap.deleteUser(user);
            System.out.println(user);
        }
//        ldap.close();
    }

    @Test
    void deleteAllDeps() {
    }

    @Test
    void createDepartment() {
    }

    @Test
    void syncDept2Ldap() {
    }

    @Test
    void updateUser() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("usercode", "A11759");
//        User user = new HibernateUserDaoImpl().getUserByProperty(map).get(0);
//        System.out.println(ldap.getUserDN("A11769"));
        User user = new HibernateUserDaoImpl().getUserById(613665);
        // TODO: 12/2/2020
//        user.getOrg().setOrgname("信息技术部-运维管理部");
        System.out.println(user);

//        ldap.connect();
//        ldap.updateUser(user);
//        ldap.close();
    }

    @Test
    void updateUsers() {
        ldap.connect();
        UserDao userDao = new HibernateUserDaoImpl();
        List<User> userlist;
//        File file = new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\user.sql");
//        String sql = FileUtils.readFile(file);
//        sql = sql + " where userid > 617794";// maxid 617831
        userlist = userDao.listUserByProperty(null);
        ldap.updateUsers(userlist);
        ldap.close();
    }

    @Test
    void addUserTest() {
        this.ldap.connect();
        User user = new HibernateUserDaoImpl().getUserById(2);
        this.ldap.addUser(user);
        this.ldap.close();
    }

    @Test
    void modifyInformation(){
//        Map<String,Object> proprety =new HashMap<>();
//        proprety.put("usercode","A2073");
        ldap.connect();
        String userDN;
//        userDN = ldap.getUserDN("A2073");
        userDN="ou=信息技术部管理办公室,ou=信息技术部,ou=赞同科技,ou=赞同";
//        User user = new HibernateUserDaoImpl().getUserByProperty(proprety).get(0);
//        System.out.println(user.getMailbox());
//        String mailBox = "wang.zhe@agree.com.cn";
        boolean result = ldap.modifyInformation(userDN,"cn","王喆-A2073");
        System.out.println("修改结果:"+result);
    }

    @Test
    void sync2Ldap() {
    }

    @Test
    void searchInformation() {
        this.ldap.connect();
//        this.ldap.searchInformation("", "","uid=A6853");
//        this.ldap.searchInformation("","","uid=A6853");
        this.ldap.searchInformation("","","uid=superadmin");
//        this.ldap.searchInformation("uid=A10623,ou=北京开发一部,ou=北京应用开发部,ou=应用开发事业部,ou=赞同科技,ou=赞同","","objectClass=organizationalPerson");
//        this.ldap.searchInformation("","one","uid=A6853");
//        this.ldap.searchInformation("","base","description=A6853");
//        this.ldap.searchInformation("", "", "(objectClass=organizationalPerson)");
        this.ldap.close();
    }

    @Test
    void searchUser() {
        this.ldap.connect();
        System.out.println(this.ldap.searchUser("A11305", "userPassword"));
//        String[] arr = {"uid", "sn", "userPassword"};
//        Map<String,String> map = this.ldap.searchUser("A6853", arr);
//        assert map!=null;
//        System.out.println(map.get("uid")+map.get("sn")+map.get("userPassword"));
        this.ldap.close();
    }
    @Test
    void getAttributeValue(){
        String usercode = "a0554";
//        String usercode = "A4508";//田田
        ldap.connect();
        String ldappwd;
        String md5pwd;
        ldappwd = ldap.getAttributeValue("","uid="+usercode,"userPassword");
//        String result = ldap.getAttributeValue("","objectClass=organizationalPerson","userPassword");
        ldap.close();
        System.out.println(ldappwd=ldappwd.replace("{MD5}",""));
        md5pwd=new BigInteger(1,java.util.Base64.getDecoder().decode(ldappwd.getBytes())).toString(16);
        System.out.println(md5pwd);
        System.out.println(EncryptUtils.getMD5(md5pwd));
    }
    @Test
    void getAttributeType(){
        // TODO: 8/31/2020 LIST中属性重复
        ldap.connect();
        List<String> list = ldap.getAttributeType("","uid=A6853");
//        List<String> list = ldap.getAttributeType("","objectClass=organizationalPerson");
        for (String str:list) {
            System.out.println(str);
        }
        ldap.close();
    }
}