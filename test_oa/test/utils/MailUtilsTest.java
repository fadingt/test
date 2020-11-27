package utils;

import org.junit.jupiter.api.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.IOException;

import static utils.MailUtils.*;


class MailUtilsTest {
    private MailUtils mailUtil;

    public MailUtilsTest() {
        this.mailUtil = new MailUtils();
        try {
            this.mailUtil.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    void sendEmailTest() throws MessagingException, IOException {
//        Message[] rMessages = this.mailUtil.receiveEmail();
//        Message message = new MimeMessage(init263SMTPSession());
//        message.setFrom(new InternetAddress("liuxingyu@agree.com.cn"));
//        message.setRecipient(Message.RecipientType.TO, new InternetAddress("378730609@qq.com"));
//        message.setSubject("MailUtils test");
//        String test = MailUtils.showMailMessage(MailUtils.findMessage(rMessages, "体检"));
//        message.setText(test);
//        this.mailUtil.sendEmail(message);
        this.mailUtil.sendEmail("测试","文本内容","378730609@qq.com");
    }
//
//    @Test
//    void SendEmailTest() throws MessagingException, IOException {
//        Message[] rMessages = this.mailUtil.receiveEmail();
//        Message message = new MimeMessage(init263SMTPSession());
//        message.setFrom(new InternetAddress("liuxingyu@agree.com.cn"));
//        message.setRecipient(Message.RecipientType.TO, new InternetAddress("378730609@qq.com"));
//        message.setSubject("MailUtils test");
////        String test = MailUtils.showMailMessage(MailUtils.findMessage(rMessages, "体检"));
//        String test = MailUtils.showMailMessage(MailUtils.findMessage(rMessages, "赞同科技2020-2021年度体检通知"));
//        message.setText(test);
////        System.out.println(test);
//        this.mailUtil.sendEmail(message, "liuxingyu@agree.com.cn", "z13833545277");
//    }

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