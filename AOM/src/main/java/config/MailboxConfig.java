package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 11/27/2020 3:12 PM
 */
public class MailboxConfig extends Config {
    private String mailbox;
    private String password;

    // TODO: 11/27/2020 检查必须含有邮箱名 密码等属性。校验合法
    // TODO: 11/27/2020 通过inputStream构造的Config 没有this.file
    // TODO: 11/27/2020 将inputstream的构造方法 上移动到Config类
    public MailboxConfig(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new FileNotFoundException("inputStream is null");
        }
//        this.setFile();
        Properties mailprop = new Properties();
        mailprop.load(inputStream);
        this.setProp(mailprop);
        this.mailbox = mailprop.getProperty("mailbox");
        this.password = mailprop.getProperty("password");
    }
    public MailboxConfig(File file) throws IOException {
        this.setFile(file);
        Properties mailprop = this.getProp();
        this.mailbox = mailprop.getProperty("mailbox");
        this.password = mailprop.getProperty("password");
    }

    public String getMailbox() {
        return mailbox;
    }

    public String getPassword() {
        return password;
    }
}
