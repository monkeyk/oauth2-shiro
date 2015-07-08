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

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.shared.BeanProvider;
import com.monkeyk.os.service.OAuthRSService;
import org.apache.oltu.oauth2.common.error.OAuthError;
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


    private transient OAuthRSService rsService = BeanProvider.getBean(OAuthRSService.class);

    @Override
    public OAuthDecision validateRequest(String rsId, String token, HttpServletRequest req) throws OAuthProblemException {
        LOG.debug("Call OAuthRSProvider, rsId: {},token: {},req: {}", new Object[]{rsId, token, req});

        AccessToken accessToken = rsService.loadAccessTokenByTokenId(token);
        if (accessToken == null || accessToken.tokenExpired()) {
            LOG.debug("Invalid access_token: {}, because it is null or expired", token);
            throw OAuthProblemException.error(OAuthError.TokenResponse.INVALID_GRANT)
                    .description("Invalid access_token: " + token);
        }

        ClientDetails clientDetails = rsService.loadClientDetailsByClientId(accessToken.clientId());
        if (clientDetails == null || clientDetails.archived()) {
            LOG.debug("Invalid ClientDetails: {} by client_id: {}", clientDetails, accessToken.clientId());
            throw OAuthProblemException.error(OAuthError.TokenResponse.INVALID_CLIENT)
                    .description("Invalid client by token: " + token);
        }




        return null;
    }
}
