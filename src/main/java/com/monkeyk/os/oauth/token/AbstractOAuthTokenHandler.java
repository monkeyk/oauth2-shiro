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

import com.monkeyk.os.web.WebUtils;
import com.monkeyk.os.oauth.OAuthHandler;
import com.monkeyk.os.oauth.OAuthTokenxRequest;
import com.monkeyk.os.oauth.validator.AbstractClientDetailsValidator;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * 2015/7/3
 *
 * @author Shengzhao Li
 */
public abstract class AbstractOAuthTokenHandler extends OAuthHandler implements OAuthTokenHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractOAuthTokenHandler.class);


    protected OAuthTokenxRequest tokenRequest;
    protected HttpServletResponse response;

    @Override
    public final void handle(OAuthTokenxRequest tokenRequest, HttpServletResponse response) throws OAuthProblemException, OAuthSystemException {
        this.tokenRequest = tokenRequest;
        this.response = response;

        //validate
        if (validateFailed()) {
            return;
        }


        handleAfterValidation();
    }


    protected boolean validateFailed() throws OAuthSystemException {
        AbstractClientDetailsValidator validator = getValidator();
        LOG.debug("Use [{}] validate client: {}", validator, tokenRequest.getClientId());

        final OAuthResponse oAuthResponse = validator.validate();
        return checkAndResponseValidateFailed(oAuthResponse);
    }

    protected boolean checkAndResponseValidateFailed(OAuthResponse oAuthResponse) {
        if (oAuthResponse != null) {
            LOG.debug("Validate OAuthAuthzRequest(client_id={}) failed", tokenRequest.getClientId());
            WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
            return true;
        }
        return false;
    }

    protected abstract AbstractClientDetailsValidator getValidator();


    protected String clientId() {
        return tokenRequest.getClientId();
    }

    protected abstract void handleAfterValidation() throws OAuthProblemException, OAuthSystemException;


}
