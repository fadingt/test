package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;



public final class MailUtils {
    public static void main(String[] args) throws MessagingException, IOException {
/*        Message[] rMessages = receiveEmail("liuxingyu@agree.com.cn", "z13833545277", init263POPSession());
        Message message = new MimeMessage(init263SMTPSession());
        message.setFrom(new InternetAddress("liuxingyu@agree.com.cn"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("378730609@qq.com"));
        message.setSubject("MailUtils test");
        String test = showMailMessage(findMessage(rMessages, "体检"));
        message.setText(test);
        sendEmail(message);*/
        /*
         * 1.接收support邮箱邮件。记录文本内容和附件
         * 2.将文本内容分类写入Excel中
         * */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String now = dateFormat.format(new Date());
        System.out.println("nowday is:\t" + now);
//        Message[] messages = receiveEmail("support@agree.com.cn", "ag1234", init263POPSession());
        Message[] messages = receiveEmail("liuxingyu@agree.com.cn", "z13833545277", init263POPSession());
        if (messages.length > 0) {
            for (int i = 0; i < messages.length; i++) {
                if (dateFormat.format(messages[i].getSentDate()).equals(now)) {
                    String messageText = showMailMessage(messages[i]);
                    String filename = "NO" + messages[i].getMessageNumber() + ".txt";
                    FileUtils.saveFile(messageText, "D:/JAVAIO_TEMP/supMail/" + filename);
                }
            }
        }
    }

    public static Message[] receiveEmail(String user, String password, Session session) throws MessagingException {
        Store store = session.getStore(session.getProperty("mail.transport.protocol"));
        store.connect(user, password);
        Folder folder = store.getFolder("Inbox");
        if (!folder.exists()) {
            throw new RuntimeException("Inbox NOT FOUND");
        }
        folder.open(Folder.READ_ONLY);
        return folder.getMessages();
    }

    public static Message[] receiveEmail(String user, String password, String protocol, String host, String port) throws MessagingException {
        return receiveEmail(user, password, initSession(protocol, host, port));
    }

    public static Session initSession(String protocol, String host, String port) {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", protocol);
        properties.put("mail." + protocol + ".host", host);
        properties.put("mail." + protocol + ".port", port);
        properties.put("mail." + protocol + ".auth", "true");
        properties.put("mail." + protocol + ".ssl.enalbe", "false");
        return Session.getInstance(properties);
    }

    public static Session init263SMTPSession() {
        return initSession("smtp", "smtp.263.net", "465");
    }

    public static Session init263POPSession() {
        return initSession("pop3", "pop3.263.net", "110");
    }

    public static void sendEmail(Message message) throws MessagingException {
        Transport transport = init263SMTPSession().getTransport();
        transport.connect("liuxingyu@agree.com.cn", "z13833545277");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public static void sendEmail(Message message, String user, String password) throws MessagingException {
        Transport transport = init263SMTPSession().getTransport();
        transport.connect(user, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public static boolean isContainAttachment(Part part) throws IOException, MessagingException {
        boolean flag = false;
        if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDescription();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                    flag = true;
                } else if (bodyPart.isMimeType("multipart/*")) {
                    flag = isContainAttachment(bodyPart);
                } else {
                    String containType = bodyPart.getContentType();
                    if (containType.contains("application") || containType.contains("name")) {
                        flag = true;
                    }
                }
                if (flag) break;
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = isContainAttachment((Part) part.getContent());
        }
        return flag;
    }

    public static void saveAttachment(Part part, String destDir) throws IOException, MessagingException {
        if (!isContainAttachment(part)) {
            throw new RuntimeException("NO ATTACHMENT");
        }
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCnt = multipart.getCount();
            for (int i = 0; i < partCnt; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String description = bodyPart.getDescription();
                String filename = null;
                if (bodyPart.getFileName() != null) {
                    filename = MimeUtility.decodeText(bodyPart.getFileName());
                }
                if (description != null && (description.equalsIgnoreCase(Part.ATTACHMENT) || description.equalsIgnoreCase(Part.INLINE))) {
                    FileUtils.saveFile(bodyPart.getInputStream(), destDir + filename);
                } else if (bodyPart.isMimeType("multipart/*")) {
                    saveAttachment(bodyPart, destDir + filename);
                } else {
                    String contentType = part.getContentType();
                    if (contentType.contains("name") || contentType.contains("application")) {
                        FileUtils.saveFile(bodyPart.getInputStream(), destDir + filename);
                    } else if (filename != null) {
                        FileUtils.saveFile(bodyPart.getInputStream(), destDir + filename);
                    }
                }

            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachment((Part) part.getContent(), destDir);
        }
    }

    public static String getFilename(Part part) throws IOException, MessagingException {
        StringBuilder filename = new StringBuilder();
        if (!isContainAttachment(part)) {
            return null;
        }
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCnt = multipart.getCount();
            for (int i = 0; i < partCnt; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.getFileName() != null) {
                    filename.append("<").append(MimeUtility.decodeText(bodyPart.getFileName())).append(">;");
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            getFilename((Part) part.getContent());
        }
        return filename.toString();
    }

    public static String getMailText(Part part) throws MessagingException, IOException {
        StringBuilder contentStr = new StringBuilder();
        if (part.isMimeType("text/*") && !(part.getContentType().indexOf("name") > 0)) {
            contentStr.append(part.getContent());
        } else if (part.isMimeType("text/html")) {
            contentStr.append(part.getContent());
        } else if (part.isMimeType("message/rfc822")) {
            getMailText((Part) part.getContent());
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int len = multipart.getCount();
            for (int i = 0; i < len; i++) {
                getMailText(multipart.getBodyPart(i));
            }
        }

        return "";
    }

    public static Message findMessage(Message[] messages, String condition) throws MessagingException, IOException {
        for (Message message : messages) {
            if (message.getSubject().equals(condition)) {
                return message;
            }
        }
        return null;
    }

    public static String showMailMessage(Message message) throws MessagingException, IOException {
        String mailText;
        if (message == null) {
            mailText = "MESSAGE IS NULL";
            return mailText;
        }
        InternetAddress[] from = (InternetAddress[]) message.getFrom();
        InternetAddress[] to = (InternetAddress[]) message.getRecipients(Message.RecipientType.TO);
        InternetAddress[] cc = (InternetAddress[]) message.getRecipients(Message.RecipientType.CC);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mailText = "=================begin============" +
                "\r\n发送日期:" + simpleDateFormat.format(message.getSentDate()) +
                "\r\n是否已读：" + message.getFlags().contains(Flags.Flag.SEEN) +
                "\r\n邮件大小：" + message.getSize() / 1024 + "(KB)" +
                "\r\n发件人:" + (from == null ? "" : getMailbox(from)) +
                "\r\n收件人" + (to == null ? "" : getMailbox(to)) +
                "\r\n抄送人:" + (cc == null ? "" : getMailbox(cc)) +
                "\r\n标题:" + message.getSubject() +
                "\r\n邮件正文:" + getMailText(message) +
                "\r\n是否包含附件:" + isContainAttachment(message) +
                "\r\n附件文件名称:" + getFilename(message) +
                "\r\n=================end==============";
        return mailText;
    }

    private static String getMailbox(InternetAddress[] addresses) {
        int i = addresses.length;
        StringBuilder mailbox = new StringBuilder();
        while (i > 0) {
            mailbox.append("<").append(addresses[i - 1].getAddress()).append(">; ");
            i--;
        }
        return mailbox.toString();
    }

}