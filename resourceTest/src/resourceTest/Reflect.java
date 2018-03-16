package resourceTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {
	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Class<BeanA> clazz = BeanA.class;
		BeanA obj = (BeanA) reflectBeanA("姚明", "男", 30);
		// 4、reflect method
		Method getName = clazz.getMethod("getName");
		System.out.println("getName obj:"+getName.invoke(obj));

		Method setName = clazz.getMethod("setName", String.class);
		System.out.println("setName zhoushuang:"+setName.invoke(obj, "zhoushuang")+":"+obj.getName());
		
		System.out.println("=====================");
		// 5、reflect Field
		 Field field = clazz.getDeclaredField("name");
		 field.setAccessible(true);
		 System.out.println("name Field:"+field.get(obj));

	}

	public static Object reflectBeanA(String name, String sex, int age) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// 1.通过class属性
		// Class clazz = BeanA.class;

		// 2.通过无参构造方法
		// Class clazz = Class.forName("resourceTest.BeanA");
		// Object obj = clazz.newInstance();

		// 3、通过有参构造方法
		// Class<BeanA> clazz = (Class<BeanA>)
		// Class.forName("resourceTest.BeanA");

		// 4、
		Constructor<BeanA> constructor = BeanA.class.getConstructor(String.class, String.class, int.class);
		return constructor.newInstance(name, sex, age);
	}
}
