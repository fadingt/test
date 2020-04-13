package cn.com.agree.servlet;

import cn.com.agree.config.LDAPConfig;
import cn.com.agree.dao.UserDao;
import cn.com.agree.dao.UserDaoImpl;
import cn.com.agree.domain.User;
import cn.com.agree.utils.EncryptUtils;
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

import static cn.com.agree.utils.EncryptUtils.toBytes;


@WebServlet(urlPatterns = "/checkLdapUser")
public class checkLdapUserServlet extends HttpServlet {
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
        StringBuilder result = new StringBuilder();
        String userCode = null;
        String password = null;
        String querystr;
        if ((querystr = request.getQueryString()) != null) {
            for (String condition : querystr.split("&")) {
                if (condition.split("=")[0] == null) {
                    System.out.println("condition null");
                } else if (condition.split("=")[0].equals("usercode")) {
                    userCode = condition.split("=")[1];
                } else if (condition.split("=")[0].equals("password")) {
                    password = condition.split("=")[1];
                }
            }
        }
        if (userCode != null) {
            User user = userDao.getUserBYUsercode(userCode);
            if (password == null) {
                request.setAttribute("MD5_PWD", user.getPassword());
//            todo: extract method in EncryptUtils
//            pwd1:数据库密码BASE64加密后 pwd2:ldap用户密码 同步后应一致
                String pwd1 = "{MD5}".concat(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(toBytes(user.getPassword())));
                String pwd2 = "";
            } else if (password != null) {
                String md5pwd = EncryptUtils.getMD5(password);
                String md5base64pwd = EncryptUtils.EncoderByMd5(password);
                if(md5pwd.equals(user.getPassword())){
                    result.append("是否可登陆AOM:输入密码与AOM数据库密码一致<br>");
                }else{
                    result.append("是否可登陆AOM:输入密码与AOM数据库密码不一致<br>");
                }
            }
        } else {
            result.append("error:search usercode is null;");
        }

        request.setAttribute("result", result.toString());
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
