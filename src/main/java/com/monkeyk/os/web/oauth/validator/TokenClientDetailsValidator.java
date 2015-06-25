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

import com.monkeyk.os.domain.oauth.ClientDetails;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 15-6-13
 *
 * @author Shengzhao Li
 */
public class TokenClientDetailsValidator extends AbstractClientDetailsValidator {

    private static final Logger LOG = LoggerFactory.getLogger(TokenClientDetailsValidator.class);


    public TokenClientDetailsValidator(OAuthAuthzRequest oauthRequest) {
        super(oauthRequest);
    }

    /*
     * grant_type="implicit"   -> response_type="token"
     * ?response_type=token&scope=read,write&client_id=[client_id]&client_secret=[client_secret]&redirect_uri=[redirect_uri]
    * */
    @Override
    public OAuthResponse validateSelf(ClientDetails clientDetails) throws OAuthSystemException {

        //validate client_secret
        final String clientSecret = oauthRequest.getClientSecret();
        if (clientSecret == null || !clientSecret.equals(clientDetails.getClientSecret())) {
            return invalidClientSecretResponse();
        }

        //validate redirect_uri
        final String redirectURI = oauthRequest.getRedirectURI();
        if (redirectURI == null || !redirectURI.equals(clientDetails.getRedirectUri())) {
            LOG.debug("Invalid redirect_uri '{}' by response_type = 'code', client_id = '{}'", redirectURI, clientDetails.getClientId());
            return invalidRedirectUriResponse();
        }

        //validate scope
        final Set<String> scopes = oauthRequest.getScopes();
        if (scopes.isEmpty() || excludeScopes(scopes, clientDetails)) {
            return invalidScopeResponse();
        }


        return null;
    }

    private OAuthResponse invalidClientSecretResponse() throws OAuthSystemException {
        return OAuthResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                .setErrorDescription("Invalid client_secret by client_id '" + oauthRequest.getClientId() + "'")
                .buildJSONMessage();
    }


}
