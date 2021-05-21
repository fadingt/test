<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="http://ajax.microsoft.com/ajax/jquery/jquery-1.4.min.js"></script>
<script>
	$(document).ready(function() {
		$.ajax({
			type : "post",
			url : "prod.do",
/* 			datType: "JSON",
			contenttype : "application/JSON;charset=UTF-8", */
			success : function(data) {
				console.log(data);
				alert(data);
			},
			error : function() {
				alert("error");
			}
		});
	});
</script>
<title>Insert title here</title>
</head>
<body>
	<h2>index2 page</h2>
	<br /> ${pageContext.servletContext.contextPath}
	<br />

</body>

</html>