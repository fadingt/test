package org.vaalbara.dao;
import org.apache.ibatis.annotations.Param;
import org.vaalbara.bean.Repair;
import org.vaalbara.bean.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lizq on 2018/3/8.
 */
@Repository
public interface IStuDao {

    /**
     * 查询所有学生
     * @return
     */
    int getStudentsByEmail(String sEmail);

    /**
     * 根据id查询学生信息
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
    List<Student> selectStudent(@Param("sName")String sName);
}
