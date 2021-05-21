package dao.impl;

import java.sql.*;
import java.util.*;

import dao.UserDao;
import domain.paasaom.User;
import org.springframework.stereotype.Component;
import utils.HibernateUtil;
import utils.JDBCUtils;
import utils.LDAPUtils;

@Component
public class HibernateUserDaoImpl implements UserDao {

    @Override
    public void updateUser(User user) {
        HibernateUtil.updateObject(user);
    }

    @Override
    public void deleteUser(User user) {
        HibernateUtil.deleteObject(user);
    }

    @Override
    public void saveUser(User user) {
        HibernateUtil.saveObject(user);
    }

    @Override
    public List<User> listUserByProperty(Map<String, Object> userProperty) {
        return HibernateUtil.listObjectByProperty(userProperty, User.class);
    }

    @Override
    public List<User> listUserByProperty(Map<String, Object> userProperty, int from, int len) {
        return HibernateUtil.listObjectByProperty(userProperty, User.class, from, len);
    }

    //    @Override
    public String parseOrgPath(User user) {
        StringBuilder ret = new StringBuilder();
        String path = user.getOrgcode();
        String[] paths = path.substring(1).split("\\|");
        for (String s : paths) {
            ret.insert(0, "ou=" + s + ",");
        }
        return ret.toString();
    }

    @Override
    public String parseOrgPath(String orgname) {
        String[] paths = orgname.split("-");
        StringBuilder result = new StringBuilder();
        for (String path : paths) {
            result.insert(0, "ou=" + path + ",");
        }
        if (!orgname.contains("赞同科技")) {
            result.append("ou=赞同科技,");
        }
        if (!orgname.contains("赞同")) {
            result.append("ou=赞同,");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    @Override
    public Map<String, String> getOrgMap(String sql) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData meta = resultSet.getMetaData();
            int columnCnt = meta.getColumnCount();
            Map<String, String> orgMap = new HashMap<>();
            String key = null;
            String val = null;
            while (resultSet.next()) {
                for (int i = 1; i <= columnCnt; i++) {
                    switch (meta.getColumnName(i).toUpperCase()) {
                        case "S_ORGCODE":
                            key = (String) resultSet.getObject(i);
                            break;
                        case "S_NAME":
                            val = (String) resultSet.getObject(i);
                            break;
                    }
                    orgMap.put(key, val);
                }
            }
            return orgMap;
        } finally {
            JDBCUtils.free(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public User getUserById(int userid) {
        return HibernateUtil.getObjectById(User.class, userid);
    }

    @Override
    public User getUserByUsercode(String usercode) {
        // TODO: 11/30/2020  校验usercode合法
        if (usercode == null) {
            return null;
        }
        Map<String, Object> prop = new HashMap<>();
        prop.put("usercode", usercode);
        return HibernateUtil.listObjectByProperty(prop, User.class,0,1).get(0);
    }

    @Override
    public int countUser() {
        return HibernateUtil.countTable(User.class);
    }

    @Override
    public int countUser(Map<String, Object> filter) {
        return HibernateUtil.countTable(User.class, filter);
    }

    /**
     * 通过LDAP查找员工的密码
     *
     * @param usercode 员工工号
     * @return 2次MD5加密后的密码
     */
    @Override
    public String get2MD5PasswordByLDAP(String usercode) {
        String password;
        LDAPUtils ldap = new LDAPUtils();
        ldap.connect();
        password = ldap.getAttributeValue("", "uid=" + usercode, "userPassword");
        ldap.close();
        return password;
    }

    @Override
    public boolean isRole(int userid, int roleid) {
        // TODO: 11/30/2020 校验roleid合法
        String userroles = getUserById(userid).getRoleids();
        return isRole(""+roleid, userroles);
    }

    private boolean isRole(String roleid, String userroleids){
        String[] roleids = userroleids.split(",");
        for (String id : roleids) {
            if (roleid.equals(id)) {
                return true;
            }
        }
        return false;
    }
}
