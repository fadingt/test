package org.vaalbara.dao;

import org.springframework.stereotype.Repository;
import org.vaalbara.bean.DroStudent;


import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
@Repository
public interface IStuInfo {

    /**
     * 获取宿舍学生住宿情况
     * @return
     */
    public List<DroStudent> getStuInfo(DroStudent droStudent);

    /**
     * 修改宿舍入住情况
     * @param droStudent
     * @return
     */
    public int updateDroStuInfo(DroStudent droStudent);

    /**
     * 判断学生是否入住 or 学生所在宿舍
     * @param sId
     * @return
     */
    public String existStu(Integer sId);

    /**
     *删除学生入住
     * @param droStudent
     * @return
     */
    public int deleteDroStuInfo(DroStudent droStudent);
}
