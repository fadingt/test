package org.vaalbara.utils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by lizq on 2017/4/11.
 */
public class JavaMailLearn {
    /*public static void main(String[] args) throws Exception {
        //设置参数
    	// 初始化连接邮件服务器的会话信息
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.host", "smtp.qq.com");//邮件服务器
        prop.setProperty("mail.transport.protocol", "smtp");//SMTP邮件传输协议
        prop.setProperty("mail.smtp.auth", "true");     //是否要验证用户
        prop.setProperty("mail.smtp.port", "465");//邮件服务器端口号
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.port", "465");

        //使用JavaMail发送邮件5个步骤
        //1.创建session
        Session session = Session.getInstance(prop);
        //开启session的debug模式 查看程序发送Email的运行状态
        session.setDebug(true);
        //2.通过session得到transport对象
        Transport ts = session.getTransport();
        //3.使用邮箱的用户名和密码连上邮件服务器，发件人需要
        //提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够
        //正常发送邮件给收件人  QQ邮箱需要授权码
        ts.connect("smtp.qq.com", "1763983247@qq.com", "nbwhekbgdldhceae");
        //4.创建邮件
        Message message = createSimpleMail(session);//message代表一封电子邮件
        //5.发送邮件
        ts.sendMessage(message,message.getAllRecipients());
        ts.close();

    }*/
    public static void ready(String mail,String s) throws Exception{
        //设置参数
        // 初始化连接邮件服务器的会话信息
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.host", "smtp.qq.com");//邮件服务器
        prop.setProperty("mail.transport.protocol", "smtp");//SMTP邮件传输协议
        prop.setProperty("mail.smtp.auth", "true");     //是否要验证用户
        prop.setProperty("mail.smtp.port", "465");//邮件服务器端口号
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.port", "465");

        //使用JavaMail发送邮件5个步骤
        //1.创建session
        Session session = Session.getInstance(prop);
        //开启session的debug模式 查看程序发送Email的运行状态
        session.setDebug(true);
        //2.通过session得到transport对象
        Transport ts = session.getTransport();
        //3.使用邮箱的用户名和密码连上邮件服务器，发件人需要
        //提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够
        //正常发送邮件给收件人  QQ邮箱需要授权码
        ts.connect("smtp.qq.com", "wu981109334@qq.com", "xyfioqkgoeoxbchg");
        //4.创建邮件
        Message message = createSimpleMail(session,mail,s);//message代表一封电子邮件
        //5.发送邮件
        ts.sendMessage(message,message.getAllRecipients());
        ts.close();
        System.out.println("我发送了");
    }

    private static MimeMessage createSimpleMail(Session session,String mail,String s) throws Exception {
        //创建邮件对象
        MimeMessage message=new MimeMessage(session);
        //指定发件人
        message.setFrom(new InternetAddress("wu981109334@qq.com"));
        //指定收件人  自己给自己发
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(mail));
        //邮件标题
        message.setSubject("");
        //邮件的文本内容
        message.setContent(s,"text/html;charset=utf-8");
        return message;
    }
}