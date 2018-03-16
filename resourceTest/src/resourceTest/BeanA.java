package resourceTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class BeanA implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String sex;
	private int age;

	public static void main(String[] args) throws IOException {
//		Character brackets = '\u0028';
//		System.out.println(Character.isMirrored(brackets));
//		String word = new String("we)a(f)f(asd)汉字".getBytes("UTF-8"));
//		System.out.println(word);
		
		
		StringBuilder sb1 = new StringBuilder("\u00E5");
		StringBuilder sb2 = new StringBuilder("a\u030A");
		
		FileOutputStream fos = null;
		fos = new FileOutputStream(new File("d:\\unicode.txt"));
		fos.write(sb1.toString().getBytes());
		fos.write(sb2.toString().getBytes());
		fos.close();

		
		String a = System.lineSeparator();
		for (int i = 0; i < a.length(); i++) {
			int codepoint = a.codePointAt(i);
			System.out.println(a.charAt(i)+"\t"+codepoint);
		}
		System.out.println('\u23EE');
		System.out.println(a.length());
		System.out.println(a.codePointCount(0, a.length()));
		
	}

	public BeanA() {
		System.out.println("BeanA constuctor run...");
	}

	public BeanA(String name, String sex, int age) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
	}

	public void a() {
		System.out.println("method a" + ": " + name);
	}

	@Override
	public String toString() {
		return "BeanA [name=" + name + ", sex=" + sex + ", age=" + age + "]";
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
