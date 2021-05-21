package utils;


import config.JDBCConfig;

import java.io.*;
import java.sql.*;
import java.util.Objects;

/**
 * JDBC utils<br>
 * register {@link com.mysql.cj.jdbc.Driver}
 */
public class JDBCUtils {
    private static JDBCConfig config;

    static {
        File file = new File(Objects.requireNonNull(JDBCUtils.class.getClassLoader().getResource("JDBC.properties")).getPath());
        try {
            config = new JDBCConfig(file);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * use {@link config.JDBCConfig} in the directory of classpath. The file name must be 'JDBC.properties'
     *
     * @return {@link java.sql.Connection}
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
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
            if (rs != null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null){
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null){
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void destroy() {
        try {
            DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
