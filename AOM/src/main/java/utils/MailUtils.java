package utils;

import config.MailboxConfig;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

public final class MailUtils {
    private MailboxConfig config;

    // TODO: 11/27/2020 通过初始化config(steam)没有File getProp()方法异常
    public boolean init() {
//        InputStream stream = MailUtils.class.getClassLoader().getResourceAsStream("mailbox.properties");
//        this.config = new MailboxConfig(stream);
//        String filepath = Objects.requireNonNull(MailUtils.class.getClassLoader().getResource("mailbox.properties")).getPath();
        String filepath = Objects.requireNonNull(MailUtils.class.getClassLoader().getResource("supportMailbox.properties")).getPath();
        try {
            this.config = new MailboxConfig(new File(filepath));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // TODO: 8/13/2020 FUNCTION:将接受数据内容本地保存 比如保存到EXCEL中
    public static void main(String[] args) throws MessagingException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String now = dateFormat.format(new Date());
        MailUtils mailUtils = new MailUtils();
        mailUtils.init();
        Message[] messages = mailUtils.receiveEmail();
        if (messages.length > 0) {
            for (Message message : messages) {
                if (dateFormat.format(message.getSentDate()).equals(now)) {
                    String messageText = showMailMessage(message);
                    String filename = "NO" + message.getMessageNumber() + ".txt";
                    FileUtils.saveFile(messageText, "D:/JAVAIO_TEMP/supMail/" + filename);
                }
            }
        }
    }

    public Message[] receiveEmail() throws MessagingException, IOException {
        Store store = getPOP3Session().getStore("pop3");
        store.connect(this.config.getMailbox(), this.config.getPassword());
        Folder folder = store.getFolder("Inbox");
        if (!folder.exists()) {
            throw new RuntimeException("Inbox NOT FOUND");
        }
        folder.open(Folder.READ_ONLY);
        return folder.getMessages();
    }

    // TODO: 11/30/2020 方法应该是private 如何构造message而不暴露session
    public Session getSMTPSession() throws IOException {
        Properties prop = this.config.getProp();
        prop.setProperty("mail.transport.protocol", "smtp");
        return Session.getInstance(prop);
    }

    private Session getPOP3Session() throws IOException {
        Properties prop = this.config.getProp();
        prop.setProperty("mail.transport.protocol", "pop3");
        return Session.getInstance(prop);
    }

    public boolean sendEmail(Message message) {
        if (this.config == null) {
            return false;
        }
        Transport transport;
        try {
            transport = getSMTPSession().getTransport();
            transport.connect(this.config.getMailbox(), this.config.getPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (MessagingException | IOException e) {
            // TODO: 12/3/2020 tell the reason of failure
            return false;
        }
    }

    // TODO: 11/27/2020 发送人超过1个 有抄送人 有附件 等情况
    public boolean sendEmail(String subject, String text, String toAdreess) {
        try {
            InternetAddress[] addresses = {new InternetAddress(toAdreess)};
            return sendEmail(subject, text, addresses, null);
        } catch (AddressException e) {
            return false;
        }
    }

    public boolean sendEmail(String subject, String text, Address[] toAddress, Address[] ccAddress) {
        if (this.config == null) {
            return false;
        }
        Session session;
        try {
            session = getSMTPSession();
            Transport transport = session.getTransport();
            transport.connect(this.config.getMailbox(), this.config.getPassword());
            Message message = new MimeMessage(getSMTPSession());
            message.setFrom(new InternetAddress(this.config.getMailbox()));
            message.setRecipients(Message.RecipientType.TO, toAddress);
            message.setRecipients(Message.RecipientType.CC, ccAddress);
            message.setSubject(subject);
            message.setText(text);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (SendFailedException e) {
            Address[] unsent = e.getValidUnsentAddresses();
            Address[] invalid = e.getInvalidAddresses();
            if (invalid != null) {
                text = text + System.lineSeparator() + "以下邮箱地址错误,请转告他：";
                for (int i = 0; i < invalid.length; i++) {
                    text = text + System.lineSeparator() + invalid[i] + ";";
                }
            }
            int len = unsent.length;
            InternetAddress[] to = new InternetAddress[len];
            if (unsent != null) {
                return sendEmail(subject, text, unsent, null);
            }
            return false;
        } catch (IOException | MessagingException e) {
            // TODO: 12/3/2020 tell the reason why sendEmail() failed
            return false;
        }
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
                if (flag) {
                    break;
                }
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
            contentStr.append(getMailText((Part) part.getContent()));
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int len = multipart.getCount();
            for (int i = 0; i < len; i++) {
                contentStr.append(getMailText(multipart.getBodyPart(i)));
            }
        }
        return contentStr.toString();
    }

    public static Message findMessage(Message[] messages, String subject) throws MessagingException {
        for (Message message : messages) {
            if (message.getSubject().equals(subject)) {
                return message;
            }
        }
        return null;
    }

    public static String showMailMessage(Message message) throws MessagingException, IOException {
        String mailText;
        if (message == null) {
            return "";
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