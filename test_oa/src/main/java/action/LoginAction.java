package action;

import javax.annotation.Resource;

import action.validate.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.User;
import service.IUserService;

import java.util.List;

@Controller
public class LoginAction {
    @Resource(name = "userService")
    private IUserService userService;

//    @InitBinder
//    public void initBinder(DataBinder dataBinder) {
//        dataBinder.setValidator(new UserValidator());
//    }

    @RequestMapping("/login.do")
    public String login(User user) {
//        List<ObjectError> errors = result.getAllErrors();
/*        if(result.hasErrors()){
            for(int i=0; i<errors.size(); i++){
//                System.out.println(errors.get(i));
                System.out.println("validate");
                System.out.println(errors.get(i).getObjectName());
            }
            return "error";
        }*/
        if (user.getPassword() == null) {
            System.out.println("no password");
            return "login";
        }
        user.setPassword(utils.MD5.getMD5(utils.MD5.getMD5(user.getPassword())));
        int loginCode = userService.loginCheck(user);
        if (loginCode == 0) {
            return "loginok";
        }
        return "login";
    }

    @RequestMapping("/userform.do")
    public String userform() {
        return "saveUser";
    }
}
