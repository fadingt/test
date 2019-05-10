package service.impl;

import java.util.List;

import javax.annotation.Resource;

import dao.UserDao;
import domain.User;
import service.UserManageService;

public class UserManageServiceImpl implements UserManageService {
	@Resource
	private UserDao userDao;

	@Override
	public void addUser(User user) {
		userDao.addUser(user);
	}

	@Override
	public User getUser(int userid) {
		return userDao.getUserById(userid);
	}
/*
 * 通过username查询user
 * 若没有user则返回NOUSER
 * 若返回的user的密码与传来的密码一致则返回OK
 * 否则不一致返回FAIL
 * (non-Javadoc)
 * @see service.UserManageService#login(java.lang.String, java.lang.String)
 */
	@Override
	public String login(String username, String password) {
		List<User> users = userDao.getUserByName(username);
		if (users.size() == 0) {
			return "NOUSER";
		}
		for (User user : users) {
//			no password in database
			if(user.getPassword()==null){
				return "OK";
			}
			if (user.getPassword().equals(utils.MD5.getMD5(password).toUpperCase())) {
				return "OK";
			}
		}
		return "FAIL";
	}

	@Override
	public int regist(User user) {
		if (user == null) {
			return -2;
		} else if (user.getUserid() > 0 && userDao.getUserById(user.getUserid()) == null) {
			userDao.addUser(user);
			return 1;
		}
		return -1;
	}

	@Override
	public List<User> SearchUser(String sql, String[] criteria) {
		return userDao.getUser(sql, criteria);
	}
	
}
