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


    public MkkOAuthDecision() {
    }

    @Override
    public boolean isAuthorized() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Principal getPrincipal() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public OAuthClient getOAuthClient() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
