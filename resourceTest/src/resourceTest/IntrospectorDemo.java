package resourceTest;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;

public class IntrospectorDemo {

	public static void main(String[] args) throws IntrospectionException, ClassNotFoundException, NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		Object obj = Reflect.reflectBeanA("liuxingyu", "男", 24);
		BeanInfo beanInfo = Introspector.getBeanInfo(BeanA.class, Object.class);
		// 1、PropertyDescriptor 获得属性
		System.out.println("1、PropertyDescriptor 获得属性=======================");
		getProperty(obj, beanInfo);
		// 2、MethodDescriptor 获得方法
		System.out.println("2、MethodDescriptor 获得方法=======================");
		getMethod(beanInfo);
		// String[] paths = Introspector.getBeanInfoSearchPath();
		// for (String path : paths) {
		// System.out.println("=====================");
		// System.out.println(paths.length + "bean(s)");
		// System.out.println(path);
		// }
	}

	private static void getMethod(BeanInfo beanInfo) {
		// 2、MethodDescriptor 获得方法
		MethodDescriptor[] mds = beanInfo.getMethodDescriptors();
		for (MethodDescriptor md : mds) {
			System.out.print(md.getMethod().getReturnType().getSimpleName() + "\t" + md.getMethod().getName()+"(");
			Class<?>[] clazzs = md.getMethod().getParameterTypes();
			for (Class<?> clazz : clazzs) {
				System.out.print(clazz.getSimpleName());
			}
			System.out.println(")");
		}
	}

	private static void getProperty(Object obj, BeanInfo beanInfo)
			throws IllegalAccessException, InvocationTargetException {
		// 1、PropertyDescriptor 获得属性
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor pd : pds) {
			System.out.println(pd.getPropertyType().getSimpleName()+"\t"+pd.getName() + "\t" + pd.getReadMethod().invoke(obj));
		}
	}

}
