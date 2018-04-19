package org.vaalbara.dao;

import org.springframework.stereotype.Repository;
import org.vaalbara.bean.Admin;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */
@Repository
public interface IAdminDao {
    public int updatePass(Admin admin);
    List<Admin> adminLogin(Admin admin);
}
