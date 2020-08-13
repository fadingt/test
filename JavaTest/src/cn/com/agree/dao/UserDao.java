package cn.com.agree.dao;

import cn.com.agree.domain.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserDao {
    public User getUserBYUsercode(String usercode) throws IOException;

    List<User> getUserList(String sql) throws SQLException, IOException;

    List<User> getUserListBYUsercode(List<String> usercodeList) throws IOException;

    String parseOrgPath(User user);

    Map<String, String> getOrgMap(String sql) throws SQLException, IOException, ClassNotFoundException;

}

