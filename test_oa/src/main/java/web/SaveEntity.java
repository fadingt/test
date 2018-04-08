package web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.User;
import service.IUserService;
@Controller
public class SaveEntity {
	@Resource(name="userServiceImpl")private IUserService userService;
	@RequestMapping("/saveUser.do")
	public ModelAndView saveUser(@ModelAttribute User user){
		ModelAndView mav = new ModelAndView("saveUser");
		if(user == null){
			mav.addObject("no user", "请输入");
			return mav;
		}
//		encrypt password with MD5
		if(user.getPassword() != null){
			user.setPassword(utils.MD5.getMD5(user.getPassword()));
			mav.addObject("emptyPassword", "emptyPassword");
		}
		userService.saveUser(user);
		return mav;
	}
}
