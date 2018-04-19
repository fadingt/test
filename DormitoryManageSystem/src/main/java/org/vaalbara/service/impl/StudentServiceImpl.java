package org.vaalbara.service.impl;

import org.vaalbara.bean.Repair;
import org.vaalbara.bean.Student;
import org.vaalbara.dao.IStuDao;
import org.vaalbara.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaalbara.utils.MD5PassWord;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by lizq on 2018/3/8.
 */
@Service //告诉spring 这是一个service  同时把这个类注册到spring
public class StudentServiceImpl implements IStudentService {

    @Autowired     //让spring 注入一个dao  autowired 会根据类型去匹配对应的对象
    private IStuDao stuDao;

    @Override
    public int getStudentsByEmail(String sEmail) {
        return stuDao.getStudentsByEmail(sEmail);
    }

    @Override
    public List<Student> studentLogin(Student student) {
        Student stu = MD5PassWord.MD5Pass(student);
        return stuDao.studentLogin(stu);
    }

    @Override
    public int addStudent(Student student) {
        Student stu = MD5PassWord.MD5Pass(student);
        return stuDao.addStudent(stu);
    }

    @Override
    public int updateStudent(Student student) {
        return stuDao.updateStudent(student);
    }
    @Override
    public List<Repair> getStudentRepair(int sId) {
        return stuDao.getStudentRepair(sId);
    }

    @Override
    public int updateStudentRepair(Repair repair) {
        return stuDao.updateStudentRepair(repair);
    }

    @Override
    public int updateStuPass(Student student) {
        return stuDao.updateStuPass(student);
    }
    public List<Student> getStudents() {
        return stuDao.getStudents();
    }

    public int updateStudent1(Student student) {
        return stuDao.updateStudent1(student);
    }

    public int deleteStudent(int sId) {
        return stuDao.deleteStudent(sId);
    }

    public List<Student> selectStudent(String sName) {
        return stuDao.selectStudent(sName);
    }

}