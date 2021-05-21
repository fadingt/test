package service;

import domain.paasaom.User;

public interface IUserService {
	int loginCheck(User user);
	int saveUser(User user);
	boolean resetPassword(int userid, String newPwd);
}
