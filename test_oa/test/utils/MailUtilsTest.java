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

    @Test
    void receiveEmail() throws MessagingException {
        Message[] messages = MailUtils.receiveEmail("liuxingyu@agree.com.cn", "z13833545277", "pop3", "pop3.263.net", "110");
        assert messages.length > 0;
    }

    @Test
    void testReceiveEmail() {
    }

    @Test
    void initSession() {
    }

    @Test
    void sendEmail() throws MessagingException, IOException {
        Message[] rMessages = MailUtils.receiveEmail("liuxingyu@agree.com.cn", "z13833545277", init263POPSession());
        Message message = new MimeMessage(init263SMTPSession());
        message.setFrom(new InternetAddress("liuxingyu@agree.com.cn"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("378730609@qq.com"));
        message.setSubject("MailUtils test");
        String test = MailUtils.showMailMessage(MailUtils.findMessage(rMessages, "体检"));
        message.setText(test);
        MailUtils.sendEmail(message);
    }

    @Test
    void testSendEmail() throws MessagingException, IOException {
        Message[] rMessages = MailUtils.receiveEmail("liuxingyu@agree.com.cn", "z13833545277", init263POPSession());
        Message message = new MimeMessage(init263SMTPSession());
        message.setFrom(new InternetAddress("liuxingyu@agree.com.cn"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("378730609@qq.com"));
        message.setSubject("MailUtils test");
        String test = MailUtils.showMailMessage(MailUtils.findMessage(rMessages, "体检"));
        message.setText(test);
        MailUtils.sendEmail(message,"liuxingyu@agree.com.cn","z13833545277");
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
        MailUtils.saveAttachment(part,destDir);
    }
}