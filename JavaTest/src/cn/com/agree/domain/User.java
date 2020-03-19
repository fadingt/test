package cn.com.agree.domain;

public class User {
    private int userid;
    private String username;
    private String usercode;
    private String password;
    private String orgcode;
    private String orgname;

    @Override
    public String toString() {
        return "UserDO{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", usercode='" + usercode + '\'' +
                ", password='" + password + '\'' +
                ", orgcode='" + orgcode + '\'' +
                ", orgname='" + orgname + '\'' +
                '}';
    }

    public User() {
        this.password = "e10adc3949ba59abbe56e057f20f883e";//默认密码： 123456
    }

    public User(String username, String usercode, String password, String orgcode, String orgname) {
        this.username = username;
        this.usercode = usercode;
        this.password = password;
        this.orgcode = orgcode;
        this.orgname = orgname;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }
}
