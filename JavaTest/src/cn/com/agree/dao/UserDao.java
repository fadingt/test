package cn.com.agree.dao;

import cn.com.agree.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    List<User> getUserList(String sql) throws SQLException;
    String parseOrgpath(User user);
}

