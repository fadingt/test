package cn.com.agree.utils;

import cn.com.agree.config.JDBCConfig;

import java.io.*;
import java.sql.*;
import java.util.Objects;

/**
 *JDBC������<br>
 * singleton ע��com.mysql.jdbc.Driver<br>
 * �ṩJDBC���Ӻ�ע������
 */
public class JDBCUtils {
    private JDBCConfig config;

    private JDBCUtils() {
        File file = new File(Objects.requireNonNull(JDBCUtils.class.getClassLoader().getResource("JDBC.properties")).getPath());
        try {
            this.config = new JDBCConfig(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(config.getURL(), config.getUser(), config.getPassword());
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
