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
        登录使用的 client_id: '${param.client_id}'.
    </p>

    <div class="row">
        <div class="col-md-5">
            <form action="${pageContext.request.contextPath}/oauth/authorize" method="post" class="form-horizontal">
                <input type="hidden" name="client_id" value="${param.client_id}"/>
                <input type="hidden" name="scope" value="${param.scope}"/>
                <input type="hidden" name="response_type" value="${param.response_type}"/>
                <input type="hidden" name="redirect_uri" value="${param.redirect_uri}"/>
                <input type="hidden" name="state" value="${param.state}"/>
                <input type="hidden" name="client_secret" value="${param.client_secret}"/>

                <div class="form-group">
                    <label class="col-sm-3 control-label">Username</label>

                    <div class="col-sm-9">
                        <input type="text" name="username" value="" required="required" class="form-control"
                               placeholder="Type username"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">Password</label>

                    <div class="col-sm-9">
                        <input type="password" name="password" required="required" class="form-control"
                               placeholder="Type password"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <input type="submit" value="Login" class="btn btn-primary"/>
                        &nbsp;
                        <span style="color:red;"><c:if test="${not empty oauth_login_error}">Login failed</c:if></span>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <%--<form action="${pageContext.request.contextPath}/oauth/authorize" method="post">--%>
    <%--<input type="hidden" name="client_id" value="${param.client_id}"/>--%>
    <%--<input type="hidden" name="scope" value="${param.scope}"/>--%>
    <%--<input type="hidden" name="response_type" value="${param.response_type}"/>--%>
    <%--<input type="hidden" name="redirect_uri" value="${param.redirect_uri}"/>--%>
    <%--<input type="hidden" name="state" value="${param.state}"/>--%>
    <%--<input type="hidden" name="client_secret" value="${param.client_secret}"/>--%>

    <%--Username: <input type="text" name="username" value="" required="required"/>--%>
    <%--<br/> <br/>--%>
    <%--Password: <input type="password" name="password" required="required"/>--%>
    <%--<br/> <br/>--%>
    <%--<input type="submit" value="Login" class="btn btn-primary"/>--%>
    <%--<br/>--%>
    <%--<span style="color:red;"><c:if test="${not empty oauth_login_error}">Login failed</c:if></span>--%>
    <%--</form>--%>
</div>

<div>
    <br/>

    <p>可以使用下面的账号登录或使用你自行添加的账号:</p>
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