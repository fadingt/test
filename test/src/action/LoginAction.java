package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionSupport;
import domain.User;
import service.UserManageService;

@SuppressWarnings("serial")

public class LoginAction extends ActionSupport {
	ApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
	private UserManageService um = (UserManageService) cxt.getBean("userManageServiceImpl");
	private User user;
	private String cookieStat;

//	@Override
//	public void validate() {
//		if (user.getUsername() == null || "".equals(user.getUsername().trim()))
//			this.addFieldError(user.getUsername(), "用户名不能为空");
//		if (user.getPassword() == null || "".equals(user.getPassword().trim())) {
//			this.addFieldError(user.getPassword(), "用户密码不能为空");
//		}
//	}

	public String execute() throws ServletException, IOException{
		HttpServletResponse resp = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		String result;
		Cookie loginfo = utils.ServletUtil.findCookie(req, "loginfo");
		if(null == cookieStat && null != loginfo){
			loginfo.setMaxAge(0);
			resp.addCookie(loginfo);
		}
		if (loginfo != null) {
			// login with cookie info
			String[] value = loginfo.getValue().split(":");
			result = um.login(value[0], value[1]);
			return result;
		} else {
			// login with form info
			result = um.login(user.getUsername(), user.getPassword());
			switch (result) {
			case "NOUSER":
				this.addFieldError(user.getUsername(), "无此用户");
				break;
			case "FAIL":
				this.addFieldError(user.getPassword(), "用户名密码不匹配");
				break;
			case "OK":
				if(null != cookieStat){
					Cookie cookie = new Cookie("loginfo", user.getUsername() + ":" + user.getPassword());
					cookie.setMaxAge(24 * 3600);
					resp.addCookie(cookie);
				}
			}
			return result;
		}

	}
	public void setCookieStat(String cookieStat) {
		this.cookieStat = cookieStat;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
