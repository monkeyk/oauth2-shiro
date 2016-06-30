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

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.web.WebUtils;
import com.monkeyk.os.oauth.OAuthTokenxRequest;
import com.monkeyk.os.oauth.validator.AbstractClientDetailsValidator;
import com.monkeyk.os.oauth.validator.AuthorizationCodeClientDetailsValidator;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2015/7/3
 * <p/>
 * grant_type=authorization_code
 *
 * @author Shengzhao Li
 */
public class AuthorizationCodeTokenHandler extends AbstractOAuthTokenHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationCodeTokenHandler.class);

    @Override
    public boolean support(OAuthTokenxRequest tokenRequest) throws OAuthProblemException {
        final String grantType = tokenRequest.getGrantType();
        return GrantType.AUTHORIZATION_CODE.toString().equalsIgnoreCase(grantType);
    }

    /*
    *
    * /oauth/token?client_id=unity-client&client_secret=unity&grant_type=authorization_code&code=zLl170&redirect_uri=redirect_uri
    * */
    @Override
    public void handleAfterValidation() throws OAuthProblemException, OAuthSystemException {

        //response token, always new
        responseToken();

        //remove code lastly
        removeCode();
    }

    private void removeCode() {
        final String code = tokenRequest.getCode();
        final boolean result = oauthService.removeOauthCode(code, clientDetails());
        LOG.debug("Remove code: {} result: {}", code, result);
    }

    private void responseToken() throws OAuthSystemException {
        AccessToken accessToken = oauthService.retrieveAuthorizationCodeAccessToken(clientDetails(), tokenRequest.getCode());
        final OAuthResponse tokenResponse = createTokenResponse(accessToken, false);

        LOG.debug("'authorization_code' response: {}", tokenResponse);
        WebUtils.writeOAuthJsonResponse(response, tokenResponse);
    }

    @Override
    protected AbstractClientDetailsValidator getValidator() {
        return new AuthorizationCodeClientDetailsValidator(tokenRequest);
    }

}
