package utils;

import domain.paasaom.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HibernateUtilTest {

    @Test
    void listByHqlTest() {
        String hql;
        hql = "select distinct a from User a " +
                "where a.orgcode='0001001026006' " +
                "and locate(',76',a.roleids)!=0 and a.states = 1 ";
        List<User> userList = HibernateUtil.listByHql(hql);
//        System.out.println(userList.size());
        for (User user : userList) {
            System.out.println(user.getUsername());
        }
    }


    @Test
    void getSession() {
    }

    @Test
    void getSessionFactory() {
    }

    @Test
    void updateObject() {
    }

    @Test
    void deleteObject() {
    }

    @Test
    void addObject() {
    }

    @Test
    void getObjectById() {
    }

    @Test
    void getObjectByProperty() {
        Map<String, Object> tmap = new HashMap<>();
        tmap.put("username", "刘兴宇-A6853");
        List<User> list = HibernateUtil.listObjectByProperty(tmap, User.class);
        for (User user : list) {
            System.out.println(user);
        }
        assert list.size() > 0;
    }

    @Test
    void getObjectByPropertyTest() {
//        Map<String, Object> tmap = new HashMap<>();
//        tmap.put("username", "刘兴宇-A6853");
        List<User> list = HibernateUtil.listObjectByProperty(null, User.class, -1, 2);
        for (User user : list) {
            System.out.println(user);
        }
        System.out.println(list.size());
        assert list.size() == 2;
    }

    @Test
    void countTableTest() {
        System.out.println(HibernateUtil.countTable(User.class));
        assert HibernateUtil.countTable(User.class) > 0;

        Map<String, Object> filter = new HashMap<>();
        filter.put("sex", 1);
        System.out.println(HibernateUtil.countTable(User.class, filter));
        assert HibernateUtil.countTable(User.class, filter) > 0;
        assert HibernateUtil.countTable(User.class, null) > 0;
    }

    @Test
    void countTableTest2() {
        System.out.println(HibernateUtil.countTable(String.class));
    }
}