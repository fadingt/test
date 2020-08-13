<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>userinfo</title>
</head>
<body>
<style type="text/css">
    table.hovertable {
        font-family: verdana,arial,sans-serif;
        font-size:11px;
        color:#333333;
        border-width: 1px;
        border-color: #999999;
        border-collapse: collapse;
    }
    table.hovertable th {
        background-color:#c3dde0;
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #a9c6c9;
    }
    table.hovertable tr {
        background-color:#d4e3e5;
    }
    table.hovertable td {
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #a9c6c9;
    }
</style>

<c:if test="${requestScope.users==null}">
<%--    || fn:length(tradeList) == 0--%>
    <tr>
        <td colspan="4">无用户信息</td>
    </tr>
</c:if>



    <table class="hovertable">
    <tr>
        <th>姓名</th><th>工号</th><th>手机</th><th>邮箱</th>
    </tr>
<c:forEach items="${requestScope.users}" var="item">
    <tr onmouseover="this.style.backgroundColor='#ffff66';" onmouseout="this.style.backgroundColor='#d4e3e5';">
        <td>${item.username}</td>
        <td>${item.usercode}</td>
        <td>${item.cellphone}</td>
        <td>${item.mailbox}</td>
    </tr>
</c:forEach>
    </table>


</table>

</body>
</html>