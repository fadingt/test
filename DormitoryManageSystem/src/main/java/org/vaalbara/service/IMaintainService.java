package org.vaalbara.service;

import org.vaalbara.bean.Maintain;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */
public interface IMaintainService {

    /**
     * 查询所有维修工
     * @return
     */
    public List<Maintain> getMaintainAll();

    /**
     * 根据id查询维修工
     * @return
     */
    public Maintain getMaintainById(Integer mId);
    List<Maintain> getMaintain();
    int addMaintain(Maintain maintain);
    int updateMaintain(Maintain maintain);
    int deleteMaintain(int mId);
    List<Maintain> selectMaintain(String mName);
}
