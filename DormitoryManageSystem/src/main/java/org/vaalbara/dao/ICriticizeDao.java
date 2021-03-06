package org.vaalbara.dao;

import org.springframework.stereotype.Repository;
import org.vaalbara.bean.Criticize;
import org.vaalbara.bean.News;

import java.util.List;

/**
 * Created by Huawei on 2018/3/14.
 */
@Repository
public interface ICriticizeDao {
    /**
     * 查询处分表中所有数据
     * @return
     */
    List<News> getAllCriticize();


    /**
     * 根据Criticize对象将数据插入数据库
     * @param criticize
     * @return
     */
    int addCriticize(Criticize criticize);


    /**
     * 根据id删除一条处分
     * @param id
     * @return
     */
    int delCriticize(int id);


    /**
     *根据id查找一条处分
     * @param id
     * @return
     */
    Criticize getCriticize(int id);


    /**
     * 修改处分
     * @param criticize
     * @return
     */
    int updateCriticize(Criticize criticize);
}
