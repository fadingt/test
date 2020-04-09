package cn.com.agree.utils;

import cn.com.agree.config.JDBCConfig;

import java.io.*;
import java.sql.*;

/**
 *JDBC工具类<br>
 * singleton 注册com.mysql.jdbc.Driver<br>
 * 提供JDBC连接和注销方法
 */
public class JDBCUtils {

    private JDBCUtils() {
    }

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static Connection getConnection(JDBCConfig config) throws SQLException {
        return DriverManager.getConnection(config.getURL(), config.getUser(), config.getPassword());
    }

    public static void free(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null)
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
    public static void destroy() {
        try{
            DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String makeSQL(File file) throws IOException {
        StringBuilder sql = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String len;
        while (null != (len = br.readLine())) {
            sql.append(len);
        }
        return sql.toString();
    }
}
