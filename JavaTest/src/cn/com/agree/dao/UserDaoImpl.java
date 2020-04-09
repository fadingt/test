package cn.com.agree.dao;

import cn.com.agree.config.JDBCConfig;
import cn.com.agree.domain.User;
import cn.com.agree.utils.JDBCUtils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    @Override
    public Map<String, String> getOrgMap(String sql) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        File file;
        JDBCConfig config;
        try {
            file = new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\jdbc.properties");
            config = new JDBCConfig(file);
            connection = JDBCUtils.getConnection(config);
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
    public List<User> getUserListBYUsercode(List<String> usercodeList) throws IOException, SQLException {
        String sql;
        StringBuilder condition = new StringBuilder();
        condition.append(" WHERE USERCODE IN (");
        for (String usercode : usercodeList) {
            condition.append("'").append(usercode).append("',");
        }
        condition.append("'')");
        sql = JDBCUtils.makeSQL(new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\user.sql")) + condition.toString();
        System.out.println(sql);
        return getUserList(sql);
    }

    @Override
//    TODO
    public List<User> getUserList(String sql) throws SQLException, IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        File file = new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\jdbc.properties");
        JDBCConfig config;
        try {
            config = new JDBCConfig(file);
            connection = JDBCUtils.getConnection(config);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData meta = resultSet.getMetaData();
            int columnCnt = meta.getColumnCount();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                for (int i = 1; i <= columnCnt; i++) {
                    switch (meta.getColumnName(i)) {
                        case "userid":
                            user.setUserid(((Long) resultSet.getObject(i)).intValue());
                            break;
                        case "username":
                            user.setUsername((String) resultSet.getObject(i));
                            break;
                        case "usercode":
                            user.setUsercode((String) resultSet.getObject(i));
                            break;
                        case "password":
                            user.setPassword((String) resultSet.getObject(i));
                            break;
                        case "orgcode":
                            user.setOrgcode((String) resultSet.getObject(i));
                            break;
                        case "orgname":
                            user.setOrgname((String) resultSet.getObject(i));
                            break;
                    }
                }
                userList.add(user);
            }
            return userList;
        } finally {
            JDBCUtils.free(resultSet, preparedStatement, connection);
        }
    }


    @Override
    public String parseOrgPath(User user) {
        StringBuilder ret = new StringBuilder();
        String path = user.getOrgcode();
        String[] paths = path.substring(1).split("\\|");
        for (String s : paths) {
            ret.insert(0, "ou=" + s + ",");
        }
        return ret.toString();
    }
}
