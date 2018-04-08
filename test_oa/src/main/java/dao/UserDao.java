package dao;

import java.util.List;
import java.util.Map;

import domain.User;

public interface UserDao {

	void updateUser(User user);

	void deleteUser(User user);

	void addUser(User user);

	List<User> getUserByProperty(Map<String, Object> userProperty);

	abstract User getUserById(int userid);

	Long getCount();

}