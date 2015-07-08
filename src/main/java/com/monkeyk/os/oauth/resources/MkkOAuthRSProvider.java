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

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.rsfilter.OAuthDecision;
import org.apache.oltu.oauth2.rsfilter.OAuthRSProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 2015/7/8
 *
 * @author Shengzhao Li
 */
public class MkkOAuthRSProvider implements OAuthRSProvider {

    private static final Logger LOG = LoggerFactory.getLogger(MkkOAuthRSProvider.class);

    @Override
    public OAuthDecision validateRequest(String rsId, String token, HttpServletRequest req) throws OAuthProblemException {
        LOG.debug("rsId: {},token: {},req: {}", new Object[]{rsId, token, req});

        return null;
    }
}
