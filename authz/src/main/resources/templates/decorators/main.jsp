<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8"/>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>

    <meta name="viewport" content="width=device-width,user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link rel="shortcut icon" href="${contextPath}/favicon.ico"/>

    <title><decorator:title default=""/> . OAuth2-Shiro[authz]</title>

    <link href="${contextPath}/resources/bootstrap.min.css" rel="stylesheet"/>
    <decorator:head/>
    <style>
        body {
            font-family: "Microsoft YaHei", Arial;
        }
    </style>
</head>
<body>
<div class="container">
    <decorator:body/>

    <div class="row">
        <div class="col-md-12">
            <hr/>
            <div class="text-center">
                &copy; <a href="http://git.oschina.net/mkk/oauth2-shiro" target="_blank">oauth2-shiro</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>