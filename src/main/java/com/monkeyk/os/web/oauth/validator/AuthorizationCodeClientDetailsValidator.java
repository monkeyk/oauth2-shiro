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
import com.monkeyk.os.web.oauth.OAuthTokenxRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2015/7/3
 *
 * @author Shengzhao Li
 */
public class AuthorizationCodeClientDetailsValidator extends AbstractClientDetailsValidator {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationCodeClientDetailsValidator.class);


    public AuthorizationCodeClientDetailsValidator(OAuthTokenxRequest oauthRequest) {
        super(oauthRequest);
    }

    /**
     * /oauth/token?client_id=unity-client&client_secret=unity&grant_type=authorization_code&code=zLl170&redirect_uri=redirect_uri
     */
    @Override
    protected OAuthResponse validateSelf(ClientDetails clientDetails) throws OAuthSystemException {

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

        //validate code


        return null;
    }
}
