package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domain.paasaom.User;
import domain.paasaom.UserRole;
import utils.HibernateUtil;

public interface UserDao {
//    String parseOrgPath(User user);

    String parseOrgPath(String orgname);

    Map<String, String> getOrgMap(String sql) throws SQLException, IOException, ClassNotFoundException;

//    List<User> getUserList(String sql) throws SQLException, IOException;

    void updateUser(User user);

    void deleteUser(User user);

    void saveUser(User user);

    List<User> listUserByProperty(Map<String, Object> userProperty);

    List<User> listUserByProperty(Map<String, Object> userProperty, int from, int len);

    User getUserById(int userid);

    User getUserByUsercode(String usercode);

    // TODO: 11/30/2020 no static
    static String getUsername(int userid) {
        return HibernateUtil.getObjectById(User.class, userid).getUsername();
    }

    static List<String> getUserRole(String roleids){
        // TODO: 11/26/2020 判断roleids 合法 翻译rolename无名字设置成空 不报错
        String[] roles = roleids.split(",");
        List<String> list = new ArrayList<>();
        for (String role: roles) {
            list.add(HibernateUtil.getObjectById(UserRole.class,Integer.valueOf(role)).getRolename());
        }
        return list;
    }

    int countUser();

    int countUser(Map<String,Object> filter);

    String get2MD5PasswordByLDAP(String usercode);

    boolean isRole(int userid, int roleid);

}