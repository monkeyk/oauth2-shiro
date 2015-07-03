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
package com.monkeyk.os.web.oauth;

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.shared.BeanProvider;
import com.monkeyk.os.service.OauthService;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
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
public abstract class OAuthHandler {


    private static final Logger LOG = LoggerFactory.getLogger(OAuthHandler.class);

    protected transient OauthService oauthService = BeanProvider.getBean(OauthService.class);


    private ClientDetails clientDetails;


    protected ClientDetails clientDetails() {
        if (clientDetails == null) {
            final String clientId = clientId();
            clientDetails = oauthService.loadClientDetails(clientId);
            LOG.debug("Load ClientDetails: {} by clientId: {}", clientDetails, clientId);
        }
        return clientDetails;
    }


    protected OAuthResponse createTokenResponse(AccessToken accessToken) throws OAuthSystemException {
        final ClientDetails clientDetails = clientDetails();

        return OAuthASResponse
                .tokenResponse(HttpServletResponse.SC_OK)
                .location(clientDetails.getRedirectUri())
                .setAccessToken(accessToken.tokenId())
                .setExpiresIn(String.valueOf(accessToken.currentTokenExpiredSeconds()))
                .setTokenType(accessToken.tokenType())
                .buildQueryMessage();
    }


    protected abstract String clientId();

}
