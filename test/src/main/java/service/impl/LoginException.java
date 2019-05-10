package service.impl;


public class LoginException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public enum ErrorType{
		NO_SUCH_USER_EXISTS,
		PASSSWORD_NULL,
		NO_MATCH_USER_EXISTS
	}
	
	public LoginException(String description, ErrorType type){
		super(description);
	}
}
