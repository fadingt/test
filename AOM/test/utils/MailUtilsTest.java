package utils;

import org.junit.jupiter.api.Test;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

class MailUtilsTest {
    private MailUtils mailUtil;

    public MailUtilsTest() {
        this.mailUtil = new MailUtils();
        this.mailUtil.init();
    }

    @Test
    void ReceiveEmailTest() throws IOException, MessagingException {
        Message[] messages = this.mailUtil.receiveEmail();
        System.out.println(messages.length);
        assert messages.length > 0;
    }

    @Test
    void getSessionTest() {

    }

    @Test
    void initSession() {
    }

    @Test
    void sendEmailTest() {
        this.mailUtil.sendEmail("测试", "文本内容", "liuxingyu@agree.com.cn");
    }

    @Test
    void SendEmailTest2() throws IOException, MessagingException {
//        Message[] rMessages = this.mailUtil.receiveEmail();
        Message message = new MimeMessage(this.mailUtil.getSMTPSession());
        message.setFrom(new InternetAddress("liuxingyu@agree.com.cn"));
        InternetAddress ia1 = new InternetAddress("378730609@qq.com");
        InternetAddress ia2 = new InternetAddress("liuxingyu@agree.com.cn");
//        InternetAddress[] addresses = {ia1,ia2};
        InternetAddress[] addresses = new InternetAddress[2];
        addresses[0] = ia1;
        addresses[1] = ia2;
        message.setRecipients(Message.RecipientType.TO, addresses);
//        message.setRecipient(Message.RecipientType.TO, new InternetAddress("378730609@qq.com"));
//        message.setRecipient(Message.RecipientType.TO, new InternetAddress("9zliuxingyu@163.com"));
//        message.setRecipient(Message.RecipientType.CC, new InternetAddress("support@agree.com.cn"));
        message.setSubject("邮件列表抄送测试");
//        String test = MailUtils.showMailMessage(MailUtils.findMessage(rMessages, "赞同科技2020-2021年度体检通知"));
//        message.setText(test);
        message.setText("邮件列表抄送测试");
        this.mailUtil.sendEmail(message);
    }

    @Test
    void sendEmailTest3() throws AddressException {
        Address[] toaddress = {new InternetAddress("liuxingyu@agree.com.cn"), new InternetAddress("abcwe@agree.com.cn"), new InternetAddress("diserf@agree.com.cn")};
        InternetAddress[] ccaddress = {new InternetAddress("378730609@qq.com")};
        this.mailUtil.sendEmail("无效邮箱地址测试", "无效邮箱地址测试", toaddress, ccaddress);
    }

    @Test
    void isContainAttachment() {
    }

    @Test
    void getMailText() {
    }

    @Test
    void findMessage() {
    }

    @Test
    void showMailText() {
    }

    @Test
    void saveAttachment() throws IOException, MessagingException {
        String destDir = "D:/";
        Part part = null;
        MailUtils.saveAttachment(part, destDir);
    }
}