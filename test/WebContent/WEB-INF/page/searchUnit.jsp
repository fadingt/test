<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>SearchUnit</title>
</head>
<body>
	<form action="/test/unit_searchUnit" method="post">
		<table bgcolor=#88FFFF border="1">
			<tr>
				<td>id：<input type="text" name="unit.unitid"></td>
			</tr>
			<tr>
				<td><input type="submit" value="查询"></td>
				<td><input type="reset" value="重置"></td>
			</tr>
			<tr>
				<td>unitid</td>
				<td>unitname</td>
				<td>brno</td>
				<td>isdel</td>
			</tr>
			<tr>
				<td>${unit.unitid }</td>
				<td>${unit.unitname }</td>
				<td>${unit.brno }</td>
				<td>${unit.isdel }</td>

			</tr>

		</table>
	</form>
</body>
</html>