package org.vaalbara.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.vaalbara.advice.SystemControllerLog;
import org.vaalbara.bean.Repair;
import org.vaalbara.bean.Student;
import org.vaalbara.service.IStudentService;
import org.vaalbara.utils.MD5PassWord;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by lizq on 2018/3/8.
 */
@RestController
//可以写@RestController,下面不需要写@ResponseBody.相当于@ResponseBody和@Controller结合

@RequestMapping("students")
public class StudentController {

    @Autowired     //自动注入studentService
    IStudentService studentService;

    @RequestMapping(value = "",method = RequestMethod.GET,produces="text/html;charset=UTF-8;")
    @SystemControllerLog(description = "学生登陆")
    public String studentLogin(Student student,HttpSession httpSession){
        Gson gson = new Gson();
        List<Student> list = studentService.studentLogin(student);
        int i = 0;
        if (list.size()>0){
            for (Student s:list) {
                httpSession.setAttribute("student",s);
                if(s.getsName() == null || s.getsName() == "" || s.getsPhone() == null || s.getsPhone() == "" ||s.getsClass() == null || s.getsClass() == "" || s.getpId() == null){
                   i = 2;
                }else{
                    i = 1;
                }
            }
        }
        return gson.toJson(i);
    }

    @RequestMapping(value = "reg",method = RequestMethod.GET)
    @SystemControllerLog(description = "学生验证邮箱是否存在")
    public int getStudentsByEmail(String sEmail){
        int i = studentService.getStudentsByEmail(sEmail);
        System.out.println(i);
        return i;
    }

    @RequestMapping(value = "add",method = RequestMethod.GET)
    @SystemControllerLog(description = "学生注册")
    public int addStudent(Student student,HttpSession httpSession){
        httpSession.setAttribute("student",student);
        int i = studentService.addStudent(student);
        return i;
    }

    @RequestMapping(value = "getStudentByHttpSession",method = RequestMethod.GET,produces="text/html;charset=UTF-8;")
    @SystemControllerLog(description = "个人信息获取")
    public String studentLogin(HttpSession httpSession){
        Gson gson = new Gson();
        Student student = (Student)httpSession.getAttribute("student");
        String str = gson.toJson(student);
        System.out.println(str);
        return str;
    }


    @RequestMapping(value = "updateStudent",method = RequestMethod.GET)
    @SystemControllerLog(description = "学生更改个人信息")
    public int updateStudent(Student student,HttpSession httpSession){
        httpSession.setAttribute("student",student);
        int i = studentService.updateStudent(student);
        System.out.println(i);
        return i;
    }
    @RequestMapping(value = "getStudentRepair",method = RequestMethod.GET,produces="text/html;charset=UTF-8;")
    @SystemControllerLog(description = "学生获取学生订单信息")
    public String getStudentRepair(HttpSession httpSession){
        Gson gson = new Gson();
        Student student = (Student)httpSession.getAttribute("student");
        List<Repair> repairs = studentService.getStudentRepair(student.getsId());
        System.out.println(repairs.toString());
        String str = gson.toJson(repairs);
        System.out.println(str);
        return str;
    }

    @RequestMapping(value = "updateStudentRepair",method = RequestMethod.GET)
    @SystemControllerLog(description = "学生评价订单")
    public int  updateStudentRepair(Repair repair){
        System.out.println(repair.toString());
        int i = studentService.updateStudentRepair(repair);
        System.out.println(i);
        return i;
    }

    @RequestMapping(value = "updateStuPass",method = RequestMethod.GET)
    @SystemControllerLog(description = "学生更改个人密码")
    public int  updateStuPass(String oldPass,String newPass,HttpSession httpSession){
        int i = 4;
        System.out.println(oldPass+"   "+newPass);
        Student student = (Student)httpSession.getAttribute("student");
        Student student1 = new Student();
        Student student4 = new Student();
        student1.setsPass(oldPass);
        Student student2 = MD5PassWord.MD5Pass(student1);
        student4.setsPass(newPass);
        Student student3 = MD5PassWord.MD5Pass(student4);
        System.out.println(student2.getsPass() + "    "+student3.getsPass());
        System.out.println(student.getsPass());
        if(!student2.getsPass().equals(student.getsPass())){
            i = 2;
        }else if(student3.getsPass().equals(student.getsPass())){
            i = 0;
        }else{
            student.setsPass(student3.getsPass());
            i = studentService.updateStuPass(student);
        }
        System.out.println(i);
        httpSession.removeAttribute("student");
        return i;
    }
    @RequestMapping(value = "StudentLogout",method = RequestMethod.GET)
    @SystemControllerLog(description = "学生退出登录")
    public int logout(@ModelAttribute("student")Student student, HttpSession httpSession){
        httpSession.removeAttribute("student");
        return 1;
   }
    @RequestMapping(value = "all",method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "管理员查询所有学生信息")
    public List<Student> getStudents(){
        System.out.println("我进去了学生查询全部");
        List<Student> students = studentService.getStudents();
        System.out.println(students);
        return students;
    }
    @RequestMapping(value = "gai",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "管理员修改学生信息")
    public int updateStudent(Student student){
        System.out.println("我进去了修改学生");
        int i = studentService.updateStudent1(student);
        System.out.println(i);
        return i;
    }
    @RequestMapping(value = "shana",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "管理员删除学生信息")
    public int deleteStudent(int sId){
        System.out.println("我进去了删除学生");
        int i = studentService.deleteStudent(sId);
        System.out.println(i);
        return i;
    }
    @RequestMapping(value = "cha",method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "管理员按条件学生信息")
    public List<Student> selectStudent(String sName){
        System.out.println(sName);
        System.out.println("我查询了学生");
        List<Student> students = studentService.selectStudent(sName);
        System.out.println(students);
        return students;
    }
}