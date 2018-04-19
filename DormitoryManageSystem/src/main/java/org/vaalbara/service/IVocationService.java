package org.vaalbara.service;

import org.vaalbara.bean.Student;
import org.vaalbara.bean.Vocation;
import org.vaalbara.bean.dao.VocationDao;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface IVocationService {
    int addVocation(Vocation vocation);
    List<VocationDao> getAllVocation();
   int updateVocation(VocationDao voDao);
    Student getVocationById(VocationDao voDao);
    int deleteVocation(VocationDao voDao);

}
