package cn.com.agree.utils;

import cn.com.agree.config.JDBCConfig;
import cn.com.agree.domain.UserDO;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtils {
    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
        String sql;
        System.out.println(sql = makeSQL(new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\user.sql")));
        List<UserDO> userlist = getUserList(sql);
        for (UserDO user : userlist) {
            System.out.println(user.toString());
        }
    }

    public static Map<String, String> getOrgMap(String sql) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        File file;
        JDBCConfig config;
        try {
            file = new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\jdbc.properties");
            config = new JDBCConfig(file);
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(config.getURL(), config.getUser(), config.getPassword());
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData meta = resultSet.getMetaData();
            int columnCnt = meta.getColumnCount();
            Map<String, String> orgMap = new HashMap<String, String>();
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

    public static List<UserDO> getUserList(String sql) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        File file = new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource\\jdbc.properties");
        JDBCConfig config = null;
        try {
            config = new JDBCConfig(file);
            Class.forName("com.mysql.jdbc.Driver");
//            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(config.getURL(), config.getUser(), config.getPassword());
//            connection = DriverManager.getConnection("jdbc:mysql://192.9.200.123:3306/paas_aom?user=query&password=xitongkaifa_2019&useUnicode=true&characterEncoding=utf-8");
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData meta = resultSet.getMetaData();
            int columnCnt = meta.getColumnCount();
            List<UserDO> userList = new ArrayList<UserDO>();
            while (resultSet.next()) {
                UserDO user = new UserDO();
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

    public static String makeSQL(File file) throws IOException {
        StringBuffer sql = new StringBuffer();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String len;
        while (null != (len = br.readLine())) {
            sql.append(len);
        }
        return sql.toString();
    }
}
