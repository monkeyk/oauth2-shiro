/*
 * Copyright (c) 2013 Andaily Information Technology Co. Ltd
 * www.andaily.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Andaily Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with Andaily Information Technology Co. Ltd.
 */
package com.monkeyk.os.domain.oauth;

import com.monkeyk.os.infrastructure.DateUtils;
import org.apache.oltu.oauth2.common.domain.client.BasicClientInfo;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import java.util.Date;

/**
 * 15-6-12
 * <p/>
 * DBTable: oauth_client_details
 *
 * @author Shengzhao Li
 */
public class ClientDetails extends BasicClientInfo {


    private String resourceIds;

    private String scope;

    private String grantTypes;

    /*
   * Shiro roles
   * */
    private String roles;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private boolean trusted = false;

    private boolean archived = false;

    private Date createTime = DateUtils.now();

    public ClientDetails() {
    }


    public String resourceIds() {
        return resourceIds;
    }

    public ClientDetails resourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
        return this;
    }

    public String scope() {
        return scope;
    }

    public ClientDetails scope(String scope) {
        this.scope = scope;
        return this;
    }

    public String grantTypes() {
        return grantTypes;
    }

    public ClientDetails grantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
        return this;
    }

    public String roles() {
        return roles;
    }

    public ClientDetails roles(String roles) {
        this.roles = roles;
        return this;
    }

    public Integer accessTokenValidity() {
        return accessTokenValidity;
    }

    public ClientDetails accessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
        return this;
    }

    public Integer refreshTokenValidity() {
        return refreshTokenValidity;
    }

    public ClientDetails refreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
        return this;
    }

    public boolean trusted() {
        return trusted;
    }

    public ClientDetails trusted(boolean trusted) {
        this.trusted = trusted;
        return this;
    }

    public boolean archived() {
        return archived;
    }

    public ClientDetails archived(boolean archived) {
        this.archived = archived;
        return this;
    }

    public Date createTime() {
        return createTime;
    }

    public ClientDetails createTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public boolean supportRefreshToken() {
        return this.grantTypes != null && this.grantTypes.contains(GrantType.REFRESH_TOKEN.toString());
    }
}
