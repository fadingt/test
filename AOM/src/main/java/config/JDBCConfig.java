package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JDBCConfig extends Config {
    private String URL;
    private String user;
    private String password;
    private String port;
    private String driverName;

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
        this.driverName = JDBCProp.getProperty("driverName");
        if (this.port == null) {
            this.port = "3306";
        }
        if (this.getURL() == null || this.user == null || this.password == null) {
            throw new RuntimeException("JDBC配置信息不全");
        }
    }
    public JDBCConfig(InputStream inputStream) throws IOException {
        if(inputStream==null){
            throw new FileNotFoundException("inputStream is null");
        }
        Properties JDBCProp=new Properties();
        JDBCProp.load(inputStream);
        this.URL = JDBCProp.getProperty("URL");
        this.user = JDBCProp.getProperty("user");
        this.password = JDBCProp.getProperty("password");
        this.port = JDBCProp.getProperty("port");
        this.driverName = JDBCProp.getProperty("driverName");
        if (this.port == null) {
            this.port = "3306";
        }
        if (this.getURL() == null || this.user == null || this.password == null) {
            throw new RuntimeException("JDBC配置信息不全");
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

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }


}
