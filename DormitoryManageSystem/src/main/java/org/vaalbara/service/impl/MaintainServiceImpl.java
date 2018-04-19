package org.vaalbara.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaalbara.bean.Maintain;
import org.vaalbara.dao.IMaintainDao;
import org.vaalbara.service.IMaintainService;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */
@Service
public class MaintainServiceImpl implements IMaintainService {

    //声明maintain对象
    @Autowired //默认根据类型自动装配的
    private IMaintainDao iMaintainDao;

    /**
     * 查询所有维修工
     * @return
     */
    public List<Maintain> getMaintainAll() {
        return iMaintainDao.getMaintainAll();
    }

    /**
     * 根据id查询维修工
     * @return
     */
    public Maintain getMaintainById(Integer mId){
        return iMaintainDao.getMaintainById(mId);
    };
    @Override
    public List<Maintain> getMaintain() {
        return iMaintainDao.getMaintain();
    }

    @Override
    public int addMaintain(Maintain maintain) {
        return iMaintainDao.addMaintain(maintain);
    }

    @Override
    public int updateMaintain(Maintain maintain) {
        return iMaintainDao.updateMaintain(maintain);
    }

    @Override
    public int deleteMaintain(int mId) {
        return iMaintainDao.deleteMaintain(mId);
    }

    @Override
    public List<Maintain> selectMaintain(String mName) {
        return iMaintainDao.selectMaintain(mName);
    }
}
