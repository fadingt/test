package org.vaalbara.service;

import org.vaalbara.bean.Repair;
import org.vaalbara.bean.Student;

import java.util.List;

/**
 * Created by ZhangW on 2018/3/9.
 */
public interface IStudentService {
    int getStudentsByEmail(String sEmail);

    /**
     * 学生进行登录
     * @param student
     * @return
     */
    List<Student> studentLogin(Student student);
    /**
     * 增加学生信息
     * @param student
     * @return
     */
    int addStudent(Student student);

    /**
     * 学生更改自己的个人信息
     * @param student
     * @return
     */
    int updateStudent(Student student);
    List<Repair> getStudentRepair(int sId);
    int updateStuPass(Student student);

    int updateStudentRepair(Repair repair);
    List<Student> getStudents();
    int updateStudent1(Student student);
    int deleteStudent(int sId);
    List<Student> selectStudent(String sName);

}
