package service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import dao.UserDao;
import domain.User;
import service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Resource(name = "hibernateUserDaoImpl")
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public int loginCheck(User user) {
		Map<String, Object> userProperty = new HashMap<String, Object>();
		userProperty.put("usercode", user.getUsercode());
		List<User> list = userDao.getUserByProperty(userProperty);
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
			if (userreal.getPassword() != user.getPassword()) {
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
		user.setUsercode("A001231213");
		user.setPassword("3AFD6E38E6B725FE2466D5263B05039E");
		ApplicationContext cxt = new ClassPathXmlApplicationContext("dispatcherServlet-servlet.xml");
		UserServiceImpl userService = (UserServiceImpl) cxt.getBean("userServiceImpl");
		userService.saveUser(user);
		((AbstractXmlApplicationContext) cxt).close();
	}

	@Override
	public int saveUser(User user) {
		Map<String, Object> property = new HashMap<>();
		property.put("usercode", user.getUsercode());
		if (userDao.getUserByProperty(property).size() > 0) {
			System.out.println("usercode:" + user.getUsercode() + " must be unique");
			return OpStatus.OperateType.NOT_REPEAT.getNum();
		}
		userDao.addUser(user);
		return OpStatus.OperateType.SUCCESS.getNum();
	}

}
