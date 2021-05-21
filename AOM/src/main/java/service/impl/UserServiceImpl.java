package service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import dao.UserDao;
import domain.paasaom.User;
import service.IUserService;
import utils.AomClinet;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource(name = "hibernateUserDaoImpl")
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int loginCheck(User user) {
        Map<String, Object> userProperty = new HashMap<>();
        userProperty.put("usercode", user.getUsercode());
        List<User> list = userDao.listUserByProperty(userProperty);
        int listsize = list.size();
        if (listsize > 1) {
            // TODO define exception and status
            return OpStatus.OperateType.NOT_REPEAT.getNum();
        } else if (listsize < 1) {
            // TODO define exception and status
            System.out.println("NO USER WITH USERCODE");
            return OpStatus.OperateType.NOT_PROP.getNum();
        } else {
            User userreal = list.iterator().next();
            if (!userreal.getPassword().equals(user.getPassword())) {
                System.out.println("password do not match");
                return OpStatus.OperateType.NOT_MATCH.getNum();
            }
            return OpStatus.OperateType.SUCCESS.getNum();
        }
    }

    /**
     * unit test
     */
    public static void main(String[] args) {
        User user = new User();
        user.setUsercode("A0025");
        user.setPassword("6a8785951d58d7e56232e4e88bb195ab");
//        ApplicationContext cxt = new ClassPathXmlApplicationContext("conf/applicationServlet.xml");
        AbstractXmlApplicationContext cxt = new ClassPathXmlApplicationContext("conf/applicationServlet.xml");
        UserServiceImpl userService = (UserServiceImpl) cxt.getBean("userServiceImpl");
//		userService.saveUser(user);
        int result = userService.loginCheck(user);
        System.out.println(result);
        cxt.close();
    }

    @Override
    // TODO: 8/25/2020 use all user properties. instead of usercode only
    public int saveUser(User user) {
        Map<String, Object> property = new HashMap<>();
        property.put("usercode", user.getUsercode());
        if (userDao.listUserByProperty(property).size() > 0) {
            System.out.println("usercode:" + user.getUsercode() + " must be unique");
            return OpStatus.OperateType.NOT_REPEAT.getNum();
        }
        userDao.saveUser(user);
        return OpStatus.OperateType.SUCCESS.getNum();
    }

    @Override
    public boolean resetPassword(int userid, String newPwd) {
        return new AomClinet().resetPassword(userid,newPwd);
    }
}
