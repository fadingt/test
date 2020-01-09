package utils;

import org.junit.jupiter.api.Test;

import javax.mail.Message;
import javax.mail.MessagingException;


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
    void sendEmail() {
    }

    @Test
    void testSendEmail() {
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
}