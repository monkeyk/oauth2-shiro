package com.monkeyk.os.service.dto;

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.infrastructure.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 2016/6/8
 *
 * @author Shengzhao Li
 */
public class ClientDetailsDto implements Serializable {
    private static final long serialVersionUID = 6642623244436765992L;


    private String resourceIds;

    private String scope;

    private String grantTypes;

    /*
   * Shiro roles
   * */
    private String roles;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private boolean trusted;

    private boolean archived;

    private String createTime;


    private String clientId;
    private String clientSecret;

    private String clientName;
    private String clientUri;

    private String clientIconUri;
    private String redirectUri;

    private String description;


    public ClientDetailsDto() {
    }


    public ClientDetailsDto(ClientDetails details) {
        this.clientId = details.getClientId();
        this.clientSecret = details.getClientSecret();
        this.clientName = details.getName();

        this.clientUri = details.getClientUri();
        this.clientIconUri = details.getIconUri();
        this.redirectUri = details.getRedirectUri();

        this.description = details.getDescription();
        this.createTime = DateUtils.toDateTime(details.createTime());
        this.archived = details.archived();

        this.trusted = details.trusted();
        this.refreshTokenValidity = details.refreshTokenValidity();
        this.accessTokenValidity = details.accessTokenValidity();

        this.roles = details.roles();
        this.grantTypes = details.grantTypes();
        this.scope = details.scope();

        this.resourceIds = details.resourceIds();
    }


    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public boolean isTrusted() {
        return trusted;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientUri() {
        return clientUri;
    }

    public void setClientUri(String clientUri) {
        this.clientUri = clientUri;
    }

    public String getClientIconUri() {
        return clientIconUri;
    }

    public void setClientIconUri(String clientIconUri) {
        this.clientIconUri = clientIconUri;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<ClientDetailsDto> toDtos(List<ClientDetails> list) {
        List<ClientDetailsDto> dtos = new ArrayList<>(list.size());
        for (ClientDetails clientDetails : list) {
            dtos.add(new ClientDetailsDto(clientDetails));
        }
        return dtos;
    }


    public boolean isContainsAuthorizationCode() {
        return this.grantTypes.contains("authorization_code");
    }

    public boolean isContainsPassword() {
        return this.grantTypes.contains("password");
    }

    public boolean isContainsImplicit() {
        return this.grantTypes.contains("implicit");
    }

    public boolean isContainsClientCredentials() {
        return this.grantTypes.contains("client_credentials");
    }

    public boolean isContainsRefreshToken() {
        return this.grantTypes.contains("refresh_token");
    }


}
