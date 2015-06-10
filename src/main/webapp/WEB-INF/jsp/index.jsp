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
<hr/>
<div>
    <strong>Menus</strong>
    <ul>
        <shiro:hasRole name="Admin">
            <li><a href="#">Admin Action</a></li>
        </shiro:hasRole>
        <shiro:hasRole name="User">
            <li><a href="#">User Action</a></li>
        </shiro:hasRole>
        <shiro:hasPermission name="admin:list">
            <li><a href="#">User List</a></li>
        </shiro:hasPermission>
         <shiro:hasPermission name="test:list">
            <li><a href="#">User List(Test)</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="admin:create">
            <li><a href="#">User Create</a></li>
        </shiro:hasPermission>
    </ul>
</div>
</body>
</html>