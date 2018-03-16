<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>showTXT</title>
</head>

    <style>
     td{
     	border : 1;
        text-align:center;
        text-align:justify;
        text-justify:distribute-all-lines;
        text-align-last:justify
     }
    </style>
<body>
	<%
		int page_count = 1;
		int page_size = 10;
		int page_current = 1;
	
	%>

	<c:forEach var="line" varStatus="i" begin="" items="${list }">
		<table>
			<tr class="${i.count }">
				<td>${i.count }:</td>
				<td>${line }</td>
			</tr>
		</table>
	</c:forEach>


</body>
</html>