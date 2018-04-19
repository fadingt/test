package action;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class BeanEncoder {

    public static void main(String[] args) throws FileNotFoundException {
        XMLEncoder encoder = new XMLEncoder(new FileOutputStream("e:\\liu.obj"));
        encoder.writeObject(new String("liuxingyu"));
        encoder.flush();
        encoder.close();

        XMLDecoder decoder = new XMLDecoder(new FileInputStream("e:\\liu.obj"));
        Object liu = decoder.readObject();
        decoder.close();
        System.out.println(liu);
    }
}
