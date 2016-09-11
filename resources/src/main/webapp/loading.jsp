<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8"/>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>

    <meta name="viewport" content="width=device-width,user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link rel="shortcut icon" href="${contextPath}/favicon.ico"/>

    <title>OAuth2-Shiro[resources]</title>

    <link href="${contextPath}/static/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body {
            font-family: "Microsoft YaHei", Arial;
        }
    </style>
</head>
<body>

<div class="container">

    <h2 class="page-header">OAuth2-Shiro [resources]
        <small class="badge">0.3</small>
    </h2>

    <div>
        <p class="text-muted">
            [resources]模块用于提供资源(resource), 是 access_token 的消费者, 在实际应用中, 属于业务系统(认证,安全交给 authz 模块处理), 对其API的调用需要<code>access_token</code>;
            需要先从authz模块获取access_token.
        </p>

        <strong>操作说明</strong>

        <ol>
            <li>
                <p>resources 模块提供了两个API接口,需要不同权限的access_token才可以访问, API分别为:</p>

                <p>
                    a. <a href="${contextPath}/mobile/system_time">/mobile/system_time</a> -- 获取系统时间
                </p>

                <p>
                    b. <a href="${contextPath}/rs/username">/rs/username</a> -- 获取用户信息
                </p>

                <p>
                    对API的详细使用请查看文档 <a href="${contextPath}/static/OS_API-0.3.html" target="_blank">OS_API</a>
                </p>
            </li>
            <li>
                <p>对于每一类资源(resource), 需要定义一个 resource-id, 并且需要在 rs-security.xml 中配置一个<code>OAuth2Filter</code>, 然后在
                    shiroFilter 中添加该 Filter与配置URL Pattern; 详细请参数项目中 rs-security.xml 文件的配置</p>
            </li>
            <li>
                <p>
                    <strong>注意</strong> [resources]模块的配置文件 <em>resources.properties</em>(位于src/main/resources目录)
                    中数据库连接配置信息必须与 [authz]模块中的配置一致. 因为[authz]模块获取token后存入数据库, [resources]模块在调用时从数据库中查询获取.
                </p>
            </li>
        </ol>
    </div>

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