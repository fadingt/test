package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtils {
    private static Session SMTPSession = initSMTPSession();
    private static Session POPSession = initPOPSession();

    public static void main(String[] args) throws MessagingException {
        Message[] rMessages = receiveEmail("liuxingyu@agree.com.cn", "z13833545277");
        StringBuilder stringBuilder = new StringBuilder();
        for (Message message:rMessages) {
            if(message.getSubject().contains("重置")){
                stringBuilder.append(InternetAddress.toString(message.getFrom())).append("\\t").append(message.getSubject()).append(System.lineSeparator());
            }
        }
        Message message = new MimeMessage(MailUtils.SMTPSession);
        message.setFrom(new InternetAddress("liuxingyu@agree.com.cn"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("378730609@qq.com"));
//        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("9zliuxingyu@163.com"), new InternetAddress("378730609@qq.com")});
        message.setSubject("MailUtils test");
        message.setText(stringBuilder.toString());
        sendEmail(message);
    }

    public static Message[] receiveEmail(String user, String password) throws MessagingException {
        Store store;
        Folder folder;
        store = POPSession.getStore("pop3");
        store.connect(user, password);
        folder = store.getFolder("Inbox");
        if (!folder.exists()) {
            throw new RuntimeException("Inbox NOT FOUND");
        }
        folder.open(Folder.READ_ONLY);
        return folder.getMessages();
    }

    private static Session initSMTPSession() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");//连接协议
        properties.put("mail.smtp.host", "smtp.263.net");//主机
        properties.put("mail.smtp.port", "465");//端口号
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "false");//SSL加密
        return Session.getInstance(properties);
    }

    private static Session initPOPSession() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "pop3");
        properties.put("mail.pop3.host", "pop.263.net");
        properties.put("mail.pop3.port", "110");
        properties.put("mail.pop3.auth", "true");
        properties.put("mail.pop3.starttls.enable", "true");
        return Session.getDefaultInstance(properties);
    }

    public static void sendEmail(Message message) throws MessagingException {
        Transport transport = SMTPSession.getTransport();
        transport.connect("liuxingyu@agree.com.cn", "z13833545277");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public static void sendEmail(Message message, String user, String password) throws MessagingException {
        Transport transport = SMTPSession.getTransport();
        transport.connect(user, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}