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
import com.monkeyk.os.oauth.OAuthTokenxRequest;
import com.monkeyk.os.oauth.validator.AbstractClientDetailsValidator;
import com.monkeyk.os.oauth.validator.RefreshTokenClientDetailsValidator;
import com.monkeyk.os.web.WebUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2015/7/3
 * <p/>
 * grant_type=refresh_token
 *
 * @author Shengzhao Li
 */
public class RefreshTokenHandler extends AbstractOAuthTokenHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RefreshTokenHandler.class);


    @Override
    public boolean support(OAuthTokenxRequest tokenRequest) throws OAuthProblemException {
        final String grantType = tokenRequest.getGrantType();
        return GrantType.REFRESH_TOKEN.toString().equalsIgnoreCase(grantType);
    }

    /**
     * URL demo:
     * /oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=refresh_token&refresh_token=b36f4978-a172-4aa8-af89-60f58abe3ba1
     *
     * @throws org.apache.oltu.oauth2.common.exception.OAuthProblemException
     */
    @Override
    public void handleAfterValidation() throws OAuthProblemException, OAuthSystemException {

        final String refreshToken = tokenRequest.getRefreshToken();
        AccessToken accessToken = oauthService.changeAccessTokenByRefreshToken(refreshToken, tokenRequest.getClientId());

        final OAuthResponse tokenResponse = createTokenResponse(accessToken, false);

        LOG.debug("'refresh_token' response: {}", tokenResponse);
        WebUtils.writeOAuthJsonResponse(response, tokenResponse);

    }

    @Override
    protected AbstractClientDetailsValidator getValidator() {
        return new RefreshTokenClientDetailsValidator(tokenRequest);
    }

}
