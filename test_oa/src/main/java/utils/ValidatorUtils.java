package utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtils {
    public static void main(String[] args) {
        System.out.println(isCellString("bA123"));
    }
    public static boolean isCellString(String position){
        Pattern cellPattern = Pattern.compile("[A-Za-z]+\\d+");
        Matcher cellMatcher = cellPattern.matcher(position);
        return cellMatcher.matches();
    }
    public static ArrayList<Integer> numberString(String position){
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        Pattern strPattern = Pattern.compile("\\d+");
        Matcher intMatcher = strPattern.matcher(position);
        while(intMatcher.find()){
            arrayList.add(Integer.valueOf(intMatcher.group()));
        }
        return arrayList;
    }
    public static ArrayList<String> letterString(String position){
        ArrayList<String> arrayList = new ArrayList<String>();
        Pattern strPattern = Pattern.compile("[A-Za-z]+");
        Matcher matcher = strPattern.matcher(position);
        while (matcher.find()){
            arrayList.add(matcher.group());
        }
        return arrayList;
    }

}
