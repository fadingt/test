package org.vaalbara.service;

import org.vaalbara.bean.Admin;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */
public interface IAdminService {
    int updatePass(Admin admin);

    List<Admin> adminLogin(Admin admin);
}
