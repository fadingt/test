package action;

import domain.paasaom.User;
import org.springframework.stereotype.Controller;
import utils.AomClinet;
import utils.HibernateUtil;
import utils.MailUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 12/3/2020 2:47 PM
 */

@Controller
public class AomAuth {
    /**
     * 将角色批量授权给用户
     *
     * @param hql    要授权的用户列表HQL 查询结果必须是List<User>
     * @param roleid 要授权的角色主键ID
     */
    public void auth(String hql, int roleid) {
        List<User> userlist = HibernateUtil.listByHql(hql);
        auth(userlist, roleid);
    }

    private void auth(List<User> users, int roleid) {
//        StringBuilder resultstr = new StringBuilder();
        AomClinet clinet = new AomClinet();
        clinet.login("admin", "AGREE123", AomClinet.PWD_ORIGIN);
        if (users.size() == 0) {
            return;
        }
        for (User user : users) {
            boolean result = clinet.authRole(user, roleid);
            if (result) {
//                resultstr.append("<").append(user.getUsername()).append(">").append("授权成功");
            }else{
//                resultstr.append("<").append(user.getUsername()).append(">").append("授权失败");
            }
        }
    }

    /**
     * @return 执行结果success fail
     */
    public String authTec() {
        String hql;
        hql = "select distinct a from User a right outer join CustTech b with a.userid = b.techid " +
                "where locate(',21',a.roleids)=0 and a.states = 1";
//        auth(hql,21);
        List<User> list = HibernateUtil.listByHql(hql);
        if (list.size() == 0) {
            // TODO: 2/23/2021 记录log
            System.out.println("无客户经理需要授权");
            return "{result:\"没有需要授权的客户经理\"}";
        }
        StringBuilder textBody = new StringBuilder("您好：\n根据维护的各条线的客户经理信息，授权");
        AomClinet clinet = new AomClinet();
        clinet.login("admin", "AGREE123", AomClinet.PWD_ORIGIN);
        InternetAddress[] toAddress = new InternetAddress[list.size()];
        InternetAddress[] ccAddress = new InternetAddress[2];
        try {
            ccAddress[0] = new InternetAddress("jiao.zl@agree.com.cn");
            ccAddress[1] = new InternetAddress("lidongnan@agree.com.cn");
        } catch (AddressException e) {
            // TODO: 12/3/2020
            e.printStackTrace();
        }
        int cnt = 0;
        for (User user : list) {
            user.setRoleids(user.getRoleids() + ",21");
            textBody.append("<").append(user.getUsername()).append(">");
            clinet.resetUser(clinet.parseUser(user));
            if (user.getMailbox() == null || "".equals(user.getMailbox())) {
                textBody.append("\n由于").append(user.getUsername()).append("的用户邮箱信息为空,无法转发邮件给他，请代为告知！");
            }
            try {
                // TODO: 2/18/2021 用户邮箱为空的情况 邮箱地址错误的情况(空字符串等)
                toAddress[cnt] = new InternetAddress(user.getMailbox());
            } catch (AddressException e) {
                // TODO: 12/3/2020
                return "{result:fail}";
            }
            cnt++;
        }
        MailUtils mailUtils = new MailUtils();
        mailUtils.init();
        // TODO: 12/3/2020 将邮件列表 定义成常量或配置文件 方便修改。 通过网页修改邮件列表
        try {
            Message message = new MimeMessage(mailUtils.getSMTPSession());
            message.setFrom(new InternetAddress("support@agree.com.cn"));
            message.setRecipients(Message.RecipientType.TO, toAddress);
            message.setRecipients(Message.RecipientType.CC, ccAddress);
            message.setSubject("AOM客户经理授权[自动]");
            textBody.append("<职能管理-客户经理>权限。\n请点击系统头像下的清除缓存重试。");
            message.setText(textBody.toString());
            mailUtils.sendEmail(message);
            // TODO: 12/3/2020 返回已经授权的客户经理名单
            return "{result:success}";
        } catch (MessagingException | IOException e) {
            return "{result:fail}";
        }
    }

    public String authSale() {
        String hql;
        hql = "select distinct a from User a right outer join CustSale b with a.userid = b.saleid " +
                "where locate(',20',a.roleids)=0 and a.states = 1";
        List<User> list = HibernateUtil.listByHql(hql);
        if (list.size() == 0) {
            System.out.println("没有需要授权的销售代表");
            return "{result:\"没有需要授权的销售代表\"}";
        }
        StringBuilder textBody = new StringBuilder("您好：\n根据客户中维护的销售信息，已授权");
        AomClinet clinet = new AomClinet();
        clinet.login("admin", "AGREE123", AomClinet.PWD_ORIGIN);
        InternetAddress[] toAddress = new InternetAddress[list.size()];
        InternetAddress[] ccAddress = new InternetAddress[2];
        try {
            ccAddress[0] = new InternetAddress("jiao.zl@agree.com.cn");
            ccAddress[1] = new InternetAddress("meng.fx@agree.com.cn");
        } catch (AddressException e) {
            // TODO: 12/3/2020
            e.printStackTrace();
        }
        int cnt = 0;
        for (User user : list) {
            user.setRoleids(user.getRoleids() + ",20");
            textBody.append("<").append(user.getUsername()).append(">");
            clinet.resetUser(clinet.parseUser(user));
            try {
                toAddress[cnt] = new InternetAddress(user.getMailbox());
            } catch (AddressException e) {
                // TODO: 12/3/2020
                return "{result:fail}";
            }
            cnt++;
        }
        MailUtils mailUtils = new MailUtils();
        mailUtils.init();
        // TODO: 12/3/2020 将邮件列表 定义成常量或配置文件 方便修改。 通过网页修改邮件列表
        try {
            Message message = new MimeMessage(mailUtils.getSMTPSession());
            message.setFrom(new InternetAddress("support@agree.com.cn"));
            message.setRecipients(Message.RecipientType.TO, toAddress);
            message.setRecipients(Message.RecipientType.CC, ccAddress);
            message.setSubject("AOM销售代表授权[自动]");
            textBody.append("<职能管理-销售>权限。\n请点击系统头像下的清除缓存重试。");
            message.setText(textBody.toString());
            mailUtils.sendEmail(message);
            // TODO: 12/3/2020 返回已经授权的客户经理名单
            return "{result:success}";
        } catch (MessagingException | IOException e) {
            return "{result:fail}";
        }
    }


}


