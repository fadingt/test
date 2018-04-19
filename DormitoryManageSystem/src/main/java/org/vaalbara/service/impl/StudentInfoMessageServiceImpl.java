package org.vaalbara.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaalbara.bean.StudentInfoMessage;
import org.vaalbara.dao.IStudentInfoMessageDao;
import org.vaalbara.service.IStudentInfoMessageService;

/**
 * Created by Huawei on 2018/3/16.
 */
@Service
public class StudentInfoMessageServiceImpl implements IStudentInfoMessageService {

    @Autowired
    IStudentInfoMessageDao iStudentInfoMessageDao;

    public StudentInfoMessage getAllMessage(int sId) {
        return iStudentInfoMessageDao.getAllMessage(sId);
    }

    public int getVocationCount(int sId) {
        return iStudentInfoMessageDao.getVocationCount(sId);
    }

    public int getRepairCount(int sId) {
        return iStudentInfoMessageDao.getRepairCount(sId);
    }

    public int getCriticizeCount(int sId) {
        return iStudentInfoMessageDao.getCriticizeCount(sId);
    }
}
