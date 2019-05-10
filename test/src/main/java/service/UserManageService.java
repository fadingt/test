package service;

import java.util.List;

import domain.User;

public interface UserManageService {
	public void addUser(User user);
	public User getUser(int userid);
	public String login(String username, String password);
	public int regist(User user);
	public List<User> SearchUser(String sql, String[] criteria);
}
