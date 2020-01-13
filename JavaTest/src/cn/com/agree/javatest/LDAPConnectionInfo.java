package cn.com.agree.javatest;

//import javax.persistence.Entity;
//import javax.persistence.Table;
import java.util.Date;

/**
 * 目录服务连接信息
 * dq
 */
//@Entity
//@Table(name = "bo_ldap_info")//用户表
public class LDAPConnectionInfo
//        extends  IdEntity
{

    private static final long serialVersionUID = 7300555212960602097L;

    public static final Integer ENABLE_STATUS = 1;//1:表示启用状态

    public static final Integer DISABLE_STATUS = 0;// 0：表示停用状态

    public static final Integer LDAP_VERSION_3 = 3;// 3：表示ldap协议版本为3.0

    public static final Integer LDAP_VERSION_2 = 2;// 2:表示ldap协议版本为2.0

    public static final Integer LDAP_PORT_389 = 389; //389:默认端口

    private Integer userId; //用户的ID

    private String serverName;  //服务名称

    private String ip;  //ip

    private Integer port = LDAP_PORT_389;   //端口

    private Integer version = LDAP_VERSION_3;   //协议版本

    private String baseDN;  //根节点

    private Integer isAnonymousBind = DISABLE_STATUS; //是否匿名访问

    private String userDN;  //用户DN

    private Integer isAppendBaseDN = DISABLE_STATUS; //是否追加根节点

    private String password;    //密码

    private Date optTime = new Date();//操作时间

    @Override
    public String toString() {
        return "LDAPConectionInfo{" +
                "userId=" + userId +
                ", serverName='" + serverName + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", version=" + version +
                ", baseDN='" + baseDN + '\'' +
                ", isAnonymousBind=" + isAnonymousBind +
                ", userDN='" + userDN + '\'' +
                ", isAppendBaseDN=" + isAppendBaseDN +
                ", password='" + password + '\'' +
                ", optTime=" + optTime +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getBaseDN() {
        return baseDN;
    }

    public void setBaseDN(String baseDN) {
        this.baseDN = baseDN;
    }

    public Integer getIsAnonymousBind() {
        return isAnonymousBind;
    }

    public void setIsAnonymousBind(Integer isAnonymousBind) {
        this.isAnonymousBind = isAnonymousBind;
    }

    public String getUserDN() {
        return userDN;
    }

    public void setUserDN(String userDN) {
        this.userDN = userDN;
    }

    public Integer getIsAppendBaseDN() {
        return isAppendBaseDN;
    }

    public void setIsAppendBaseDN(Integer isAppendBaseDN) {
        this.isAppendBaseDN = isAppendBaseDN;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

/*    public LDAPConectionInfo(Integer userId, String serverName, String ip, Integer port, Integer version, String baseDN, Integer isAnonymousBind, String userDN, Integer isAppendBaseDN, String password, Date optTime) {
        this.userId = userId;
        this.serverName = serverName;
        this.ip = ip;
        this.port = port;
        this.version = version;
        this.baseDN = baseDN;
        this.isAnonymousBind = isAnonymousBind;
        this.userDN = userDN;
        this.isAppendBaseDN = isAppendBaseDN;
        this.password = password;
        this.optTime = optTime;
    }

    public LDAPConectionInfo() {
    }*/
}