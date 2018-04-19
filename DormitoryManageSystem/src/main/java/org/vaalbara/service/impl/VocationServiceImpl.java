package org.vaalbara.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaalbara.bean.Student;
import org.vaalbara.bean.Vocation;
import org.vaalbara.bean.dao.VocationDao;
import org.vaalbara.dao.IVocationDao;
import org.vaalbara.service.IVocationService;
import org.vaalbara.utils.JavaMailLearn;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */
@Service
public class VocationServiceImpl implements IVocationService{

   @Autowired    //让spring 注入一个dao  autowired 会根据类型去匹配对应的对象
    private IVocationDao vocationDao;
    public int  addVocation(Vocation vocation) {
        return vocationDao.addVocation(vocation);
    }
    public List<VocationDao> getAllVocation(){
        return vocationDao.getAllVocation();
    }
    public Student getVocationById(VocationDao voDao){
//        String msg=s.getsName()+":你好，你在"+s.getVocation().getvBegin()+"至"+s.getVocation().getvBegin()+"申请的请假要求未通过管理员审核";
//        try {
//            JavaMailLearn.ready(s.getsEmail(),msg);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("邮件发送异常");
//        }
        return vocationDao.getVocationById(voDao);
    }
    public int updateVocation( VocationDao voDao){
        return vocationDao.updateVocation(voDao);
    }
    public int deleteVocation(VocationDao voDao){
        Student s=vocationDao.getVocationById(voDao);
        System.out.println(s.getsEmail());
        String msg=s.getsName()+":你好，你在"+s.getVocation().getvBegin()+"至"+s.getVocation().getvBegin()+"申请的请假要求未通过管理员审核";
        System.out.println(msg);
        try {
            JavaMailLearn.ready(s.getsEmail(),msg);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("邮件发送异常");
        }
        return vocationDao.deleteVocation(voDao);

    }
}
