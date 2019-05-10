package domain;

import java.math.BigDecimal;

public class User {
	private int userid;
	private String usercode;
	private String password;
	private String mailbox;
	private String cellphone;
	private BigDecimal state;
	private String username;
	private BigDecimal sex;
//	private String deptid;
	private String deptname;
	private Unit unit;

	@Override
	public String toString() {
		return "User [userid=" + userid + ", usercode=" + usercode + ", password=" + password + ", mailbox=" + mailbox
				+ ", cellphone=" + cellphone + ", state=" + state + ", username=" + username + ", sex=" + sex
				+ ", deptname=" + deptname + ", unit=" + unit + "]";
	}

	public User() {
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

	public BigDecimal getSex() {
		return sex;
	}

	public void setSex(BigDecimal sex) {
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

	public BigDecimal getState() {
		return state;
	}

	public void setState(BigDecimal state) {
		this.state = state;
	}

//	public String getDeptid() {
//		return deptid;
//	}
//
//	public void setDeptid(String deptid) {
//		this.deptid = deptid;
//	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
}
