package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LDAPConfig extends Config {
    private String URL;
    private String BASEDN;
    private String root;
    private String rootpwd;

    public LDAPConfig(File file) throws IOException {
        this.setFile(file);
        Properties ldapProp = this.getProp();
        this.URL = ldapProp.getProperty("URL");
        this.BASEDN = ldapProp.getProperty("BASEDN");
        this.root = ldapProp.getProperty("root");
        this.rootpwd = ldapProp.getProperty("rootpwd");
    }

    public LDAPConfig(InputStream inputStream) throws IOException {
        if(inputStream==null){
            throw new FileNotFoundException("inputStream is null");
        }
        Properties ldapProp = new Properties();
        ldapProp.load(inputStream);
        this.URL = ldapProp.getProperty("URL");
        this.BASEDN = ldapProp.getProperty("BASEDN");
        this.root = ldapProp.getProperty("root");
        this.rootpwd = ldapProp.getProperty("rootpwd");
    }
    public String getURL() {
        return URL;
    }

    public String getBASEDN() {
        return BASEDN;
    }

    public String getRoot() {
        return root;
    }

    public String getRootpwd() {
        return rootpwd;
    }


    @Override
    public String toString() {
        return "LDAPConfig{" +
                "URL='" + URL + '\'' +
                ", BASEDN='" + BASEDN + '\'' +
                ", root='" + root + '\'' +
                ", rootpwd='" + rootpwd + '\'' +
                '}';
    }
}
