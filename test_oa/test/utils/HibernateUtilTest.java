package utils;

import domain.Server;
import domain.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUtilTest {

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
        // tmap.put("username", "王宇晖-A0002");
        // tmap.put("usercode", "A0002");
        tmap.put("state", new BigDecimal(1));
        List<User> list = HibernateUtil.getObjectByProperty(tmap, User.class);
        for (User user : list) {
            System.out.println(user.toString());
        }
//        Map map = new HashMap();
//        map.put("ipadress","192.168.100.102");
//        List<Server> servers = HibernateUtil.getObjectByProperty(map,Server.class);
//        System.out.println(servers.get(0));
    }

    @Test
    void getTableCount() {
    }
}