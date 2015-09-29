<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>

<html>
<head>
    <title>Oauth Approval</title>
</head>
<body><h1>OAuth Approval</h1>

<p>Do you authorize '${param.client_id}' to access your protected resources?</p>

<form id='confirmationForm' name='confirmationForm' action='${pageContext.request.contextPath}/oauth/authorize'
      method='post'>
    <input type="hidden" name="client_id" value="${param.client_id}"/>
    <input type="hidden" name="scope" value="${param.scope}"/>
    <input type="hidden" name="response_type" value="${param.response_type}"/>
    <input type="hidden" name="redirect_uri" value="${param.redirect_uri}"/>
    <input type="hidden" name="state" value="${param.state}"/>
    <input type="hidden" name="client_secret" value="${param.client_secret}"/>

    <input name='user_oauth_approval' value='true' type='hidden'/>
    <label> <input value='Authorize' type='submit' class="btn btn-success"/></label>
</form>

<form id='denialForm' name='denialForm' action='${pageContext.request.contextPath}/oauth/authorize' method='post'>
    <input type="hidden" name="client_id" value="${param.client_id}"/>
    <input type="hidden" name="scope" value="${param.scope}"/>
    <input type="hidden" name="response_type" value="${param.response_type}"/>
    <input type="hidden" name="redirect_uri" value="${param.redirect_uri}"/>
    <input type="hidden" name="state" value="${param.state}"/>
    <input type="hidden" name="client_secret" value="${param.client_secret}"/>

    <input name='user_oauth_approval' value='false' type='hidden'/>
    <label><input value='Deny' type='submit' class="btn btn-warning"/></label>
</form>
</body>
</html>