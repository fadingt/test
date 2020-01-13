package cn.com.agree.javatest;

//import javax.persistence.Entity;
//import javax.persistence.Table;
import java.util.Date;

/**
 * Ŀ¼����������Ϣ
 * dq
 */
//@Entity
//@Table(name = "bo_ldap_info")//�û���
public class LDAPConnectionInfo
//        extends  IdEntity
{

    private static final long serialVersionUID = 7300555212960602097L;

    public static final Integer ENABLE_STATUS = 1;//1:��ʾ����״̬

    public static final Integer DISABLE_STATUS = 0;// 0����ʾͣ��״̬

    public static final Integer LDAP_VERSION_3 = 3;// 3����ʾldapЭ��汾Ϊ3.0

    public static final Integer LDAP_VERSION_2 = 2;// 2:��ʾldapЭ��汾Ϊ2.0

    public static final Integer LDAP_PORT_389 = 389; //389:Ĭ�϶˿�

    private Integer userId; //�û���ID

    private String serverName;  //��������

    private String ip;  //ip

    private Integer port = LDAP_PORT_389;   //�˿�

    private Integer version = LDAP_VERSION_3;   //Э��汾

    private String baseDN;  //���ڵ�

    private Integer isAnonymousBind = DISABLE_STATUS; //�Ƿ���������

    private String userDN;  //�û�DN

    private Integer isAppendBaseDN = DISABLE_STATUS; //�Ƿ�׷�Ӹ��ڵ�

    private String password;    //����

    private Date optTime = new Date();//����ʱ��

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