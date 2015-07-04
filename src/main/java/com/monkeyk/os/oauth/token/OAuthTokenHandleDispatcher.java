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
package com.monkeyk.os.oauth.token;

import com.monkeyk.os.oauth.OAuthTokenxRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 2015/7/3
 *
 * @author Shengzhao Li
 */
public class OAuthTokenHandleDispatcher {

    private static final Logger LOG = LoggerFactory.getLogger(OAuthTokenHandleDispatcher.class);

    private List<OAuthTokenHandler> handlers = new ArrayList<>();

    private final OAuthTokenxRequest tokenRequest;
    private final HttpServletResponse response;

    public OAuthTokenHandleDispatcher(OAuthTokenxRequest tokenRequest, HttpServletResponse response) {
        this.tokenRequest = tokenRequest;
        this.response = response;

        initialHandlers();
    }

    private void initialHandlers() {
        handlers.add(new AuthorizationCodeTokenHandler());
        handlers.add(new PasswordTokenHandler());
        handlers.add(new RefreshTokenHandler());

        handlers.add(new ClientCredentialsTokenHandler());

        LOG.debug("Initialed '{}' OAuthTokenHandler(s): {}", handlers.size(), handlers);
    }


    public void dispatch() throws OAuthProblemException, OAuthSystemException {
        for (OAuthTokenHandler handler : handlers) {
            if (handler.support(tokenRequest)) {
                LOG.debug("Found '{}' handle OAuthTokenxRequest: {}", handler, tokenRequest);
                handler.handle(tokenRequest, response);
                return;
            }
        }
        throw new IllegalStateException("Not found 'OAuthTokenHandler' to handle OAuthTokenxRequest: " + tokenRequest);
    }
}
