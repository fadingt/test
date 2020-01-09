package utils;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

public final class FileUtils {
    public static void main(String[] args) throws IOException {
//        String filepath = "D:/JAVAIO_TEMP/a";
//        saveFile("asdf",filepath);
//        Runtime.getRuntime().exec("notepad");
        Deque<Integer> stack = new ArrayDeque<Integer>();
        stack.push(12);
        stack.push(16);
        System.out.println(stack.size());
        int tail = stack.pop();
        System.out.println(tail + "\t" + stack.size());
        tail = stack.peek();
        System.out.println(tail + "\t" + stack.size());
        System.out.println(stack.iterator());
    }
    public static void saveFile(InputStream inputStream, String filepath) throws IOException {
        File file = new File(filepath);
        if (file.exists() && file.isFile()) {
            throw new RuntimeException("file already exists:" + filepath);
        }
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        int len = 0;
        while ((len = bis.read()) != 0) {
            bos.write(len);
            bos.flush();
        }
        System.out.println("文件已保存至:" + filepath);
        bis.close();
        bos.close();
    }

    public static void saveFile(String inputStr, String filepath) throws IOException {
        File file = new File(filepath);
        if (file.isFile() && file.exists()) {
            //TODO 图形化显示；抛出异常
            System.out.println(filepath + "已存在，请重新输入文件地址:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            saveFile(inputStr, br.readLine());
        } else if (new File(file.getParent()).isDirectory()) {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(inputStr.getBytes());
            System.out.println(inputStr);
            System.out.println("文件已保存至:" + filepath);
            bos.close();
        } else{
            //TODO 图形化显示；抛出异常
            System.out.println(filepath +"路径不合法,请重新输入文件地址：");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            saveFile(inputStr,filepath);
        }
    }

}
