package dao;

import java.util.List;

import domain.User;

public interface UserDao {

	void updateUser(User u1);

	void deleteUser(User u1);

	void addUser(User u1);

	List<User> getUserByName(String username);
	
	List<User> getUser(String sql, String[] criteria);

	abstract User getUserById(int userid);

	Long getCount();

}