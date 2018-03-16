<%@page import="utils.ServletUtil"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Expires" content="0">
<title>Login</title>
</head>
<body>

	<font color="green">Login</font>
	<br>
	<font color="pink">===============================</font>
	<br>
	<%
		Cookie cookie = ServletUtil.findCookie(request, "loginfo");
		if(null != cookie){
			out.print("loginfo cookie not null");
			response.sendRedirect("localhost:8080/test/login_execute");
		}
	%>
	<s:fielderror />
	<form action="/test/login_execute" method="post">
		<table align="left" width="40%" bgcolor=#88FFFF border="1">
			<tr>
				<td>username:</td>
				<td><input type="text" name="user.username"
					value="${user.username}"></td>
			</tr>
			<tr>
				<td>password:</td>
				<td><input type="password" name="user.password" value="${user.password}"></td>
			</tr>
			<tr>
				<td><input type="submit" value="login"></td>
				<td><input type="reset" value="reset"></td>
				<td>使用cookie?<input type="checkbox"  name="cookieStat" value="true"></td>
			</tr>
		</table>
	</form>
</body>
</html>