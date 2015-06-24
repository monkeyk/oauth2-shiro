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
package com.monkeyk.os.web.oauth.validator;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * 15-6-13
 *
 * @author Shengzhao Li
 */
public class OauthAuthorizeValidator {

    private static final Logger LOG = LoggerFactory.getLogger(OauthAuthorizeValidator.class);

    protected OAuthAuthzRequest oauthRequest;

    protected OAuthResponse oAuthResponse;

    public OauthAuthorizeValidator(OAuthAuthzRequest oauthRequest) {
        this.oauthRequest = oauthRequest;
    }

    public OAuthResponse validate() throws OAuthSystemException {

        //If it is auth used
        if (oauthRequest.isClientAuthHeaderUsed()) {
            buildAuthHeaderUsedResponse();
            return oAuthResponse;
        }

        //validate client details
        if (isCode()) {
            validateCodeClientDetails();
        } else if (isToken()) {
            validateTokenClientDetails();
        } else {
            LOG.debug("Unsupport response_type '{}' by client_id '{}'", responseType(), oauthRequest.getClientId());
            this.oAuthResponse = OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE)
                    .setErrorDescription("Unsupport response_type '" + responseType() + "'")
                    .buildJSONMessage();
        }

        return oAuthResponse;
    }

    private void buildAuthHeaderUsedResponse() throws OAuthSystemException {
        this.oAuthResponse = OAuthResponse.status(HttpServletResponse.SC_FOUND)
                .location(oauthRequest.getRedirectURI())
                .setParam("client_id", oauthRequest.getClientId())
                .buildJSONMessage();
    }


    protected void validateTokenClientDetails() throws OAuthSystemException {
        TokenClientDetailsValidator validator = new TokenClientDetailsValidator(oauthRequest);
        this.oAuthResponse = validator.validate();
    }

    protected void validateCodeClientDetails() throws OAuthSystemException {
        CodeClientDetailsValidator validator = new CodeClientDetailsValidator(oauthRequest);
        this.oAuthResponse = validator.validate();
    }


    /*
   * Available values;
   * authorization_code
   * implicit
   * */
//    protected String grantType() {
//        return oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE);
//    }

    /*
    * Available values:
    * code
    * token
    * */
    protected String responseType() {
        return oauthRequest.getResponseType();
    }

    protected boolean isCode() {
        return ResponseType.CODE.name().equalsIgnoreCase(responseType());
    }

    protected boolean isToken() {
        return ResponseType.TOKEN.name().equalsIgnoreCase(responseType());
    }
}
