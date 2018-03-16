package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	public static String Trim(String str){
		Pattern trimPattern = Pattern.compile("\\s+\\S+\\s+");
		Matcher matcher = trimPattern.matcher(str);
		System.out.println(matcher.matches());
		String[] result = trimPattern.split(str,-1);
//		System.out.println(result.length);
		for(String i : result){
			System.out.println(i);
		}
		return str;
	}
	
	public static void main(String[] args) {
		Trim(" 12 3s");
	}
}
