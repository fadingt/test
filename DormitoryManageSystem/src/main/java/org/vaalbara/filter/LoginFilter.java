package org.vaalbara.filter;

import org.vaalbara.bean.Admin;
import org.vaalbara.bean.Student;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		System.out.println("销毁过滤器");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		Student student = (Student)req.getSession().getAttribute("student");
		Admin admin = (Admin)req.getSession().getAttribute("admin");
		System.out.println(student);
		if(student != null || admin != null ){
			chain.doFilter(request, response);
		}else{
			resp.sendRedirect("../../..../../../../DormitoryManageSystem/login/index.html");
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("初始化过滤器");
		
	}

}
