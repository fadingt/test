package org.vaalbara.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaalbara.bean.Dromitory;
import org.vaalbara.dao.Idromitory;
import org.vaalbara.service.IdromitoryService;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */
@Service //说明这个类是一个service，并且把这个service注册到spring
public class DromitoryServiceImpl implements IdromitoryService {

    //声明dromitory对象
    @Autowired //默认根据类型自动装配的

    Idromitory idromitory;

    /**
     * 获取宿舍全部信息
     * @return
     */
    public List<Dromitory> getDromitoryAll() {
        return idromitory.getDromitoryAll();
    }


    /**
     * 根据Id查询宿舍
     * @param dId
     * @return
     */
    public List<Dromitory> getDromitoryById(Integer dId){
        return idromitory.getDromitoryById(dId);
    }
}
