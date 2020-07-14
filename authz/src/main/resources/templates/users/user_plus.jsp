<%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../comm-header.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Add User</title>
</head>
<body>
<a href="../">Home</a>

<h2>Add User</h2>


<form:form commandName="formDto" cssClass="form-horizontal">
    <div class="form-group">
        <label class="col-sm-2 control-label">Username<em class="text-danger">*</em></label>

        <div class="col-sm-10">
            <form:input path="username" cssClass="form-control" placeholder="Type username"
                        required="required"/>

            <p class="help-block">Username, unique.</p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Password<em class="text-danger">*</em></label>

        <div class="col-sm-10">
            <form:password path="password" cssClass="form-control" placeholder="Type password"
                           required="required"/>

            <p class="help-block">Password, required.</p>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">Roles<em class="text-danger">*</em></label>

        <div class="col-sm-10">
            <c:forEach items="${formDto.rolesDtos}" varStatus="vs" var="role">
                <input type="hidden" name="rolesDtos[${vs.index}].guid" value="${role.guid}"/>
                <input type="hidden" name="rolesDtos[${vs.index}].roleName" value="${role.roleName}"/>

                <label class="checkbox">
                    <form:checkbox path="roleGuids" value="${role.guid}"/> ${role.roleName} ${role.permissions}
                </label>
            </c:forEach>

            <p class="help-block">Select Roles</p>
        </div>
    </div>


    <div class="form-group">
        <div class="col-sm-2"></div>
        <div class="col-sm-10">
            <form:errors path="*" cssClass="label label-warning"/>
        </div>
    </div>


    <div class="form-group">
        <div class="col-sm-2"></div>
        <div class="col-sm-10">
            <button type="submit" class="btn btn-success">Save</button>
            <a href="../overview">Cancel</a>
        </div>
    </div>
</form:form>


</body>
</html>
