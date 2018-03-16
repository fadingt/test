package resourceTest;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

public class Regex {

	public static void main(String[] args) {
		// test();
		// test2();
		String s1 = "192.168.10.34.\\E123";
		// String s2 = "127.0.0.1";
		// String s3 = "3.3.3.3";
		// String s4 = "105.70.11.55";
		// Pattern ipaddr = Pattern.compile("(\\d{1,3}\\.){3}\\d{1,3}");
		ArrayList<Integer> a1 = toIntArr(s1);
		System.out.println(a1.toString());
		System.out.println("================");
		int flag = 341;
		int index = 5;
		int mask = (1 << index-1);
//		flag = flag & mask 
		int pos = (flag & mask) >> index-1;
		System.out.println(pos);

		System.out.println(Integer.valueOf("101010101", 2));
	}

	public static ArrayList<Integer> toIntArr(String s1) {
		Pattern pattern = Pattern.compile("\\.");
		System.out.println(Pattern.quote(s1));
//		System.out.println(pattern.flags());
		String[] ipArray = pattern.split(s1, 0);
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			result.add(Integer.valueOf(ipArray[i]));
		}
		return result;
	}

	public static void test2() {
		Map<String, String> m = System.getenv();
		Set<Entry<String, String>> sets = m.entrySet();

		for (Entry<String, String> set : sets) {
			System.out.println(set.getKey() + "\t:\t" + set.getValue());

		}
	}

	public static void test() {
		String mailboxa = "9zliuxingyu@gmail.com ";
		String mailboxb = "fading_tale@qq.com";
		String mailboxc = "378730609@agree.com.cn";
		String mailboxd = "¡ı–À”Ó@qq.com";
		String mailboxe = "a@hello.cn";
		Pattern pattern = Pattern.compile("\\w{1,}@{1}[\\w]+(.\\w+)+");

		System.out.println(pattern.matcher(mailboxa).matches());
		System.out.println(pattern.matcher(mailboxb).matches());
		System.out.println(pattern.matcher(mailboxc).matches());
		System.out.println(pattern.matcher(mailboxd).matches());
		System.out.println(pattern.matcher(mailboxe).matches());
		System.out.println("===========================================");
		System.out.println(pattern.matcher(mailboxa).lookingAt());
		System.out.println(pattern.matcher(mailboxd).lookingAt());
		System.out.println("===========================================");
	}

}
