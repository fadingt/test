<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.js"></script>
<script type="text/javascript">
console.log(JSON.stringify({"username" : "liuxingyu","password" : "abc"}));
 	$(document).ready(function() {
		$.ajax({
			type : "post",
			url : "cons.do",
			contentType : "application/JSON;charset=UTF-8",
			data : '{"username" : "liuxingyu","password" : "abc"}',
			success : function(data) {
				console.log(data);
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
	<h2>cons.do</h2>
	${pageContext.request.serverName} ${pageContext.request.localPort}
	<br />
</body>

</html>