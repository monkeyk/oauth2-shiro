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
<%@ include file="comm-header.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h3>Login
    <small>If you want</small>
</h3>

<div>
    <form:form commandName="formDto" action="login">
        Username: <form:input path="username" required="true"/> (test)
        <br/> <br/>
        Password: <form:password path="password" required="true"/> (test)
        <br/> <br/>
        <input type="submit" value="Login" class="btn btn-primary"/>
        <br/>
        <span style="color:red;"><form:errors path="*"/></span>
    </form:form>
</div>
<hr/>
<div>
    <h3>Oauth
        <small>Ignore login, testing oauth directly</small>
    </h3>

    <p>
        See <a href="http://git.oschina.net/mkk/oauth2-shiro/raw/master/others/oauth_test.txt" target="_blank">oauth_test.txt</a>
        firstly.
    </p>

    <p>
        <a href="${contextPath}/resources/oauth_test.html">oauth_test</a>
    </p>
</div>

</body>
</html>