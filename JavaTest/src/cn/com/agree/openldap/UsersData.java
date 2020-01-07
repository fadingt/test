package cn.com.agree.openldap;

public class UsersData {
	private String userName = null;
	private String userId = null;
	private String path = null;
	private String userMD5PWD = null;
	private String remark = null;
	public UsersData() {
		userName = "";
		userId = "";
		path = "";
		userMD5PWD = "";
		remark = "";
	}
	
	public static void parsePath() {//|0|20000|30833|30835|30843|
		
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUserMD5PWD() {
		return userMD5PWD;
	}

	public void setUserMD5PWD(String userMD5PWD) {
		this.userMD5PWD = userMD5PWD;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
