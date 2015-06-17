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
package com.monkeyk.os.service.impl;

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.OauthCode;
import com.monkeyk.os.domain.oauth.OauthRepository;
import com.monkeyk.os.service.OauthService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 15-6-10
 *
 * @author Shengzhao Li
 */
@Service("oauthService")
public class OauthServiceImpl implements OauthService {

    private static final Logger LOG = LoggerFactory.getLogger(OauthServiceImpl.class);


    @Autowired
    private OauthRepository oauthRepository;

    @Override
    public ClientDetails loadClientDetails(String clientId) {
        return oauthRepository.findClientDetails(clientId);
    }

    @Override
    public OauthCode saveAuthorizationCode(String authCode, ClientDetails clientDetails) {
        final String username = currentUsername();
        OauthCode oauthCode = new OauthCode()
                .code(authCode).username(username)
                .clientId(clientDetails.getClientId());

        oauthRepository.saveOauthCode(oauthCode);
        return oauthCode;
    }

    private String currentUsername() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    @Override
    public String retrieveAuthCode(ClientDetails clientDetails) throws OAuthSystemException {
        final String clientId = clientDetails.getClientId();
        final String username = currentUsername();

        OauthCode oauthCode = oauthRepository.findOauthCodeByUsernameClientId(username, clientId);
        if (oauthCode == null) {
            oauthCode = createOauthCode(clientDetails);
        } else if (oauthCode.expired()) {
            LOG.debug("OauthCode ({}) is expired, remove it and create a new one", oauthCode);
            oauthRepository.deleteOauthCode(oauthCode);

            oauthCode = createOauthCode(clientDetails);
        }

        return oauthCode.code();
    }

    private OauthCode createOauthCode(ClientDetails clientDetails) throws OAuthSystemException {
        OauthCode oauthCode;
        final OAuthIssuerImpl oAuthIssuer = retrieveOAuthIssuer();
        final String authCode = oAuthIssuer.authorizationCode();

        LOG.debug("Save authorizationCode '{}' of ClientDetails '{}'", authCode, clientDetails);
        oauthCode = this.saveAuthorizationCode(authCode, clientDetails);
        return oauthCode;
    }

    private OAuthIssuerImpl retrieveOAuthIssuer() {
        return new OAuthIssuerImpl(new MD5Generator());
    }

}
