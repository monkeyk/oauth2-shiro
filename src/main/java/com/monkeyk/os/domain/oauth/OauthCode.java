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
import com.monkeyk.os.infrastructure.DateUtils;

/**
 * 15-6-17
 *
 * @author Shengzhao Li
 */
public class OauthCode extends AbstractDomain {


    //Default expired: 600 s (10 min)
    public static final int DEFAULT_CODE_EXPIRED_SECONDS = 600;


    private String code;

    private String username;

    private String clientId;

    private int expiredSeconds = DEFAULT_CODE_EXPIRED_SECONDS;

    public OauthCode() {
    }

    public int expiredSeconds() {
        return expiredSeconds;
    }

    public OauthCode expiredSeconds(int expiredSeconds) {
        this.expiredSeconds = expiredSeconds;
        return this;
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

    public boolean expired() {
        if (this.expiredSeconds <= 0) {
            return false;
        }
        final long time = createTime.getTime() + (this.expiredSeconds * 1000);
        return time >= DateUtils.now().getTime();
    }
}
