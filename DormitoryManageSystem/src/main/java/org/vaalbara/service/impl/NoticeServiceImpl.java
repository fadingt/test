package org.vaalbara.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaalbara.bean.Links;
import org.vaalbara.bean.Notice;
import org.vaalbara.dao.INoticeDao;
import org.vaalbara.service.INoticeService;

import java.util.List;

/**
 * Created by Huawei on 2018/3/13.
 */
@Service
public class NoticeServiceImpl implements INoticeService{
    @Autowired
    private INoticeDao iNoticeDao;

    public int addNotice(Notice notice) {
        return iNoticeDao.addNotice(notice);
    }

    public List<Links> getAllLinks() {
        return iNoticeDao.getAllLinks();
    }

    public int delNotice(int id) {
        return iNoticeDao.delNotice(id);
    }

    public List<Notice> getAllNotice() {
        return iNoticeDao.getAllNotice();
    }

    public Notice getNoticeById(int id) {
        return iNoticeDao.getNoticeById(id);
    }

    public int updateNotice(Notice notice) {
        return iNoticeDao.updateNotice(notice);
    }
}
