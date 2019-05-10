package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.UserDao;
import dao.impl.HibernateUserDaoImpl;
import domain.User;

@WebServlet(urlPatterns = "/getUserServlet")
public class GetUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	BeanFactory beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
	private UserDao userDao = (HibernateUserDaoImpl) beanFactory.getBean("hibernateUserDaoImpl");
//	@Resource private UserDao userDao;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		int userid = Integer.valueOf(request.getParameter("userid"));
		System.out.println(userid);
		User user = userDao.getUserById(userid);
		request.setAttribute("user", user);
		request.getRequestDispatcher("/getUser.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
