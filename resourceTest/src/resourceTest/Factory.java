package resourceTest;

//import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Factory {

    public static void main(String[] args) {
        System.out.println(Class.class.getResource("/").getPath());
        AbstractXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
//        BeanFactory beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
        BeanB b = (BeanB) beanFactory.getBean("beanB");
        BeanB b2 = (BeanB) beanFactory.getBean("beanB");
        System.out.println(b == b2);
        beanFactory.close();
    }

}
