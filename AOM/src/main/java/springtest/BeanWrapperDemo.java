package springtest;

import domain.paasaom.User;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class BeanWrapperDemo {
    public static void main(String[] args) {
        BeanWrapper user = new BeanWrapperImpl(new User());
        user.setPropertyValue("username","liuxingyu");
        System.out.println(user.toString());
        System.out.println(user.getWrappedClass()+user.getWrappedInstance().toString());

    }
}
