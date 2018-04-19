package org.vaalbara.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaalbara.bean.Admin;
import org.vaalbara.dao.IAdminDao;
import org.vaalbara.service.IAdminService;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */
@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    IAdminDao iAdminDao;
    @Override
    public int updatePass(Admin admin) {
        return iAdminDao.updatePass(admin) ;
    }

    @Override
    public List<Admin> adminLogin(Admin admin) {
        return iAdminDao.adminLogin(admin);
    }
}
