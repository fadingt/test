<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="agree" prefix="agree"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<style>
.odd {
	background-color: #FF88FF;
}

.even {
	background-color: #88FF88;
}

tr:HOVER {
	background-color: yellow;
}
</style>
<title>Login</title>
</head>
<body>
	<%
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		request.setAttribute("list", list);
	%>

	<agree:forEach var="str" items="${list }">
		${str }
	</agree:forEach>

	<table border="1" width="40%">
		<c:forEach var="str" items="${list }" varStatus="status">
			<tr class="${status.count%2==0?'even':'odd'}">
				<td>${str }</td>
			</tr>
		</c:forEach>
	</table>
	<%=this.getServletContext().getContextPath()%>
	${pageContext.servletContext.contextPath}
</body>
</html>