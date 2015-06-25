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

import com.monkeyk.os.domain.AbstractDomain;

/**
 * 15-6-17
 *
 * @author Shengzhao Li
 */
public class OauthCode extends AbstractDomain {

    private String code;

    private String username;

    private String clientId;

    public OauthCode() {
    }


    public String code() {
        return code;
    }

    public OauthCode code(String code) {
        this.code = code;
        return this;
    }

    public String username() {
        return username;
    }

    public OauthCode username(String username) {
        this.username = username;
        return this;
    }

    public String clientId() {
        return clientId;
    }

    public OauthCode clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

}
