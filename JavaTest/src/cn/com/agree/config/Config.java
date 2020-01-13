package cn.com.agree.config;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

public class Config {
    private File file;

    public Config() {
    }

    public Config(File file) {
        this.file = file;
    }

    private void showConfigProp(Properties prop) {
        //TODO show properties
        Enumeration<?> propNames = prop.propertyNames();
        while (propNames.hasMoreElements()) {
            System.out.println(propNames.nextElement());
        }
    }

    public Properties getProp() throws IOException {
        if (this.file == null) {
            throw new FileNotFoundException("config.file do not exists");
        }
        FileInputStream fis = new FileInputStream(this.file);
        Properties prop = new Properties();
        prop.load(fis);
        return prop;
    }

    public boolean setProp(Properties prop) throws IOException {
        if (this.file == null) {
            throw new FileNotFoundException("config.file do not exists");
        }
        FileOutputStream fos = new FileOutputStream(this.file);
        prop.store(fos, "");
        //TODO check if file is successfully writtend/检查文件是否保存
        return true;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        //TODO 1.check if file is legal/检查文件合法
        //TODO 2.check .properties suffix/检查prop后缀
        this.file = file;
    }
}
