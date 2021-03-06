package org.vaalbara.service;

import org.springframework.stereotype.Service;
import org.vaalbara.bean.Links;
import org.vaalbara.bean.Notice;

import java.util.List;

/**
 * Created by Huawei on 2018/3/13.
 */
public interface INoticeService {
    /**
     * 根据Notice内容向数据库添加数据
     * @param notice
     * @return
     */
    int addNotice(Notice notice);


    /**
     * 查询所有的links信息(notice)
     * @return
     */
    List<Links> getAllLinks();

    /**
     * 根据n_id(linksId)删除一条公告
     * @param id
     * @return
     */
    int delNotice(int id);



    /**
     * 查询出所有notice
     * @return
     */
    List<Notice> getAllNotice();


    /**
     * 根据id查询一条notice
     * @param id
     * @return
     */
    Notice getNoticeById(int id);



    /**
     * 根据完整的一个notice对象进行修改
     * @param notice
     * @return
     */
    int updateNotice(Notice notice);
}
