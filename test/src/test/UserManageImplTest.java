package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import domain.User;
import service.impl.UserManageServiceImpl;

public class UserManageImplTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testAddUser() {
	}

	@Test
	public void testGetUser() {
		AbstractApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserManageServiceImpl userManageImpl = (UserManageServiceImpl) cxt.getBean("userManageImpl");
		User user1 = userManageImpl.getUser(1);
		System.out.println(user1.toString());
		cxt.close();
	}

	@Test
	public void testLogin() {
		fail("Not yet implemented");
	}

}
