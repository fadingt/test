package service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.HrpoolDao;
import dao.UserDao;
import domain.Hrpool;
import domain.User;

public class FindUnchangedPassword {
	@Resource
	private HrpoolDao hrpoolDao;
	@Resource
	private UserDao userDao;

	public boolean find() {
		User user = userDao.getUserById(2);
		Hrpool hrpool = hrpoolDao.getHrpoolById(user.getUserid());
		String origin_password = utils.MD5.getMD5(hrpool.getIdnumber()).toUpperCase();
		String password = user.getPassword();
		System.out.println("password:" + password + "\torigin:" + origin_password);
		if (origin_password.equals(password))
			return true;
		return false;
	}

	public static void main(String[] args) {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
		FindUnchangedPassword find = (FindUnchangedPassword) beanFactory.getBean("findUnchangedPassword");
		find.find();
		((AbstractApplicationContext) beanFactory).close();
	}

}
