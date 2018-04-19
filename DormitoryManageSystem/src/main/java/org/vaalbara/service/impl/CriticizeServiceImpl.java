package org.vaalbara.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaalbara.bean.Criticize;
import org.vaalbara.bean.News;
import org.vaalbara.dao.ICriticizeDao;
import org.vaalbara.service.ICriticizeService;

import java.util.List;

/**
 * Created by Huawei on 2018/3/14.
 */
@Service
public class CriticizeServiceImpl implements ICriticizeService{

    @Autowired
    ICriticizeDao iCriticizeDao;

    public List<News> getAllCriticize() {
        return iCriticizeDao.getAllCriticize();
    }

    public int addCriticize(Criticize criticize) {
        return iCriticizeDao.addCriticize(criticize);
    }

    public int delCriticize(int id) {
        return iCriticizeDao.delCriticize(id);
    }

    public Criticize getCriticize(int id) {
        return iCriticizeDao.getCriticize(id);
    }

    public int updateCriticize(Criticize criticize) {
        return iCriticizeDao.updateCriticize(criticize);
    }
}
