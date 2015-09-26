<%--
 * Copyright (c) 2013 Andaily Information Technology Co. Ltd
 * www.andaily.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Andaily Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with Andaily Information Technology Co. Ltd.
--%>
<%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../comm-header.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Oauth Login</title>
</head>
<body>

<div class="row">
    <h3>Oauth Login</h3>

    <p>
        Login for client_id: '${param.client_id}'.
    </p>

    <form action="${pageContext.request.contextPath}/oauth/authorize" method="post">
        <input type="hidden" name="client_id" value="${param.client_id}"/>
        <input type="hidden" name="scope" value="${param.scope}"/>
        <input type="hidden" name="response_type" value="${param.response_type}"/>
        <input type="hidden" name="redirect_uri" value="${param.redirect_uri}"/>
        <input type="hidden" name="state" value="${param.state}"/>
        <input type="hidden" name="client_secret" value="${param.client_secret}"/>

        Username: <input type="text" name="username" value="" required="required"/>
        <br/> <br/>
        Password: <input type="password" name="password" required="required"/>
        <br/> <br/>
        <input type="submit" value="Login" class="btn btn-primary"/>
        <br/>
        <span style="color:red;"><c:if test="${not empty oauth_login_error}">Login failed</c:if></span>
    </form>
</div>

<div>
    <p>You can use the users to login as follow:</p>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>username</th>
            <th>password</th>
            <th>grant_types</th>
            <th>roles</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>test</td>
            <td>test</td>
            <td>authorization_code,password,refresh_token,client_credentials</td>
            <td>User(id=22)</td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>