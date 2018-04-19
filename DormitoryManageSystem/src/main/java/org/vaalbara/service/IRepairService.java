package org.vaalbara.service;

import org.vaalbara.bean.ManyIds;
import org.vaalbara.bean.Repair;
import org.vaalbara.bean.dao.VocationDao;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */
public interface IRepairService {
    int addRepair(Repair repair);
    List<VocationDao> getAllRepair();
//    public int deleteRepair(VocationDao voDao);

    /**
     * 根据学生ID查找报修原因
     * @param rId
     * @return
     */
    String getRepair(Integer rId);

    /**
     * 根据rid修改维修属性
     * @param manyIds
     * @return
     */
    int updateRepStatus(ManyIds manyIds);

    /**
     * 随机查询数据
     * @param dId
     * @return
     */
    List<Repair> selectRepInfo(Integer dId);
}
