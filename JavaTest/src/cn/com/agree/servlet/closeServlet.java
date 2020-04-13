package cn.com.agree.servlet;

import cn.com.agree.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/close")
public class closeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        JDBCUtils.destroy();
    }
}
