package action;

import dao.UserDao;
import dao.impl.HibernateUserDaoImpl;
import domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
@Controller
public class userinfoAction {
    @RequestMapping("/userinfo.do")
    public ModelAndView userinfo() {
        ModelAndView mav = new ModelAndView("userinfo");
        UserDao userDao = new HibernateUserDaoImpl();
        List<User> users = new ArrayList<>();
        int id =0;
        User user;
        while(id<100){
            user=userDao.getUserById(id);
            id++;
            if(user!=null){
                users.add(user);
            }
        }
        System.out.println("users:"+users.size());
        mav.addObject("users",users);
        return mav;
    }
}
