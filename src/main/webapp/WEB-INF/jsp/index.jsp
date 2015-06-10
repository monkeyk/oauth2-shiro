<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="comm-header.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h2>Oauth2-Shiro is work!</h2>
<a href="${contextPath}/logout">Logout</a>
<br/>
Welcome: <shiro:principal/>
</body>
</html>