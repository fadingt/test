package cn.com.agree.servlet;

import cn.com.agree.config.LDAPConfig;
import cn.com.agree.domain.User;
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

import static cn.com.agree.utils.JDBCUtils.getUserListBYUsercode;

@WebServlet(name = "LdapUserManageServlet", urlPatterns = "/JavaTest_war_exploded/updateLdapUser")
public class LdapUserManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        request.getQueryString();
        LDAPUtils ldap = new LDAPUtils();
        File parent = new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource");
//        String child = "ldap.properties";
        String child = "ldap_produce.properties";
        LDAPConfig config = new LDAPConfig(new File(parent,child));
        ldap.connect(config);
        System.out.println(request.getQueryString());
        List<String> usercodeList = new ArrayList<String>();
        usercodeList.add("A6853");
        List<User> userlist = null;
        try {
            userlist = getUserListBYUsercode(usercodeList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ldap.updateUsers(ldap, userlist);
        ldap.closeContext();
        request.getRequestDispatcher("/result.jsp").forward(request,response);
    }
}
