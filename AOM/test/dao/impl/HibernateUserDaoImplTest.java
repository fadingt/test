package dao.impl;

import dao.UserDao;
import domain.paasaom.User;
import domain.paasaom.UserRole;
import org.junit.jupiter.api.Test;
import utils.FileUtils;
import utils.HibernateUtil;
import utils.LDAPUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HibernateUserDaoImplTest {

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUserByProperty() {
    }

    @Test
    void getUserRoleTest() {
        User user = HibernateUtil.getObjectById(User.class, 608954);
        List list = UserDao.getUserRole(user.getRoleids());
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    @Test
    void isRoleTest() {
        UserDao userDao = new HibernateUserDaoImpl();
        Map<String, Object> prop = new HashMap<>();
//        prop.put("rolename", "职能管理-销售");
//        prop.put("rolename", "职能管理-客户经理");
        prop.put("rolename", "职能管理-运营");
        int roleid = HibernateUtil.listObjectByProperty(prop, UserRole.class).get(0).getId();
        boolean result;
//        System.out.println(roleid);
//        result = userDao.isRole(608195, roleid);
//        assert !result;
//        result = userDao.isRole(31512, roleid);
//        assert result;
        result = userDao.isRole(31230,roleid);
        assert !result;
    }

    @Test
    void getUserByIdTest() {
        User user;
        user = HibernateUtil.getObjectById(User.class, 608954);//刘兴宇
        System.out.println(user);
    }

    @Test
    void countUser() {
        System.out.println(new HibernateUserDaoImpl().countUser());
    }

    @Test
    void parseOrgPathTest() {
        HibernateUserDaoImpl userDao = new HibernateUserDaoImpl();
        System.out.println(userDao.parseOrgPath("赞同-赞同科技-人力资源部"));
        System.out.println(userDao.parseOrgPath("信息技术部-运维管理部"));
    }

    @Test
    void getOrgMap() throws SQLException, IOException {
        InputStream sqlStream = LDAPUtils.class.getClassLoader().getResourceAsStream("org.sql");
        String sql = FileUtils.readFile(sqlStream);
        Map<String, String> map = new HibernateUserDaoImpl().getOrgMap(sql);
        map.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
