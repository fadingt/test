package utils;

import dao.UserDao;
import dao.impl.HibernateUserDaoImpl;
import domain.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKAop implements InvocationHandler {
    private HibernateUserDaoImpl hibernateUserDao;

    public JDKAop(HibernateUserDaoImpl hibernateUserDao){
        this.hibernateUserDao = hibernateUserDao;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(hibernateUserDao,args);
    }

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("leo");
        user.setPassword("111111");
        user.setUsercode("A6853");
        InvocationHandler handler = new JDKAop(new HibernateUserDaoImpl());
        ClassLoader classLoader = HibernateUserDaoImpl.class.getClassLoader();
        UserDao proxy = (UserDao) Proxy.newProxyInstance(classLoader,HibernateUserDaoImpl.class.getInterfaces(),handler);
        proxy.addUser(user);
    }

}
