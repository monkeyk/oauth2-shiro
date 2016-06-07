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
    <strong>测试OAuth</strong>

    <p>
        <a href="${contextPath}/resources/oauth_test.html">oauth_test</a>
    </p>
</div>
<hr/>
<div>
    <strong>菜单</strong>

    <p class="help-block">根据不同的Role会显示不同的菜单 (Shiro权限控制)</p>
    <ul>
        <shiro:hasRole name="Admin">
            <li><a href="#">Admin Action</a></li>
        </shiro:hasRole>
        <shiro:hasRole name="User">
            <li><a href="#">User Action</a></li>
        </shiro:hasRole>
        <shiro:hasPermission name="user:list">
            <li><a href="#">User List</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="user:create">
            <li><a href="#">User Create</a></li>
        </shiro:hasPermission>
    </ul>
</div>
</body>
</html>