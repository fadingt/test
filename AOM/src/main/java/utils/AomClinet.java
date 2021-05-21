package utils;

import com.alibaba.fastjson.JSONArray;
import dao.UserDao;
import dao.WorkflowDao;
import dao.impl.WorkflowDaoImpl;
import domain.Token;
import domain.ngoss.TSnapContractsyncEntity;
import domain.paasaom.User;
import domain.paasaom.Workflow;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


/**
 * 通过login()方法向AOM模拟登陆请求并获取token
 * <br>然后利用获取到的token进行特权操作;
 * 一些方法(authRole())会记录日志
 *
 * @author fadingt
 * @date 2021.4.22
 */
public class AomClinet extends HttpClient {
    private Token token;
    private String authorization;
    private String usercode;
    private static final Logger LOGGER = LogManager.getLogger(AomClinet.class);
    public final static short PWD_ORIGIN = 0;
    public final static short PWD_LDAP = 1;
    public final static short PWD_MD5 = 2;

    /**
     * 此方法试图使用参数提供的工号和密码模拟登陆AOM
     * <br>如果失败则返回false 并记录日志
     * <br>如果登陆成功会记录token和工号,并可以使用{@link AomClinet#getToken()}{@link AomClinet#getUsercode()}方法获取
     *
     * @param usercode 用户工号
     * @param password 用户密码
     * @param type     用户密码的加密类型
     * @return 登陆是否成功;true代表成功
     */
    public boolean login(String usercode, String password, int type) {
        switch (type) {
            case PWD_ORIGIN:
                password = EncryptUtils.getMD5(password);
                break;
            case PWD_LDAP:
                password = password.replace("{MD5}", "");
                password = new BigInteger(1, java.util.Base64.getDecoder().decode(password.getBytes())).toString(16);
                break;
            case PWD_MD5:
                break;
            default:
                throw new IllegalArgumentException("type加密方式未知，无法处理");
        }
        StringBuilder params = new StringBuilder("grant_type=password").append("&username=").append(usercode).append("%40AOM").append("&password=").append(password);
        Map<String, String> headers = new HashMap<>(2);
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("Authorization", "Basic d2ViYXBwOndlYmFwcA==");
        try {
            this.token = new Token(sendPost("http://192.168.180.160:16088/api/uaa/oauth/token", params.toString(), "UTF-8", headers));
            this.authorization = this.token.getToken_type() + " " + this.token.getAccess_token();
            this.usercode = usercode;
            return true;
        } catch (IOException e) {
            LOGGER.error("AomClinet.login():" + e);
            return false;
        }
    }

    /**
     * 参考{@link HttpClient#sendPost(String, String, String, Map)}
     * 如果发生IOException将返回false并记录日志
     * 设置charset="UTF-8",根据token设置默认header:
     * "Content-Type", "application/json;charset=UTF-8"
     * "Authorization", this.authorization
     *
     * @param urlParam request地址
     * @param params   request内容
     * @return 请求结果;如果发生IO异常直接返回false,如果没有登陆(this.token==null)直接返回false
     * @see HttpClient#sendPost(String, String, String, Map)
     * @see AomClinet#getResult(String, String)
     */
    private boolean sendPost(String urlParam, String params) {
        if (this.token == null) {
            // TODO: 5/6/2021
            System.out.println("token is null");
            return false;
        }
        Map<String, String> headers = new HashMap<>(2);
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", this.authorization);
        try {
            sendPost(urlParam, params, "UTF-8", headers);
            return true;
        } catch (IOException e) {
            LOGGER.error("AomClient.sendPost():" + e);
            return false;
        }
    }

    /**
     * 修改AOM流程
     *
     * @param workflowid 要修改的流程ID
     * @param userid     要替换新流程的员工ID
     * @return 修改流程结果是否成功;true代表修改成功
     * todo 新AOM流程查询和替换
     * @deprecated 2021.3.11由于AOM上线版本废弃了原功能。原来的修改流程地址和数据库表有变化
     */
    public boolean updateWorkflow(int workflowid, int userid) {
        WorkflowDao workflowDao = new WorkflowDaoImpl();
        Workflow workflow = workflowDao.getWorkflowById(workflowid);
        StringBuilder desc = new StringBuilder();
        // TODO: 11/12/2020 检查是否存在这个员工 是否合法(未离职) NPE问题
        //  s_type 01 02 不能区分是否3@开头
        workflow.setS_TARGET1("3@" + userid);
        desc.append("由").append(UserDao.getUsername(Integer.parseInt(workflow.getS_TARGET1().replace("3@", ""))));
        workflow.setS_TARGET1("" + userid);
        desc.append("变更为:").append(UserDao.getUsername(userid));
        workflow.setS_DESC(desc.toString());
        JSONObject newJsonFlow = JSONObject.fromObject(workflow);
        System.out.println(newJsonFlow);
        return this.sendPost("http://192.168.180.160:16088/api/rpcServer/afa4j/3/common/workflowParam_edit", newJsonFlow.toString());

    }

    /**
     * 首先需要使用有权限重置密码的用户{@link AomClinet#login(String, String, int)} 如果没有登陆则直接返回false;
     * 遇到IOException等情况也直接返回失败的结果
     * TODO: 11/10/2020 判断结果是否成功，如果失败 是否是密码与上次密码相同引起的
     *
     * @param userid 要重置密码的用户ID
     * @param pwd    要修改成什么密码
     * @return 修改密码是否成功;true代表成功
     */
    public boolean resetPassword(int userid, String pwd) {
        return this.sendPost("http://192.168.180.160:16088/api/rpcServer/afa4j/3/common/resetPwd", "{\"userId\":" + userid + ",\"password\":\"" + pwd + "\",\"confirmPwd\":\"" + pwd + "\"}");
    }

    public boolean resetUser(String postContent) {
        String url = "http://192.168.180.160:16088/api/rpcServer/afa4j/3/auth/A02002";
        return this.sendPost(url, postContent);
    }

    /**
     * AOM系统管理-配置通用查询报表
     * 请求体内容例子:
     * {"searchkey":"test6","currentPage":1,"pageSize":20,"search":{},"scropeList":null}
     *
     * @return JSON结果
     */
    private String getReportResult(String postContent) {
        // TODO: 11/25/2020 检验发送的请求体中 包含 searchkey等关键字，并写入javadoc中 url写入常量配置
        String url = "http://192.168.180.160:16088/api/rpcServer/afa4j/3/ReportManagement/PUB01057";
        return getResult(url, postContent);
    }

    /**
     * 用于生成postContent内容
     * 添加的属性有：id,superiorLeader,orgCode,email,mobile,isAdmin,accountId,realName,roleIds,shdEnable,sex
     * <br>其中isAdmin已经在AOM某次更新后不能修改
     *
     * @param user 要转换的用户对象,需要包含足够的属性信息
     * @return 将特定用户信息转化后的JSON字符串
     * @see AomClinet#resetUser(String)
     * TODO: 12/2/2020 如何自动生成内容 不同请求url放到static固定的目录下
     */
    public String parseUser(User user) {
        return "{\"id\":" + user.getUserid() + "," +
                "\"superiorLeader\":" + user.getSuperiorLeader() + "," +
                "\"orgCode\":\"" + user.getOrgcode() + "\"," +
                "\"email\":\"" + user.getMailbox() + "\"," +
                "\"sex\":" + user.getSex() + "," +
                "\"mobile\":\"" + user.getCellphone() + "\"," +
                "\"isAdmin\":" + user.getIsAdmin() + "," +
                "\"accountId\":\"" + user.getUsercode() + "\"," +
                "\"realName\":\"" + user.getUsername() + "\"," +
                "\"roleIds\":\"" + user.getRoleids() + "\"," +
                "\"shdEnable\":" + user.getShdEnable() + "," +
                "\"status\":" + user.getStates() + "}";
    }

    /**
     * 参考{@link HttpClient#sendPost(String, String, String, Map)}
     * 设置charset="UTF-8",根据token设置默认header:
     * "Content-Type", "application/json;charset=UTF-8"
     * "Authorization", this.authorization
     *
     * @param url         request地址
     * @param postContent request内容
     * @return 请求结果;如果发生IO异常返回null并记录日志,如果没有登陆(this.token==null)直接返回null
     * @see HttpClient#sendPost(String, String, String, Map)
     */
    public String getResult(String url, String postContent) {
        if (this.token == null) {
            return null;
        }
        Map<String, String> headers = new HashMap<>(2);
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", this.authorization);
        try {
            return sendPost(url, postContent, "UTF-8", headers);
        } catch (IOException e) {
            LOGGER.error("AomClient.getResult():" + e);
            return null;
        }
    }

    public String getViewData() {
        String url = "http://192.168.180.160:16088/api/rpcServer/afa4j/3/custom/C12002";
//        String postContent = "{\"viewId\":540,\"currentPage\":315,\"pageSize\":10,\"sidx\":\"ID\",\"order\":\"desc\",\"searchList\":[]}";
        String postContent = "{\"viewId\":701,\"currentPage\":1,\"pageSize\":10,\"sidx\":\"ID\",\"order\":\"desc\",\"searchList\":[]}";
        return this.getResult(url, postContent);
    }

    /**
     * 利用AOM中ADMIN重置密码功能中的修改员工信息功能，授权用户角色(将roleid写进user中)
     * <br>注意:
     * <br>1没有检验roleid是否合法
     * <br>2.如果参数user中信息不足，则数据库表的相应属性会覆盖成空。覆盖user属性参考 {@link AomClinet#parseUser(User)}
     *
     * @param user   要授权的用户。需要包含必要的用户信息
     * @param roleid 要授权的角色ID
     * @return 授权结果, true代表成功
     * @see AomClinet#resetUser(String)
     */
    public boolean authRole(User user, int roleid) {
        if (user == null) {
            LOGGER.warn("AomClient.authRole():user is null");
            return false;
        }
        user.setRoleids(user.getRoleids() + "," + roleid);
        return this.resetUser(parseUser(user));
    }
    /**
     * 从AOM查询合同动态表保存数据到ngoss.t_snap_contractsync表
     * 时间条件取当前时间戳
     * @param contype 合同类型(固定金额 框架协议)
     * @see HibernateUtil#saveOrUpdateObject(Object)
     * @see TSnapContractsyncEntity
     */
    public void saveContractFromAOM(String contype) {
        String url, postcontent, result;
        int len;
        long nowtime = new Timestamp(System.currentTimeMillis()).getTime();
        refreshContractSync();
        url = "http://192.168.180.160:16088/api/rpcServer/afa4j/3/ReportManagement/PUB01028";
        postcontent = "{\"pageSize\":1,\"currentPage\":1,\"S_CONTYPE\":\"" + contype + "\",\"DT_YEARR\":" + nowtime + "}";
        len = com.alibaba.fastjson.JSONObject.parseObject(getResult(url, postcontent)).getJSONObject("data").getJSONObject("contractList").getIntValue("totalPage");
        postcontent = "{\"pageSize\":" + len + ",\"currentPage\":1,\"S_CONTYPE\":\"" + contype + "\",\"DT_YEARR\":" + nowtime + "}";
        result = getResult(url, postcontent);
//        json格式转化
        JSONArray arr = com.alibaba.fastjson.JSONObject.parseObject(result).getJSONObject("data").getJSONObject("contractList").getJSONArray("record");
        for (int i = 0; i < len; i++) {
            com.alibaba.fastjson.JSONObject bobj = arr.getJSONObject(i);
            TSnapContractsyncEntity entity = bobj.toJavaObject(TSnapContractsyncEntity.class);
            entity.setId("" + entity.getsConcode() + entity.getsStacode());
            HibernateUtil.saveOrUpdateObject(entity);
        }
    }

    /**
     * 重置按钮刷新合同动态表数据
     */
    private void refreshContractSync() {
        getResult("http://192.168.180.160:16088/api/rpcServer/afa4j/3/ReportManagement/PUB01036", "{\"isReset\":\"reset\"}");
    }
    public Token getToken() {
        return this.token;
    }

    public String getUsercode() {
        return this.usercode;
    }

}
