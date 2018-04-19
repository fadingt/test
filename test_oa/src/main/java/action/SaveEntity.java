package action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import action.validate.UserValidator;
import domain.User;
import service.IUserService;

@Controller
public class SaveEntity {
	@Resource(name = "userServiceImpl")
	private IUserService userService;
	@InitBinder
	public void initBinder(DataBinder binder){
		binder.setValidator(new UserValidator());
	}

	@RequestMapping("/saveUser.do")
	@ResponseBody
	public ModelAndView saveUser(@Validated User user, BindingResult result) {
		ModelAndView mav = new ModelAndView("saveUser");
		// not validated
		if (result.hasErrors()) {
			System.out.println("validate");
			System.out.println(result.getErrorCount());
//			System.out.println(ValidationUtils.invokeValidator(UserValidator));
//			java.beans.XMLDecoder
			return new ModelAndView("index");
		}

		// TODO encrypt password with MD5
		// if (user.getPassword() != null) {
		// user.setPassword(utils.MD5.getMD5(user.getPassword()));
		// mav.addObject("emptyPassword", "emptyPassword");
		// }
		if (user != null) {
			userService.saveUser(user);
		}
		return mav;
	}
}
