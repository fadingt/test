package resourceTest;

public class RegexDemo {
	public static void main(String[] args) {
		Demo1();
//		Demo2();
	}
	private static void Demo1() {
		String str = "我.....我我我..........要要........";
		str = str.replaceAll("\\.+", "");
		System.out.println(str);
	}
//	private static void Demo2() {
////		ip地址排序
//		String ip1 = "192.168.10.34";
//		String ip2 = " 127.0.0.1";
//		String ip3 = "3.3.3.3";
//		String ip4 = "105.70.11.55";
//		System.out.println(ip1);
//		
//	}
}
