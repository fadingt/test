<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>SearchUser</title>
</head>
<body>
<%int page_count = 20; %>
	<form action="/test/getuser_getuserbyid" method="post">
		<table bgcolor=#88FFFF border="1">
			<tr>
				<td>姓名：<input type="text" name="user.username"></td>
				<td>部门：<input type="text" name="user.deptname"></td>
				<td>性别：<input type="text" name="user.sex"></td>
			</tr>
			<tr>
				<td><input type="submit" value="查询"></td>
				<td><input type="reset" value="重置"></td>
			</tr>
			<tr>
				<td>userid</td>
				<td>username</td>
				<td>password</td>
				<td>deptname</td>
				<td>sex</td>
			</tr>

			<c:forEach items="${users }" var="user">
				<tr>
					<td>${user.userid }</td>
					<td>${user.username }</td>
					<td>${user.password }</td>
					<td>${user.deptname }</td>
					<td>${user.sex }</td>
				</tr>
			</c:forEach>

		</table>
	</form>
</body>
</html>