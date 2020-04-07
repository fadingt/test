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

@WebServlet(name = "LdapUserManageServlet", urlPatterns = "/updateLdapUser")
public class LdapUserManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LDAPUtils ldap = new LDAPUtils();
        File parent = new File("D:\\9zliuxingyu@gmail.com\\test\\JavaTest\\resource");
//        String child = "ldap.properties";
        String child = "ldap_produce.properties";
        LDAPConfig config = new LDAPConfig(new File(parent,child));
        ldap.connect(config);
        if(request.getQueryString() != null) {
            System.out.println(request.getQueryString());
            Object object = request.getAttribute("usercode");
            System.out.println(object);
        }else{
            System.out.println("request is null");
        }
//        List<String> usercodeList = new ArrayList<String>();
//        usercodeList.add("A6853");
//        List<User> userlist = null;
//        try {
//            userlist = getUserListBYUsercode(usercodeList);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        ldap.updateUsers(ldap, userlist);
        User user = new User();
        user.setOrgcode("A6853");
        ldap.updateUser(ldap,user);
        ldap.closeContext();
        request.getRequestDispatcher("/result.jsp").forward(request,response);
    }
}
