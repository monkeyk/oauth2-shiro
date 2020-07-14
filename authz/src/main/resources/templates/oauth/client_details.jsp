<%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../comm-header.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Client Details</title>
    <style>
        .list-group li:hover {
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
<a href="${contextPath}/">Home</a>

<h2>Client Details</h2>

<div class="pull-right">
    <a href="client_details/form/plus" class="btn btn-success">Add Client</a>
</div>
<form action="" class="form-inline">
    <div class="form-group">
        <input type="text" class="form-control" placeholder="Type clientId" name="clientId"
               value="${listDto.clientId}"/>
    </div>
    <button type="submit" class="btn btn-default">Search</button>
    &nbsp;<span class="text-info">Total: ${listDto.size}</span>
</form>
<br/>

<div>
    <ul class="list-group">
        <c:if test="${empty listDto.clientDetailsDtos}">
            <li class="list-group-item">
                <p>Empty result</p>
            </li>
        </c:if>
        <c:forEach items="${listDto.clientDetailsDtos}" var="cli">
            <li class="list-group-item">
                <div class="pull-right">
                    <c:if test="${not cli.archived}">
                        <a href="${contextPath}/client_details/test_client/${cli.clientId}">test</a>
                    </c:if>
                    <c:if test="${cli.archived}"><strong class="text-muted">Archived</strong></c:if>
                </div>
                <h3 class="list-group-item-heading">
                        ${cli.clientName}
                    <small>${cli.grantTypes}</small>
                </h3>

                <div class="list-group-item-text text-muted">
                    client_id: <span class="text-danger">${cli.clientId}</span>&nbsp;
                    client_secret: <span class="text-primary">${cli.clientSecret}</span>&nbsp;
                    <br/>
                    grant_types: <span class="text-primary">${cli.grantTypes}</span>&nbsp;
                    resource_ids: <span class="text-primary">${cli.resourceIds}</span>&nbsp;
                    <br/>
                    scope: <span class="text-primary">${cli.scope}</span>&nbsp;
                    redirect_uri: <span class="text-primary">${cli.redirectUri}</span>&nbsp;
                    <br/>
                    roles: <span class="text-primary">${cli.roles}</span>&nbsp;
                    access_token_validity: <span class="text-primary">${cli.accessTokenValidity}</span>&nbsp;
                    refresh_token_validity: <span class="text-primary">${cli.refreshTokenValidity}</span>&nbsp;
                    <br/>
                    client_uri: <span class="text-primary">${cli.clientUri}</span>&nbsp;
                    client_icon_uri: <span class="text-primary">${cli.clientIconUri}</span>&nbsp;
                    <br/>
                    create_time: <span class="text-primary">${cli.createTime}</span>&nbsp;
                    archived: <strong class="${cli.archived?'text-warning':'text-primary'}">${cli.archived}</strong>&nbsp;
                    trusted: <span class="text-primary">${cli.trusted}</span>&nbsp;
                    description: <span class="text-primary">${cli.description}</span>&nbsp;
                </div>
            </li>
        </c:forEach>

    </ul>
    <p class="help-block">
        每一个item对应<code>oauth_client_details</code>表中的一条数据; 共<strong>${listDto.size}</strong>条数据.
    </p>
</div>
</body>
</html>
