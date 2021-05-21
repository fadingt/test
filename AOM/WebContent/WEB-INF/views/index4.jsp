<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$.ajax({
			type : "post",
			url : "index4_jsonarr.do",
			contentType : "application/JSON;charset=UTF-8",
			async : true,
			success : function(data) {
				console.log(data);
				var users = document.getElementById("users");
				for(var i=0;i<data.length;i++){
					var user = document.createElement("div");
					var text = document.createTextNode(data[i].toString);
					user.appendChild(text);
					users.appendChild(user);
				}
			},
			error : function() {
				return;
			}
		});
	});
</script>
<title>userinfo</title>
</head>
<body>
	<h2>index4</h2>
	
	<div id="users"></div>
</body>

</html>