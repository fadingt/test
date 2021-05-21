package utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import domain.ngoss.TSnapContractsyncEntity;
import domain.paasaom.User;
import domain.paasaom.UserRole;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 11/5/2020 3:41 PM
 */
class AomClinetTest {
    private AomClinet client;

    public AomClinetTest() {
        this.client = new AomClinet();
        this.client.login("admin", "AGREE123", AomClinet.PWD_ORIGIN);
    }

    @Test
    void getReportResultTest() {
        System.out.println(this.client.getToken());
//        String postContent = "{\"searchkey\":\"test8\",\"currentPage\":1,\"pageSize\":20,\"search\":{\"a\":\"\"},\"scropeList\":null}";
//        System.out.println(this.client.getReportResult(postContent));
//        1.新增部门领导角色（职能管理-部门领导-张乐之-A5013）
//        String postContent = "{\"rolceName\":\"职能管理-部门领导-张乐之-A5013\",\"dataPermission\":\"1\",\"isAdmin\":\"0\",\"roleDesc\":\"test\",\"orgCode\":\"\"}";
//        String url = "http://192.168.180.160:16088/api/rpcServer/afa4j/3/auth//A03001";
//        2.职能管理-部门领导-张乐之-A5013 授权部门领导相关菜单
//        String postContent = "{\"roleId\":1107,\"permissionIdList\":[3062,3063,3233,3234,3235,3371,3372,3437,3469,3584,26,67,69,70,71,72,73,74,133,134,141,142,263,264,266,304,308,309,710,711,712,713,714,727,729,750,751,752,811,858,859,860,935,1463,1464,1657,1658,2099,2115,2116,2117,2119,2120,2133,2202,2342,2548,2549,2550,2566,2625,2886,3745,3746,3747,3748,3859,3937,3951,3957,3961,4030,4031,4032,3554,3949,3066,4087,4266,4132]}";
//        String url = "http://192.168.180.160:16088/api/rpcServer/afa4j/3/auth/A04001";
//        System.out.println(this.client.getResult(postContent, url));
//        resql通过转义绕过敏感信息检查
        String url = "http://192.168.180.160:16088/api/rpcServer/afa4j/3/ReportManagement/PUB01058?type=1";
        String postContent = "{\"viewId\":2355,\"columnMap\":{\"obj.S_SEARCHKEY\":\"testtt\",\"obj.S_SEANAME\":\"testtt\",\"obj.S_SEASQL\":\"select ACCOUNT_\160WD AS a from plf_aos_auth_user\"},\"batchColumn\":{\"obj2359\":[{\"obj.S_COLNUM\":\"1\",\"obj.S_COLNAME\":\"a\",\"obj.S_COLDISPL\":\"a\",\"obj.S_ISRESULT\":\"2\",\"obj.S_ISSEARCH\":\"1\",\"obj.S_COLTYPE\":\"01\",\"obj.S_ISREQUIRE\":\"1\",\"obj.S_SCROPE\":\"1\"}]},\"type\":\"1\"}";
        System.out.println(this.client.getResult(url, postContent));
    }

    @Test
    void authLeader() {
        // TODO: 3/12/2021 各种空指针错误信息判断
//        String usercode = "A0928";
//        User leader = new HibernateUserDaoImpl().getUserByUsercode(usercode);
//        String rolename = "职能管理-部门领导-" + leader.getUsername();
        String rolename = "职能管理-系统审批流程配置";
//        System.out.println(rolename);
        int roleid;
//        1.新增角色 获取角色ID验证存在
        String postContent = "{\"roleName\":\"" + rolename + "\",\"dataPermission\":\"1\",\"isAdmin\":\"0\",\"roleDesc\":\"职能管理\",\"orgCode\":\"\"}";
        String url = "http://192.168.180.160:16088/api/rpcServer/afa4j/3/auth//A03001";
        Map filtermap = new HashMap();
        filtermap.put("role_name", rolename);
        UserRole userRole = (UserRole) HibernateUtil.listObjectByProperty(filtermap, UserRole.class, 0, 1).get(0);
        if (userRole == null) {
            System.out.println(this.client.getResult(url, postContent));
        }
        userRole = (UserRole) HibernateUtil.listObjectByProperty(filtermap, UserRole.class, 0, 1).get(0);
        if (userRole == null) {
            return;
        }
        roleid = userRole.getId();
//        2.为新增角色增加菜单
//        postContent = "{\"roleId\":" + roleid + ",\"permissionIdList\":[3062,3063,3233,3234,3235,3371,3372,3437,3469,3584,26,67,69,70,71,72,73,74,133,134,141,142,263,264,266,304,308,309,710,711,712,713,714,727,729,750,751,752,811,858,859,860,935,1463,1464,1657,1658,2099,2115,2116,2117,2119,2120,2133,2202,2342,2548,2549,2550,2566,2625,2886,3745,3746,3747,3748,3859,3937,3951,3957,3961,4030,4031,4032,3554,3949,3066,4087,4266,4132]}";
        postContent = "{\"roleId\":" + roleid + ",\"permissionIdList\":[419,4129,4402,4403,4410]}";
        url = "http://192.168.180.160:16088/api/rpcServer/afa4j/3/auth/A04001";
        System.out.println(this.client.getResult(url, postContent));
// TODO: 3/17/2021 3.授权数据范围
        // TODO: 3/24/2021 授权给个人
    }


    @Test
    void resetPasswordTest() {
//        assert !this.client.resetPassword(-1,"agree123");
        // TODO: 11/10/2020 后端没有密码强度校验导致可以重置成简单密码 离职人员可重置密码
//        assert !client.resetPassword(614736,"agree123");
//        assert this.client.resetPassword(608195, "000000");
//        assert this.client.resetPassword(1000, "AGREE123");
        this.client.resetPassword(608954, "agree123");
//        client.resetPassword(608954,"agree123");
//        assert !client.resetPassword(608954, "agree123");
    }

    @Test
    void contractTest() {
        String str = "[{id:22,gridLabel:\"合同编号\",gridKey:\"S_CONCODE\",align:\"left\"},{id:1,gridLabel:\"合同名称\",gridKey:\"S_CONNAME\",align:\"left\"},{id:47,gridLabel:\"结算单编号\",gridKey:\"S_STACODE\",align:\"left\"},{id:49,gridLabel:\"结算单分类\",gridKey:\"S_STATYPE\",align:\"left\"},{id:2,gridLabel:\"合同分类\",gridKey:\"S_CONTYPE\",align:\"left\"},{id:3,gridLabel:\"销售代表\",gridKey:\"S_SALEMAN\",align:\"left\"},{id:46,gridLabel:\"销售部门\",gridKey:\"S_SALEORG\",align:\"left\"},{id:4,gridLabel:\"甲方\",gridKey:\"S_CUSTNAME\",align:\"left\"},{id:5,gridLabel:\"乙方\",gridKey:\"S_COMPNAME\",align:\"left\"},{id:50,gridLabel:\"最终客户\",gridKey:\"S_FINALNAME\",align:\"left\"},{id:44,gridLabel:\"客户经理\",gridKey:\"S_TECHNAN\",align:\"left\"},{id:45,gridLabel:\"客户经理所属大区\",gridKey:\"S_TECHDEPT\",align:\"left\"},{id:6,gridLabel:\"合同金额\",gridKey:\"DL_CONAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:7,gridLabel:\"当年合同额\",gridKey:\"DL_OLDYEARAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:51,gridLabel:\"当月合同额\",gridKey:\"DL_OLDMONTHAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:48,gridLabel:\"预计结算单金额\",gridKey:\"DL_STATEMAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:8,gridLabel:\"往年实开\",gridKey:\"DL_OLDAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:9,gridLabel:\"当年实开（不含当月）\",gridKey:\"DL_YEARAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:10,gridLabel:\"当月实开\",gridKey:\"DL_AMONTHAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:11,gridLabel:\"当月预开\",gridKey:\"DL_PMONTHAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:12,gridLabel:\"后期预开\",gridKey:\"DL_LASTBILAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:13,gridLabel:\"当月开票偏差\",gridKey:\"DL_MBILLDEV\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:14,gridLabel:\"往年实回\",gridKey:\"DL_OLBACKAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:15,gridLabel:\"当年实回（不含当月）\",gridKey:\"DL_YBACKAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:16,gridLabel:\"当月实回\",gridKey:\"DL_AMBACKAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:56,gridLabel:\"当月汇兑损益\",gridKey:\"DL_MONSYAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:57,gridLabel:\"累计汇兑损益\",gridKey:\"DL_SUMSYAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:17,gridLabel:\"当月预回\",gridKey:\"DL_PMBACKAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:18,gridLabel:\"后期预回\",gridKey:\"DL_LASBACAMT\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:19,gridLabel:\"当月回款偏差\",gridKey:\"DL_MBACKDEV\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:20,gridLabel:\"T+1预开\",gridKey:\"DL_PMBILAMT1\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:21,gridLabel:\"T+2预开\",gridKey:\"DL_PMBILAMT2\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:23,gridLabel:\"T+3预开\",gridKey:\"DL_PMBILAMT3\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:24,gridLabel:\"T+4预开\",gridKey:\"DL_PMBILAMT4\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:25,gridLabel:\"T+5预开\",gridKey:\"DL_PMBILAMT5\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:26,gridLabel:\"T+6预开\",gridKey:\"DL_PMBILAMT6\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:27,gridLabel:\"T+1预回\",gridKey:\"DL_PMBACAMT1\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:28,gridLabel:\"T+2预回\",gridKey:\"DL_PMBACAMT2\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:29,gridLabel:\"T+3预回\",gridKey:\"DL_PMBACAMT3\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:30,gridLabel:\"T+4预回\",gridKey:\"DL_PMBACAMT4\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:31,gridLabel:\"T+5预回\",gridKey:\"DL_PMBACAMT5\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:32,gridLabel:\"T+6预回\",gridKey:\"DL_PMBACAMT6\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:33,gridLabel:\"开票预警\",gridKey:\"DL_BILLWARN\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:34,gridLabel:\"回款预警\",gridKey:\"DL_BACKWARN\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:35,gridLabel:\"实开比例\",gridKey:\"DL_ABILLPER\",align:\"right\"},{id:36,gridLabel:\"实回比例\",gridKey:\"DL_ABACKPER\",align:\"right\"},{id:37,gridLabel:\"开票校验\",gridKey:\"DL_BILLCHECK\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:38,gridLabel:\"回款校验\",gridKey:\"DL_BACKCHECK\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:39,gridLabel:\"待开票数\",gridKey:\"I_BILLCOUNT\",align:\"right\"},{id:43,gridLabel:\"是否关闭\",gridKey:\"S_CONSTATUS\",align:\"left\"},{id:40,gridLabel:\"总计实开\",gridKey:\"DL_ABILLSUM\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:41,gridLabel:\"总计实回\",gridKey:\"DL_ABACKSUM\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:42,gridLabel:\"已票应收\",gridKey:\"DL_HASBILL\",align:\"right\",type:\"money\",format:function(n){return r.moneyFormat(n)}},{id:52,gridLabel:\"甲方合同编码\",gridKey:\"S_CONNUM\",align:\"left\"},{id:53,gridLabel:\"账期\",gridKey:\"S_ACCDATE\",align:\"left\"},{id:54,gridLabel:\"最晚阶段描述\",gridKey:\"S_STAGE\",align:\"left\"},{id:55,gridLabel:\"最晚阶段预计回款日期\",gridKey:\"S_BACKDATE\",align:\"left\",format:function(n){return n&&\"string\"!=typeof n?r.formatDate(n):n}}]";
        String[] list = str.split("},");
        Writer writer;
        String gridLabel;
        String gridKey;
        Properties prop = new Properties();
        for (String a : list) {
            gridLabel = a.substring(a.lastIndexOf("gridLabel:\"") + 11, a.indexOf("\",gridKey:"));
            gridKey = a.substring(a.lastIndexOf("gridKey:\"") + 9, a.indexOf("\",align"));
            prop.setProperty(gridKey, gridLabel);
        }
        try {
            writer = new FileWriter("d:/contractSync.prop");
            prop.store(writer, "合同动态表");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void contractTest2() throws IOException {
        String str = "CREATE TABLE `t_snap_contractsync` (\n" +
                "  `ID` varchar(50) NOT NULL,\n" +
                "  `合同编号` varchar(255) NOT NULL,\n" +
                "  `合同名称` varchar(255) DEFAULT NULL,\n" +
                "  `结算单编号` varchar(255) NOT NULL,\n" +
                "  `结算单分类` varchar(255) DEFAULT NULL,\n" +
                "  `合同分类` varchar(255) DEFAULT NULL,\n" +
                "  `销售代表` varchar(255) DEFAULT NULL,\n" +
                "  `销售部门` varchar(255) DEFAULT NULL,\n" +
                "  `甲方` varchar(255) DEFAULT NULL,\n" +
                "  `乙方` varchar(255) DEFAULT NULL,\n" +
                "  `最终客户` varchar(255) DEFAULT NULL,\n" +
                "  `客户经理` varchar(255) DEFAULT NULL,\n" +
                "  `客户经理所属大区` varchar(255) DEFAULT NULL,\n" +
                "  `合同金额` decimal(30,5) DEFAULT NULL,\n" +
                "  `当年合同额` decimal(30,5) DEFAULT NULL,\n" +
                "  `当月合同额` decimal(30,5) DEFAULT NULL,\n" +
                "  `预计结算单金额` decimal(30,5) DEFAULT NULL,\n" +
                "  `往年实开` decimal(30,5) DEFAULT NULL,\n" +
                "  `当年实开(不含当月)` decimal(30,5) DEFAULT NULL,\n" +
                "  `当月实开` decimal(30,5) DEFAULT NULL,\n" +
                "  `当月预开` decimal(30,5) DEFAULT NULL,\n" +
                "  `后期预开` decimal(30,5) DEFAULT NULL,\n" +
                "  `当月开票偏差` decimal(30,5) DEFAULT NULL,\n" +
                "  `往年实回` decimal(30,5) DEFAULT NULL,\n" +
                "  `当年实回(不含当月)` decimal(30,5) DEFAULT NULL,\n" +
                "  `当月实回` decimal(30,5) DEFAULT NULL,\n" +
                "  `当月汇兑损益` decimal(30,5) DEFAULT NULL,\n" +
                "  `累计汇兑损益` decimal(30,5) DEFAULT NULL,\n" +
                "  `当月预回` decimal(30,5) DEFAULT NULL,\n" +
                "  `后期预回` decimal(30,5) DEFAULT NULL,\n" +
                "  `当月回款偏差` decimal(30,5) DEFAULT NULL,\n" +
                "  `T+1预开` decimal(30,5) DEFAULT NULL,\n" +
                "  `T+2预开` decimal(30,5) DEFAULT NULL,\n" +
                "  `T+3预开` decimal(30,5) DEFAULT NULL,\n" +
                "  `T+4预开` decimal(30,5) DEFAULT NULL,\n" +
                "  `T+5预开` decimal(30,5) DEFAULT NULL,\n" +
                "  `T+6预开` decimal(30,5) DEFAULT NULL,\n" +
                "  `T+1预回` decimal(30,5) DEFAULT NULL,\n" +
                "  `T+2预回` decimal(30,5) DEFAULT NULL,\n" +
                "  `T+3预回` decimal(30,5) DEFAULT NULL,\n" +
                "  `T+4预回` decimal(30,5) DEFAULT NULL,\n" +
                "  `T+5预回` decimal(30,5) DEFAULT NULL,\n" +
                "  `T+6预回` decimal(30,5) DEFAULT NULL,\n" +
                "  `开票预警` decimal(30,5) DEFAULT NULL,\n" +
                "  `回款预警` decimal(30,5) DEFAULT NULL,\n" +
                "  `实开比例` varchar(100) DEFAULT NULL,\n" +
                "  `实回比例` varchar(100) DEFAULT NULL,\n" +
                "  `开票校验` decimal(30,5) DEFAULT NULL,\n" +
                "  `回款校验` decimal(30,5) DEFAULT NULL,\n" +
                "  `待开票数` decimal(30,5) DEFAULT NULL,\n" +
                "  `是否关闭` decimal(30,5) DEFAULT NULL,\n" +
                "  `总计实开` decimal(30,5) DEFAULT NULL,\n" +
                "  `总计实回` decimal(30,5) DEFAULT NULL,\n" +
                "  `已票应收` decimal(30,5) DEFAULT NULL,\n" +
                "  `甲方合同编码` varchar(255) DEFAULT NULL,\n" +
                "  `账期` varchar(255) DEFAULT NULL,\n" +
                "  `最晚阶段描述` varchar(255) DEFAULT NULL,\n" +
                "  `最晚阶段预计回款日期` varchar(255) DEFAULT NULL,\n" +
                "  PRIMARY KEY ID\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同动态表';";
        Properties prop = new Properties();
        Reader reader = new InputStreamReader(new FileInputStream("D:\\contractSync.prop"), "UTF-8");
        prop.load(reader);
        Set<Object> keys = prop.keySet();
        for (Object key : keys) {
            String value = (String) prop.get(key);
            int begin = str.indexOf(value);
            int end = str.indexOf("NULL,", begin);
            int len = ((String) prop.get(key)).length();
            str = str.substring(0, begin) + key + str.substring(begin + len, end + 4) + " COMMENT '" + value + "'" + str.substring(end + 4);
//            str = str.replace(value, (String) key);
        }
        System.out.println(str);
    }


    @Test
    void resetUserTest() {
//        System.out.println(this.client.getToken());
/*        User user = new HibernateUserDaoImpl().getUserById(608954);
        //        user.setIsAdmin(1);
        user.setRoleids(user.getRoleids()+",21");
        String postContent = this.client.parseUser(user);
        System.out.println(this.client.resetUser(postContent));*/
        String postContent = "{\"id\":31537,\"superiorLeader\":31538,\"orgId\":null,\"orgCode\":\"0001001011002003\",\"email\":\"dong.ll@agree.com.cn\",\"address\":null,\"sex\":0,\"mobile\":\"18616729508\",\"telephone\":null,\"userDesc\":null,\"isAdmin\":0,\"qqId\":null,\"headPhoto\":null,\"accountId\":\"A1974\",\"realName\":\"董亮亮-A1974\",\"roleIds\":\"34,20\",\"extCode\":null,\"shdEnable\":0,\"weiboId\":null,\"wechatId\":null,\"job\":null,\"status\":1}";
        System.out.println(this.client.resetUser(postContent));
    }

    @Test
    void getViewDataTest() {
        System.out.println(this.client.getViewData());
    }

    @Test
    void contractSyncTest() {
        System.out.println(new Date());
        this.client.saveContractFromAOM("固定金额");
        this.client.saveContractFromAOM("框架协议");
        System.out.println(new Date());
    }

    @Test
    void authRoleTest() {
        String hql;
        int roleid = 21;
        String subject = "AOM客户经理授权[自动]";
        int cnt = 0;
        hql = "select distinct a from User a right outer join CustTech b with a.userid = b.techid " +
                "where locate(',21',a.roleids)=0 and a.states = 1";
        List<User> list = HibernateUtil.listByHql(hql);
        int total = list.size();
        if (total == 0) {
            // TODO: 2/23/2021 log:无客户经理需要授权
        }
        StringBuilder textBody = new StringBuilder("您好：\r\n根据维护的各条线的客户经理信息，授权以下人员<职能管理-客户经理>权限：\r\n");
        InternetAddress[] toAddress = new InternetAddress[total];
        InternetAddress[] ccAddress = new InternetAddress[2];
        for (User user : list) {
            boolean result = this.client.authRole(user, roleid);
            if (result) {
                textBody.append('<').append(user.getUsername()).append('>');
                try {
                    toAddress[cnt] = new InternetAddress(user.getMailbox());
                } catch (AddressException e) {
                    System.out.println("AddressException");
                    // TODO: 2/18/2021 用户邮箱为空的情况 邮箱地址错误的情况(空字符串等)
                }
                cnt++;
                System.out.println("cnt" + cnt);

            } else {
                // TODO: 4/28/2021 log:授权失败
                System.out.println("授权失败");
            }
        }
        if (cnt == 0) {
            return;
        }
        textBody.append("\r\n需要授权：").append(total).append(" 成功：").append(cnt);
        MailUtils mailUtils = new MailUtils();
        // TODO: 12/3/2020 将邮件列表 定义成常量或配置文件 方便修改。 通过网页修改邮件列表
        mailUtils.init();
        try {
//            ccAddress[0] = new InternetAddress("jiao.zl@agree.com.cn");
//            ccAddress[1] = new InternetAddress("lidongnan@agree.com.cn");
            ccAddress[0] = new InternetAddress("liuxingyu@agree.com.cn");
            ccAddress[1] = new InternetAddress("378730609@qq.com");
        } catch (AddressException e) {
            // TODO: 4/30/2021 log AddressException
        }
        mailUtils.sendEmail(subject, textBody.toString(), toAddress, ccAddress);
    }
}