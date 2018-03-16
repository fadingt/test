package utils;

public class SingletonClass {
	private static SingletonClass instance = null;

	public static SingletonClass getInstance() {
		synchronized (SingletonClass.class) {
			if (instance == null) {
				instance = new SingletonClass();
			}
		}
		return instance;
	}

	private SingletonClass() {
	}
}
