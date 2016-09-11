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
    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>

    <title>Login</title>
</head>
<body>

<h2 class="page-header">OAuth2-Shiro [authz]
    <small class="badge">0.3</small>
</h2>

<div>
    <p>[authz]模块用于管理client_details, user,以及获取<code>access_token</code>去访问 [resources] 模块的资源.</p>

    <strong>操作说明</strong>
    <ol>
        <li>
            <p>登录系统,使用初始的账号 test/test 或去 <a href="${contextPath}/users/overview">Users</a> 先创建用户, 这用于测试Shiro安全是否工作</p>

            <div class="row">
                <div class="col-md-5">
                    <form:form commandName="formDto" action="login" cssClass="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Username</label>

                            <div class="col-sm-9">
                                <form:input path="username" required="true" cssClass="form-control"
                                            placeholder="Type username"/> (test)
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Password</label>

                            <div class="col-sm-9">
                                <form:password path="password" required="true" cssClass="form-control"
                                               placeholder="Type password"/> (test)
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <input type="submit" value="Login" class="btn btn-primary btn-sm"/>&nbsp;
                                <span style="color:red;"><form:errors path="*" cssClass="label label-danger"/></span>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
            <br/>
        </li>
        <li>
            <p>
                在开始OAuth2之前, 建议先理解OAuth2支持的5类<code>grant_type</code>, 请访问 <a href="https://andaily.com/blog/?p=103"
                                                                             target="_blank">https://andaily.com/blog/?p=103</a>,
                或直接去看看OAuth2协议 <a href="http://oauth.net/2/" target="_blank">http://oauth.net/2/</a>
            </p>
        </li>
        <li>
            <p>
                管理client_details, 在初始时创建了默认的管理client_details
                <mark>test</mark>
                与
                <mark>mobile</mark>
                (见initial-db.ddl文件), 你可以去 <a href="${contextPath}/client_details">client_details</a>
                创建新的client_details来测试. <br/>----- client_details是OAuth2中一个核心的组件
            </p>
        </li>
        <li>
            <p>
                查看 <a href="http://git.oschina.net/mkk/oauth2-shiro/raw/master/others/oauth_test.txt" target="_blank">oauth_test.txt</a>
                文件并进行OAuth2的流程测试; 也可下载
                <a href="http://git.oschina.net/mkk/spring-oauth-client" target="_blank">spring-oauth-client</a>
                项目来测试OAuth2的流程
            </p>
        </li>
    </ol>
</div>

<div>
    <h3>菜单</h3>

    <ul>
        <li>
            <p>
                <a href="${contextPath}/users/overview">Users</a> -- 管理User
            </p>
        </li>
        <li>
            <p>
                <a href="${contextPath}/client_details">client_details</a> -- 管理client_details
            </p>
        </li>
        <li>
            <p>
                <a href="http://git.oschina.net/mkk/oauth2-shiro/raw/master/others/oauth_test.txt" target="_blank">oauth_test.txt</a>
                -- 测试OAuth2的参考URL文件
            </p>
        </li>
        <li>
            <p>
                <a href="${pageContext.request.contextPath}/resources/OS_API-0.3.html" target="_blank">API</a>
                -- oauth2-shiro提供的API文档
            </p>
        </li>
        <li>
            <p>
                <a href="${pageContext.request.contextPath}/resources/oauth_test.html">oauth_test.html</a> --
                一个用于测试各类<code>grant_type</code>的HTML页面
            </p>
        </li>
    </ul>

</div>

</body>
</html>