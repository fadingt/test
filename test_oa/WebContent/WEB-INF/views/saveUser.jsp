<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>saveUser</h2>
	<form action="/test_oa/saveUser.do" method="get">
		<input name="usercode" value="usercode"><sf:errors/>
		<input name="password" value="password">
		<input name="mailbox" value="mailbox">
		<input name="cellphone" value="cellphone">
		<input name="state" value="1">
		<input name="username" value="username">
		<input name="deptname" value="deptname">
		<input name="sex" value="1">
		<input type="submit" value="submit"> 
	</form>
</body>
</html>