package org.vaalbara.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaalbara.bean.ManyIds;
import org.vaalbara.bean.Repair;
import org.vaalbara.bean.Vocation;
import org.vaalbara.bean.dao.VocationDao;
import org.vaalbara.dao.IRepairDao;
import org.vaalbara.dao.IVocationDao;
import org.vaalbara.service.IRepairService;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */
@Service
public class RepairServiceImpl  implements IRepairService{
    @Autowired    //让spring 注入一个dao  autowired 会根据类型去匹配对应的对象
    private IRepairDao repairDao;
    public int  addRepair(Repair repair) {
        return repairDao.addRepair(repair);
    }
    public List<VocationDao> getAllRepair(){
        return repairDao.getAllRepair();
    }

    /**
     * 根据学生ID查找报修原因
     * @param rId
     * @return
     */
    public String getRepair(Integer rId) {
        return repairDao.getRepair(rId);
    }

    /**
     * 根据rid修改维修属性
     * @param manyIds
     * @return
     */
    public int updateRepStatus(ManyIds manyIds) {
        return repairDao.updateRepStatus(manyIds);
    }


    /**
     * 随机查询数据
     * @param dId
     * @return
     */
    public List<Repair> selectRepInfo(Integer dId) {
        return repairDao.selectRepInfo(dId);
    }

//    public int deleteRepair(VocationDao voDao){
//        return repairDao.deleteRepair(voDao);
//    }
}
