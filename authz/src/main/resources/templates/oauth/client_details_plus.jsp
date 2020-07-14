<%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../comm-header.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Add Client</title>
    <style>
        .list-group li:hover {
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
<a href="${pageContext.request.contextPath}/">Home</a>

<h2>Add Client</h2>

<div>
    <p class="help-block">
        这里列出了一个 client_details 需要的所有属性进行添加, 但在实际使用场景中, 许多属性是由系统处理的, 不需要用户关心.
    </p>

    <div>
        <form:form commandName="formDto" cssClass="form-horizontal">
        <div class="form-group">
            <label for="clientId" class="col-sm-2 control-label">client_id<em class="text-danger">*</em></label>

            <div class="col-sm-10">
                <form:input path="clientId" cssClass="form-control" id="clientId" placeholder="client_id"
                            required="required"/>

                <p class="help-block">client_id必须输入,且必须唯一,长度至少5位; 在实际应用中的另一个名称叫appKey,与client_id是同一个概念.</p>
            </div>
        </div>
        <div class="form-group">
            <label for="clientSecret" class="col-sm-2 control-label">client_secret<em
                    class="text-danger">*</em></label>

            <div class="col-sm-10">
                <form:input path="clientSecret" cssClass="form-control" id="clientSecret"
                            placeholder="client_secret" required="required"/>

                <p class="help-block">client_secret必须输入,且长度至少8位; 在实际应用中的另一个名称叫appSecret,与client_secret是同一个概念.</p>
            </div>
        </div>
        <div class="form-group">
            <label for="resourceIds" class="col-sm-2 control-label">resource_ids<em
                    class="text-danger">*</em></label>

            <div class="col-sm-10">
                <form:select path="resourceIds" cssClass="form-control" id="resourceIds">
                    <form:option value="os-resource">os-resource</form:option>
                    <form:option value="mobile-resource">mobile-resource</form:option>
                </form:select>

                <p class="help-block">resourceIds, 用于定义一组资源的Id, 在[resources]模块中使用,
                    可在[resources]模块的<em>rs-security.xml</em>查看定义的resourceId(每一个 OAuth2Filter 代表一个资源)</p>
            </div>
        </div>

        <div class="form-group">
            <label for="scope" class="col-sm-2 control-label">scope<em class="text-danger">*</em></label>

            <div class="col-sm-10">
                <form:select path="scope" id="scope" cssClass="form-control">
                    <form:option value="read">read</form:option>
                    <form:option value="write">write</form:option>
                </form:select>

                <p class="help-block">scope必须选择</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">grant_type(s)<em class="text-danger">*</em></label>

            <div class="col-sm-10">
                <label class="checkbox-inline">
                    <input type="checkbox" name="grantTypes"
                           value="authorization_code" ${fun:containsIgnoreCase(formDto.grantTypes, 'authorization_code') ?'checked':''} />
                    authorization_code
                </label>
                <label class="checkbox-inline">
                    <input type="checkbox" name="grantTypes"
                           value="password" ${fun:containsIgnoreCase(formDto.grantTypes, 'password') ?'checked':''} />
                    password
                </label>
                <label class="checkbox-inline">
                    <input type="checkbox" name="grantTypes"
                           value="client_credentials" ${fun:containsIgnoreCase(formDto.grantTypes, 'client_credentials') ?'checked':''} />
                    client_credentials
                </label>
                <label class="checkbox-inline">
                    <input type="checkbox" name="grantTypes"
                           value="implicit" ${fun:containsIgnoreCase(formDto.grantTypes, 'implicit') ?'checked':''} />
                    implicit
                </label>
                <label class="checkbox-inline">
                    <input type="checkbox" name="grantTypes"
                           value="refresh_token" ${fun:containsIgnoreCase(formDto.grantTypes, 'refresh_token') ?'checked':''} />
                    refresh_token
                </label>

                <p class="help-block">至少勾选一项grant_type(s), 且不能只单独勾选<code>refresh_token</code>, 若需更多帮助请访问 <a
                        href="https://andaily.com/blog/?p=103"
                        target="_blank">https://andaily.com/blog/?p=103</a></p>
            </div>
        </div>

        <div class="form-group">
            <label for="redirectUri" class="col-sm-2 control-label">redirect_uri</label>

            <div class="col-sm-10">
                <form:input path="redirectUri" id="redirectUri" placeholder="http://..."
                            cssClass="form-control"/>

                <p class="help-block">若<code>grant_type</code>包括<em>authorization_code</em>,<em>implicit</em>,
                    则必须输入redirect_uri</p>
            </div>
        </div>

        <div class="form-group">
            <label for="roles" class="col-sm-2 control-label">roles</label>

            <div class="col-sm-10">
                <form:select path="roles" id="roles" cssClass="form-control">
                    <form:option value="">无</form:option>
                    <form:options items="${formDto.rolesDtoList}" itemLabel="roleName" itemValue="id"/>
                </form:select>

                <p class="help-block">指定客户端所拥有的Shiro Role,可选; 尚未使用</p>
            </div>
        </div>

        <div class="form-group">
            <label for="accessTokenValidity" class="col-sm-2 control-label">access_token_validity</label>

            <div class="col-sm-10">
                <input type="number" class="form-control" name="accessTokenValidity" id="accessTokenValidity"
                       placeholder="access_token_validity"/>

                <p class="help-block">设定客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时);
                    若设定则必须是大于0的整数值</p>
            </div>
        </div>

        <div class="form-group">
            <label for="refreshTokenValidity" class="col-sm-2 control-label">refresh_token_validity</label>

            <div class="col-sm-10">
                <input type="number" class="form-control" name="refreshTokenValidity" id="refreshTokenValidity"
                       placeholder="refresh_token_validity"/>

                <p class="help-block">设定客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30,
                    30天);
                    若设定则必须是大于0的整数值</p>
            </div>
        </div>

        <div class="form-group">
            <label for="clientName" class="col-sm-2 control-label">client_name<em class="text-danger">*</em></label>

            <div class="col-sm-10">
                <form:input path="clientName" id="clientName" placeholder="Client名称" cssClass="form-control"
                            required="true"/>

                <p class="help-block">给Client起一个名称</p>
            </div>
        </div>

        <div class="form-group">
            <label for="clientUri" class="col-sm-2 control-label">client_uri</label>

            <div class="col-sm-10">
                <form:input path="clientUri" id="clientUri" placeholder="http://..." cssClass="form-control"/>

                <p class="help-block">该Client的URI, 可选</p>
            </div>
        </div>
        <div class="form-group">
            <label for="clientIconUri" class="col-sm-2 control-label">client_icon_uri</label>

            <div class="col-sm-10">
                <form:input path="clientIconUri" id="clientIconUri" placeholder="http://..." cssClass="form-control"/>

                <p class="help-block">该Client的Icon URI, 可选</p>
            </div>
        </div>

        <div class="form-group">
            <label for="description" class="col-sm-2 control-label">description</label>

            <div class="col-sm-10">
                <input type="text" class="form-control" name="description" id="description"
                       placeholder="Client Description"/>

                <p class="help-block">Client的描述, 可选</p>
            </div>
        </div>


        <div class="form-group">
            <label class="col-sm-2 control-label">trusted</label>

            <div class="col-sm-10">
                <label class="radio-inline">
                    <input type="radio" name="trusted" value="true"/> Yes
                </label>
                <label class="radio-inline">
                    <input type="radio" name="trusted" value="false" checked="checked"/> No
                </label>

                <p class="help-block">该属性是扩展的,
                    只适用于grant_type(s)包括<code>authorization_code</code>的情况,当用户登录成功后,若选No,则会跳转到让用户Approve的页面让用户同意授权,
                    若选Yes,则在登录后不需要再让用户Approve同意授权(因为是受信任的)</p>
            </div>
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
            <button type="submit" class="btn btn-success">保存</button>
            <a href="../../client_details">取消</a>
        </div>
    </div>
    </form:form>
</div>


</body>
</html>
