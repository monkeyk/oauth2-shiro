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
<h3>Login</h3>

<div>
    <form:form commandName="formDto" action="login">
        Username: <form:input path="username" required="true"/>
        <br/> <br/>
        Password: <form:password path="password" required="true"/>
        <br/> <br/>
        <input type="submit" value="Login" class="btn btn-primary"/>
        <br/>
        <span style="color:red;"><form:errors path="*"/></span>
    </form:form>
</div>


</body>
</html>