package org.vaalbara.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaalbara.bean.DroStudent;
import org.vaalbara.dao.IStuInfo;
import org.vaalbara.service.IStuInfoService;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
@Service//说明这个类是一个service，并且把这个service注册到spring
public class StuInfoServiceImpl implements IStuInfoService{

    //声明dromitory对象
    @Autowired //默认根据类型自动装配的
    IStuInfo iStuInfo;
    /**
     * 获取宿舍学生住宿情况
     * @return
     */
    public List<DroStudent> getStuInfo(DroStudent droStudent) {
        return iStuInfo.getStuInfo(droStudent);
    }

    /**
     * 修改宿舍入住情况
     * @param droStudent
     * @return
     */
    public int updateDroStuInfo(DroStudent droStudent) {
        return iStuInfo.updateDroStuInfo(droStudent);
    }

    /**
     * 判断学生是否入住 or 学生所在宿舍
     * @param sId
     * @return
     */
    public String existStu(Integer sId) {
        return iStuInfo.existStu(sId);
    }

    /**
     *删除学生入住
     * @param droStudent
     * @return
     */
    public int deleteDroStuInfo(DroStudent droStudent) {
        return iStuInfo.deleteDroStuInfo(droStudent);
    }
}
