package domain.paasaom;


/**
 * @author fadingt
 */
public class User {
    private int userid;
    private Staff staff;
    private String usercode;
    private String username;
    private String password;
    private String mailbox;
    private String cellphone;
    private Integer sex;
    private int states;
    private String roleids;
    private Integer superiorLeader;
    private Integer isAdmin;
    private Integer shdEnable;
    private String orgcode;


    /**
     * 构造方法默认密码：123456
     * this.password = "e10adc3949ba59abbe56e057f20f883e"
     */
    public User() {
        this.password = "e10adc3949ba59abbe56e057f20f883e";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof User) {
            // TODO: 12/8/2020 判断所有属性全部相同
            return this.getUserid() == ((User) obj).getUserid() && this.getUsername().equals(((User) obj).getUsername());
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", usercode='" + usercode + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mailbox='" + mailbox + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", sex=" + sex +
                ", states=" + states +
                ", roleids='" + roleids + '\'' +
                ", superiorLeader=" + superiorLeader +
                ", isAdmin=" + isAdmin +
                ", shdEnable=" + shdEnable +
                '}';
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserid() {
        return userid;
    }


    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getRoleids() {
        return roleids;
    }

    public void setRoleids(String roleids) {
        this.roleids = roleids;
    }

    public Integer getSuperiorLeader() {
        return superiorLeader;
    }

    public void setSuperiorLeader(Integer superiorLeader) {
        this.superiorLeader = superiorLeader;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getShdEnable() {
        return shdEnable;
    }

    public void setShdEnable(Integer shdEnable) {
        this.shdEnable = shdEnable;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

}
