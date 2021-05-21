package config;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author fadingt
 * 配置文件类 用于方便读取和存储配置属性
 */
public class Config {
    private File file;

    public Config() {
    }

    private void isFileIllegal(File file) throws IllegalArgumentException {
        // TODO: 2/24/2021 检查文件不存在的情况 文件不合法（不是TXT）
        //TODO 2.check .properties suffix/检查prop后缀
        if (file == null) {
            throw new IllegalArgumentException();
        }
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException();
        }
    }

    public Config(File file) {
        isFileIllegal(file);
        this.file = file;
    }

    /**
     * 作为工具类提供给他人时,需要压缩成JAR包（为方便配置文件也在JAR包内）,
     * 在相对于类文件的相对路径寻找配置文件时,配置文件路径有误。
     * 因此需要使用inputStream读取JAR包内的配置文件
     *
     * @param inputStream 输入流;当输入流空时抛出FileNotFoundException
     */
    public Config(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new FileNotFoundException("inputStream is null");
        }
        Properties prop = new Properties();
        prop.load(inputStream);
        this.setFile(new File(""));
        this.setProp(prop);
    }

    private void showConfigProp(Properties prop) {
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

    /**
     * @param prop 要保存到文件的属性
     * @return 结果是否成功, true代表已保存到文件中
     */
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
        isFileIllegal(file);
        this.file = file;
    }
}
