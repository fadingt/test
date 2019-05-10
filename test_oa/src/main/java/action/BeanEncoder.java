package action;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.Date;

public class BeanEncoder {

    public static void main(String[] args) {
        String a = "d:\\txt\\t.tmp";
        String[] b = a.split("\\\\",2);
        for(String i : b){
            System.out.println(i);
        }
    }

    public static void writeObject() {
        StringBuffer loginfo = new StringBuffer();
        String filepath = "d:\\txt";
        String filename = "d:\\txt\\t.tmp";
        File dir = new File(filepath);
        if (dir.exists()) {
            loginfo.append("dir exists");
            if(dir.isDirectory())
                loginfo.append("dir is a directory");
            else
                loginfo.append("dir is not a directory");
        }
        else {
            loginfo.append("make dir");
            dir.mkdir();
        }
        File myfile = new File(filename);
        if(myfile.exists()) System.out.println("myfile exists");
        else {
            try {
                myfile.createNewFile();
                System.out.println("myfile created!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(myfile);
            oos = new ObjectOutputStream(fos);
            oos.writeInt(123456);
            oos.writeObject(new Date());
            oos.writeInt(78910);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream("d:\\txt\\t.tmp");
            ois = new ObjectInputStream(fis);
            int number = ois.readInt();
            Object today = ois.readObject();
            int number2 = ois.readInt();
            System.out.println(number+""+number2+"*****"+today);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void xmlencoder() throws FileNotFoundException {
        XMLEncoder encoder = new XMLEncoder(new FileOutputStream("d:\\liu.obj"));
        encoder.writeObject(new String("liuxingyu"));
        encoder.flush();
        encoder.close();

        XMLDecoder decoder = new XMLDecoder(new FileInputStream("d:\\liu.obj"));
        Object liu = decoder.readObject();
        decoder.close();
        System.out.println(liu);
        System.out.println(File.separator);
    }
}
