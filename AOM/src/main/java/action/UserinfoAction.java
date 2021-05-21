package action;

import dao.UserDao;
import net.sf.json.JSONArray;
import dao.impl.HibernateUserDaoImpl;
import domain.paasaom.User;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fadingt
 */
@Controller
public class UserinfoAction {
    @RequestMapping(value = "/userinfo2.do")
    public ModelAndView userinfo() {
        ModelAndView mav = new ModelAndView("userinfo.jsp");
        UserDao userDao = new HibernateUserDaoImpl();
        List<User> users;
        Map<String, Object> property = new HashMap<>();
        property.put("status", 1);
        userDao.countUser(property);
        users = userDao.listUserByProperty(property);
        System.out.println("users:" + users.size());
        mav.addObject("users", users);
        return mav;
    }

    /**
     * 返回非禁用状态的用户
     *
     * @param from 分页功能从第几个开始，默认是0
     * @param len  分页功能要查询多少个，默认是10
     * @return 用户数组字符串。由{@link JSONArray#add(Object)}将userList包装转化成字符串
     */
    @ResponseBody
    @RequestMapping(value = "/userinfo.do", produces = "application/JSON;charset=UTF-8")
    public String userinfo(@RequestParam(value = "from", required = false, defaultValue = "0") int from, @RequestParam(value = "len", required = false, defaultValue = "10") int len) {
        UserDao userDao = new HibernateUserDaoImpl();
        List<User> users;
        Map<String, Object> property = new HashMap<>();
        property.put("status", 1);
        userDao.countUser(property);
        users = userDao.listUserByProperty(property, from, len);
        JSONArray userArray = new JSONArray();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"org","staff"});
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        userArray.add(users,jsonConfig);
        return userArray.toString();
    }
}