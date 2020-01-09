package cn.com.agree.utils;

import cn.com.agree.openldap.JDBCConfig;
import cn.com.agree.openldap.UserDO;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUtils {
    public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
        String sql = makeSQL(new File("D:/user.sql"));
        List<UserDO> userList = getSearchList(sql);
    }

    public static ArrayList<UserDO> getSearchList(String sql) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        File file = new File("D:/jdbc.properties");
        JDBCConfig config = null;
        try {
            config = new JDBCConfig(file);
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(config.getURL(), config.getUser(), config.getPassword());
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ResultSetMetaData meta = resultSet.getMetaData();
//                int
            }
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

        return null;
    }

    public static String makeSQL(File file) throws IOException {
        StringBuffer sql = new StringBuffer();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String len = null;
        while (null != (len = br.readLine())) {
            sql.append(len);
        }
        return sql.toString();
    }
}
