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
    <strong>Oauth</strong>

    <p>
        <a href="${contextPath}/resources/oauth_test.html">oauth_test</a>
    </p>
</div>
<hr/>
<div>
    <strong>Menus</strong>

    <p class="help-block">Test different roles show different menus</p>
    <ul>
        <shiro:hasRole name="Admin">
            <li><a href="#">Admin Action</a></li>
        </shiro:hasRole>
        <shiro:hasRole name="User">
            <li><a href="#">User Action</a></li>
        </shiro:hasRole>
        <shiro:hasPermission name="user:list">
            <li><a href="user/list">User List</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="user:create">
            <li><a href="user/create">User Create</a></li>
        </shiro:hasPermission>
    </ul>
</div>
</body>
</html>