package resourceTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeDemo {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		BeanA beana = new BeanA();
		beana.setAge(1);
		beana.setName("serialazedBeana");
		beana.setSex("male");
//	System.out.println(File.separator);
		File f = new File("d:\\serializedBeana.ser");
//		if(!f.exists()){
//			f.createNewFile();
//		}
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(beana);
		oos.close();
		fos.close();
		
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		BeanA o = (BeanA) ois.readObject();
		System.out.println(o.toString());
		ois.close();
		fis.close();
	}
}
