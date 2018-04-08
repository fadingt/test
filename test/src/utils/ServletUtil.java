package utils;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


public class ServletUtil {
	public static void  showHeaders(HttpServletRequest req) {
		Enumeration<String> names = req.getHeaderNames();
		System.out.println("==============================HEAD=================================");
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			System.out.println(name + ":" + req.getHeader(name));
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
