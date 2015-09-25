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
package com.monkeyk.os.oauth.resources;

import org.apache.oltu.oauth2.rsfilter.OAuthClient;
import org.apache.oltu.oauth2.rsfilter.OAuthDecision;

import java.security.Principal;

/**
 * 2015/7/8
 *
 * @author Shengzhao Li
 */
public class MkkOAuthDecision implements OAuthDecision {


    private boolean authorized;

    private Principal principal;

    private OAuthClient oAuthClient;

    public MkkOAuthDecision() {
    }

    @Override
    public boolean isAuthorized() {
        return authorized;
    }

    @Override
    public Principal getPrincipal() {
        return principal;
    }

    @Override
    public OAuthClient getOAuthClient() {
        return oAuthClient;
    }

    public MkkOAuthDecision setPrincipal(Principal principal) {
        this.principal = principal;
        return this;
    }

    public MkkOAuthDecision setOAuthClient(OAuthClient oAuthClient) {
        this.oAuthClient = oAuthClient;
        return this;
    }

    public MkkOAuthDecision setAuthorized(boolean authorized) {
        this.authorized = authorized;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "authorized=" + authorized +
                ", principal=" + principal +
                ", oAuthClient=" + oAuthClient +
                '}';
    }
}
