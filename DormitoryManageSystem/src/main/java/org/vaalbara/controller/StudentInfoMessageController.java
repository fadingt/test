package org.vaalbara.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.vaalbara.advice.SystemControllerLog;
import org.vaalbara.bean.Student;
import org.vaalbara.bean.StudentInfoMessage;
import org.vaalbara.service.IStudentInfoMessageService;

import javax.servlet.http.HttpSession;

/**
 * Created by Huawei on 2018/3/16.
 */
@Controller
@RequestMapping("studentinfomessage")
public class StudentInfoMessageController {

    @Autowired
    IStudentInfoMessageService iStudentInfoMessageService;


    @RequestMapping(value = "all",method = RequestMethod.GET,produces="text/html;charset=UTF-8;")
    @ResponseBody
    @SystemControllerLog(description = "前台获取登录学生信息")
    public String  getAll(HttpSession httpSession){
       // Student student = (Student)httpSession.getAttribute("student");
        Student student = (Student)httpSession.getAttribute("student");
        int sId = student.getsId();
        System.out.println("************************************"+sId);
        StudentInfoMessage sim = iStudentInfoMessageService.getAllMessage(sId);
        sim.setvCount(iStudentInfoMessageService.getVocationCount(sId));
        sim.setrCount(iStudentInfoMessageService.getRepairCount(sId));
        sim.setcCount(iStudentInfoMessageService.getCriticizeCount(sId));


        if(sim.getsClass().contains("四")||sim.getsClass().contains("4")){
            sim.setForYear(4);
        }else if(sim.getsClass().contains("三")||sim.getsClass().contains("3")){
            sim.setForYear(3);
        }else if(sim.getsClass().contains("二")||sim.getsClass().contains("2")){
            sim.setForYear(2);
        }else if(sim.getsClass().contains("一")||sim.getsClass().contains("1")){
            sim.setForYear(1);
        }else {
            sim.setForYear(0);
        }
        Gson gson = new Gson();
        String str = gson.toJson(sim);
        System.out.println(str);
        return str;
    }
}
