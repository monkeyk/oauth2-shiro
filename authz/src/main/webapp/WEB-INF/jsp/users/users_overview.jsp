<%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../comm-header.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Users</title>
</head>
<body>
<a href="../">Home</a>

<h2>Users</h2>

<div class="pull-right">
    <a href="form/plus" class="btn btn-success btn-sm">Add User</a>
</div>
<form action="" class="form-inline">
    <div class="form-group">
        <input type="text" class="form-control" placeholder="Type username" name="username"
               value="${overviewDto.username}"/>
    </div>
    <button type="submit" class="btn btn-default">Search</button>
    &nbsp;<span class="text-info">Total: ${overviewDto.usersSize}</span>
</form>
<br/>
<table class="table table-bordered table-hover table-striped">
    <thead>
    <tr>
        <th>Username</th>
        <th>Roles[permission]</th>
        <th>CreateTime</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${overviewDto.usersDtos}" var="user">
        <tr>
            <td>${user.username}</td>
            <td><c:forEach items="${user.rolesDtos}" var="r"
                           varStatus="vs">${r.roleName} ${r.permissions}${vs.last?'':','}</c:forEach></td>
            <td>${user.createTime}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
