<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>
</head>
<body>
	<font color="red">welcome!</font>
	<form action="${pageContext.request.contextPath}/action/upload_up" enctype="multipart/form-data" method="post">
		文件名：<input type="text" name="imageFileName" value="imageFileName">
		请选择文件：<input type="file" name="image">
		<input type="submit" value="上传">
	</form>
</body>
</html>