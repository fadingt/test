package action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;

import domain.User;
import service.IUserService;

@Controller
public class LoginAction {
	@Resource(name="userServiceImpl")private IUserService userService;
	@RequestMapping("/login.do")
	public String login(@ModelAttribute User user){
		if(user.getPassword() == null){
			System.out.println("no password");
			return "login";
		}
		user.setPassword(utils.MD5.getMD5(user.getPassword()));
		int loginCode = userService.loginCheck(user);
		if(loginCode == 0){
			return "loginok";
		}
		return "login";
	}
	@RequestMapping("/userform.do")
	public String userform(){
		return "saveUser";
	}
}
