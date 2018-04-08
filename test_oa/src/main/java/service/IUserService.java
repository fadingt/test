package service;

import domain.User;

public interface IUserService {
	int loginCheck(User user);
	int saveUser(User user);
}
