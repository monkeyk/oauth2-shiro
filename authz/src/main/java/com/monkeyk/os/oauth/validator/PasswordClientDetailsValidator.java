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
package com.monkeyk.os.oauth.validator;

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.oauth.OAuthTokenxRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * 2015/7/5
 *
 * @author Shengzhao Li
 */
public class PasswordClientDetailsValidator extends AbstractOauthTokenValidator {

    private static final Logger LOG = LoggerFactory.getLogger(PasswordClientDetailsValidator.class);


    public PasswordClientDetailsValidator(OAuthTokenxRequest oauthRequest) {
        super(oauthRequest);
    }

    /*
    * /oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=password&scope=read,write&username=mobile&password=mobile
    * */
    @Override
    protected OAuthResponse validateSelf(ClientDetails clientDetails) throws OAuthSystemException {

        //validate grant_type
        final String grantType = grantType();
        if (invalidateGrantType(clientDetails, grantType)) {
            return invalidGrantTypeResponse(grantType);
        }

        //validate client_secret
        if (invalidateClientSecret(clientDetails)) {
            return invalidClientSecretResponse();
        }

        //validate scope
        if (invalidateScope(clientDetails)) {
            return invalidScopeResponse();
        }

        //validate username,password
        if (invalidUsernamePassword()) {
            return invalidUsernamePasswordResponse();
        }

        return null;
    }

    private boolean invalidateGrantType(ClientDetails clientDetails, String grantType) throws OAuthSystemException {
        if (!clientDetails.grantTypes().contains(grantType)) {
            LOG.debug("Invalid grant_type '{}', client_id = '{}'", grantType, clientDetails.getClientId());
            return true;
        }
        return false;
    }

    private boolean invalidateScope(ClientDetails clientDetails) throws OAuthSystemException {
        final Set<String> scopes = oauthRequest.getScopes();
        return scopes.isEmpty() || excludeScopes(scopes, clientDetails);
    }

    private boolean invalidateClientSecret(ClientDetails clientDetails) throws OAuthSystemException {
        final String clientSecret = oauthRequest.getClientSecret();
        if (clientSecret == null || !clientSecret.equals(clientDetails.getClientSecret())) {
            LOG.debug("Invalid client_secret '{}', client_id = '{}'", clientSecret, clientDetails.getClientId());
            return true;
        }
        return false;
    }


}
