package action;

import domain.paasaom.User;
import org.junit.jupiter.api.Test;
import utils.HibernateUtil;
import utils.MailUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 12/9/2020 4:20 PM
 */
class AomAuthTest {

    @Test
    void authTecTest() {
        AomAuth aomAuth = new AomAuth();
        aomAuth.authTec();
    }

    @Test
    void authSale() {
        AomAuth auth = new AomAuth();
        auth.authSale();
    }

    @Test
    void authTest() {
        AomAuth aomAuth = new AomAuth();
        String hql;
//        hql = "select distinct a from User a " +
//                "where a.orgcode='0001001026006' " +
//                "and locate(',76',a.roleids)=0 and a.states = 1 ";
//        int roleid = 76;
        hql = "select distinct a from User a right outer join CustTech b with a.userid = b.techid " +
                "where locate(',21',a.roleids)=0 and a.states = 1";
        int roleid = 21;
        aomAuth.auth(hql, roleid);
    }

    @Test
    void authTest2() {
        AomAuth auth = new AomAuth();
        String hql = "select distinct a from User a right outer join CustSale b with a.userid = b.saleid " +
                "where locate(',20',a.roleids)=0 and a.states = 1";
        int roleid = 20;
        auth.auth(hql, roleid);
    }

    @Test
    void authTest3() {
        AomAuth auth = new AomAuth();
        String hql = "select a from User a where a.orgcode='0001001011006' and locate(',1006',a.roleids)=0 and a.states = 1";
        int roleid = 1006;
        List<User> userlist = HibernateUtil.listByHql(hql);

        auth.auth(hql,roleid);

        StringBuilder textBody = new StringBuilder("您好：  \r\n根据所属部门商务部，已授权");
        InternetAddress[] toAddress = new InternetAddress[userlist.size()];
        InternetAddress[] ccAddress = new InternetAddress[2];
        try {
            ccAddress[0] = new InternetAddress("jiao.zl@agree.com.cn");
            ccAddress[1] = new InternetAddress("wangyan@agree.com.cn");
        } catch (AddressException e) {
            e.printStackTrace();
        }
        int cnt=0;
        for (User user:userlist) {
            textBody.append("<").append(user.getUsername()).append(">");
            try {
                toAddress[cnt] = new InternetAddress(user.getMailbox());
            } catch (AddressException e) {
                e.printStackTrace();
            }
            cnt++;
        }
        textBody.append("<职能管理-商务>权限。  \r\n请点击系统头像下的清除缓存重试");
        MailUtils mailUtils = new MailUtils();
        mailUtils.init();
        boolean result = mailUtils.sendEmail("AOM商务授权[自动]",textBody.toString(),toAddress,ccAddress);
        System.out.println(result);
    }
}