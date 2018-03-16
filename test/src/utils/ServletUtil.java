package utils;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


public class ServletUtil {
	public static void  showHeaders(HttpServletRequest rq) {
		Enumeration<String> names = rq.getHeaderNames();
		System.out.println("SHOW HEADERS BEGIN");
		System.out.println("===================================================================");
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			System.out.println(name + ":" + rq.getHeader(name));
		}
		System.out.println("===================================================================");
	}
	
	public static Cookie findCookie(HttpServletRequest req, String cookieName){
		Cookie[] cookies = req.getCookies();
		for(int i=0; cookies!=null && i<cookies.length; i++){
			if(cookies[i].getName().equals(cookieName))
				return cookies[i];
		}
		return null;
	}
}
