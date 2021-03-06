package org.vaalbara.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vaalbara.advice.SystemControllerLog;
import org.vaalbara.bean.Admin;
import org.vaalbara.service.IAdminService;
import org.vaalbara.utils.MD5PassWord;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by Administrator on 2018/3/15.
 */
@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    IAdminService adminService;
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @SystemControllerLog(description = "管理员修改密码")
    public int updatePass(Admin admin,HttpSession httpSession){
        admin.setaId(((Admin)httpSession.getAttribute("admin")).getaId());
        Admin admin1 = MD5PassWord.MD5AdminPass(admin);
        return adminService.updatePass(admin1);
    }

    @RequestMapping(value = "",method = RequestMethod.GET,produces="text/html;charset=UTF-8;")
    @SystemControllerLog(description = "管理员登录")
    public String studentLogin(Admin admin, HttpSession httpSession){
        Gson gson = new Gson();
        Admin admin1 = MD5PassWord.MD5AdminPass(admin);
        List<Admin> list = adminService.adminLogin(admin1);
        int i = 0;
        if (list.size()>0){
            for (Admin a:list) {
                httpSession.setAttribute("admin",a);
            }
            i = 1;
        }
        return gson.toJson(i);
    }

}
