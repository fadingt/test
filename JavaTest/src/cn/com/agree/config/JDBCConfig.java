package cn.com.agree.config;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class JDBCConfig extends Config {
    private String URL;
    private String user;
    private String password;
    private String port;

    public static void main(String[] args) throws IOException {
        File file = new File("D:/jdbc.properties");
        JDBCConfig config = new JDBCConfig(file);
        System.out.println(config);
    }
    @Override
    public String toString() {
        return "JDBCConfig{" +
                "URL='" + URL + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", port='" + port + '\'' +
                '}';
    }

    public JDBCConfig(File file) throws IOException {
        this.setFile(file);
        Properties JDBCProp = this.getProp();
        this.URL = JDBCProp.getProperty("URL");
        this.user = JDBCProp.getProperty("user");
        this.password = JDBCProp.getProperty("password");
        this.port = JDBCProp.getProperty("port");
        if (this.port == null) {
            this.port = "3306";
        }
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }


}
