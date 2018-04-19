package org.vaalbara.dao;

import org.vaalbara.bean.Student;
import org.vaalbara.bean.Vocation;
import org.vaalbara.bean.dao.VocationDao;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */
public interface IVocationDao {
    int addVocation(Vocation vocation);
    List<VocationDao> getAllVocation();
    int updateVocation(VocationDao voDao);
    int deleteVocation(VocationDao voDao);
    Student getVocationById(VocationDao voDao);

}
