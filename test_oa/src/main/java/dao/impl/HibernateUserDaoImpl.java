package dao.impl;

import java.util.List;
import java.util.Map;

import dao.UserDao;
import domain.User;
import utils.HibernateUtil;

public class HibernateUserDaoImpl implements UserDao{

	@Override
	public void updateUser(User user) {
		HibernateUtil.updateObject(user);
	}

	@Override
	public void deleteUser(User user) {
		HibernateUtil.deleteObject(user);
	}

	@Override
	public void addUser(User user) {
		HibernateUtil.addObject(user);
	}

	@Override
	public List<User> getUserByProperty(Map<String, Object> userProperty) {
		return HibernateUtil.getObjectByProperty(userProperty, User.class);
	}

	@Override
	public User getUserById(int userid) {
		return HibernateUtil.getObjectById(User.class, userid);
	}

	@Override
	public Long getCount() {
		return HibernateUtil.getTableCount(User.class);
	}

}
