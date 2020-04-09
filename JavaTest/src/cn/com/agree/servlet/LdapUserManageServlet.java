package cn.com.agree.servlet;

import cn.com.agree.config.LDAPConfig;
import cn.com.agree.dao.UserDao;
import cn.com.agree.dao.UserDaoImpl;
import cn.com.agree.domain.User;
import cn.com.agree.utils.JDBCUtils;
import cn.com.agree.utils.LDAPUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "LdapUserManageServlet", urlPatterns = "/updateLdapUser")
public class LdapUserManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LDAPUtils ldap = new LDAPUtils();
        UserDao userDao = new UserDaoImpl();
        File parent = new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource");
//        String child = "ldap.properties";
        String child = "ldap_produce.properties";
        LDAPConfig config = new LDAPConfig(new File(parent, child));
        ldap.connect(config);
        String userCode;
        List<String> userCodeList = new ArrayList<>();
        if (request.getQueryString() != null && request.getQueryString().contains("=")) {
            userCode=request.getQueryString().split("=")[1];
            System.out.println(userCode);
            if(userCode!=null){
                userCodeList.add(userCode);
            }
        } else {
            System.out.println("request is null");
        }
        List<User> userList;
        try {
            userList = userDao.getUserListBYUsercode(userCodeList);
            System.out.println(userList.get(0).getUsername());
            ldap.updateUsers(ldap, userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.destroy();
            ldap.closeContext();
        }

        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }
}
