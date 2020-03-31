package cn.com.agree.dao;

import cn.com.agree.config.JDBCConfig;
import cn.com.agree.domain.User;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    public static void main(String[] args) {
        User user = new User();
        UserDao userDao = new UserDaoImpl();
        user.setOrgcode("|0|20000|30833|30835|30843");
        System.out.println(userDao.parseOrgpath(user));
    }

    @Override
//    TODO
    public List<User> getUserList(String sql) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        File file = new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\jdbc.properties");
        JDBCConfig config = null;
        try {
            config = new JDBCConfig(file);
//            "com.mysql.jdbc.Driver";"com.mysql.cj.jdbc.Driver"
            Class.forName(config.getDriverName());
            connection = DriverManager.getConnection(config.getURL(), config.getUser(), config.getPassword());
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData meta = resultSet.getMetaData();
            int columnCnt = meta.getColumnCount();
            List<User> userList = new ArrayList<User>();
            while (resultSet.next()) {
                User user = new User();
                for (int i = 1; i <= columnCnt; i++) {
                    switch (meta.getColumnName(i)) {
                        case "userid":
                            user.setUserid(((Long)resultSet.getObject(i)).intValue());
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            //关闭数据库连接的顺序是：ResultSet,PreparedStatement,Connection.
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public String parseOrgpath(User user) {
        String ret = "";
        String path = user.getOrgcode();
        String[] paths = path.substring(1).split("\\|");
        for (int i = 0; i < paths.length; i++) {
            ret = "ou=" + paths[i] + "," + ret;
        }
        return ret;
    }
}
