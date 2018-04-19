package org.vaalbara.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestIO {

	public static void write(String s) {
		// TODO Auto-generated method stub
		
            try {
				FileWriter fw =new FileWriter("F:"+File.separator+"log.txt",true);
				BufferedWriter bw=new BufferedWriter(fw);
				bw.write(s);
				bw.newLine();
				bw.close();
				fw.close();
				System.out.println("1111");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
