package org.vaalbara.service;

import org.vaalbara.bean.Dromitory;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */
public interface IdromitoryService {

    /**
     * 获取宿舍全部信息
     * @return
     */
    public List<Dromitory> getDromitoryAll();


    /**
     * 根据Id查询宿舍
     * @param dId
     * @return
     */
    public List<Dromitory> getDromitoryById(Integer dId);
}
