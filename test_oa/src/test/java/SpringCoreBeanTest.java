//
////import static org.junit.Assert.*;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.AbstractApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import domain.User;
//import service.IUserService;
//
//public class SpringCoreBeanTest {
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		System.out.println("setUpBeforeClass");
//	}
//
//	@Test
//	public void test() {
//		ApplicationContext ct = new ClassPathXmlApplicationContext("services.xml","daos.xml");
//		IUserService userService = (IUserService) ct.getBean("userService");
//		assert userService != null;
//		User user1 = new User();
//		user1.setPassword("111111");
//		user1.setUsercode("liuxingyu2");
////		userService.saveUser(user1);
//		int status = userService.loginCheck(user1);
////		if login success
//		assert status == 0;
//		((AbstractApplicationContext) ct).close();
//	}
//
//
//}
