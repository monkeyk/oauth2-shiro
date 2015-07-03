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

import com.monkeyk.os.domain.oauth.*;
import com.monkeyk.os.service.OauthService;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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
    @Autowired
    private AuthenticationIdGenerator authenticationIdGenerator;
    @Autowired
    private OAuthIssuer oAuthIssuer;

    @Override
    public ClientDetails loadClientDetails(String clientId) {
        LOG.debug("Find ClientDetails by clientId: {}", clientId);
        return oauthRepository.findClientDetails(clientId);
    }

    @Override
    public OauthCode saveAuthorizationCode(String authCode, ClientDetails clientDetails) {
        final String username = currentUsername();
        OauthCode oauthCode = new OauthCode()
                .code(authCode).username(username)
                .clientId(clientDetails.getClientId());

        oauthRepository.saveOauthCode(oauthCode);
        LOG.debug("Save OauthCode: {}", oauthCode);
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
        if (oauthCode != null) {
            //Always delete exist
            LOG.debug("OauthCode ({}) is existed, remove it and create a new one", oauthCode);
            oauthRepository.deleteOauthCode(oauthCode);
        }
        //create a new one
        oauthCode = createOauthCode(clientDetails);

        return oauthCode.code();
    }

    @Override
    public AccessToken retrieveAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException {
        return retrieveAccessToken(clientDetails, scopes, clientDetails.supportRefreshToken());
    }

    @Override
    public AccessToken retrieveAccessToken(ClientDetails clientDetails, Set<String> scopes, boolean includeRefreshToken) throws OAuthSystemException {
        String scope = OAuthUtils.encodeScopes(scopes);
        final String username = currentUsername();
        final String clientId = clientDetails.getClientId();

        final String authenticationId = authenticationIdGenerator.generate(clientId, username, scope);

        AccessToken accessToken = oauthRepository.findAccessToken(clientId, username, authenticationId);
        if (accessToken == null) {
            accessToken = createAndSaveAccessToken(clientDetails, includeRefreshToken, username, authenticationId);
            LOG.debug("Create a new AccessToken: {}", accessToken);
        }

        return accessToken;
    }

    //Always return new AccessToken, exclude refreshToken
    @Override
    public AccessToken retrieveNewAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException {
        String scope = OAuthUtils.encodeScopes(scopes);
        final String username = currentUsername();
        final String clientId = clientDetails.getClientId();

        final String authenticationId = authenticationIdGenerator.generate(clientId, username, scope);

        AccessToken accessToken = oauthRepository.findAccessToken(clientId, username, authenticationId);
        if (accessToken != null) {
            LOG.debug("Delete existed AccessToken: {}", accessToken);
            oauthRepository.deleteAccessToken(accessToken);
        }
        accessToken = createAndSaveAccessToken(clientDetails, false, username, authenticationId);
        LOG.debug("Create a new AccessToken: {}", accessToken);

        return accessToken;
    }

    @Override
    public OauthCode loadOauthCode(String code, ClientDetails clientDetails) {
        final String clientId = clientDetails.getClientId();
        return oauthRepository.findOauthCode(code, clientId);
    }

    @Override
    public boolean removeOauthCode(String code, ClientDetails clientDetails) {
        final OauthCode oauthCode = loadOauthCode(code, clientDetails);
        final int rows = oauthRepository.deleteOauthCode(oauthCode);
        return rows > 0;
    }

    private AccessToken createAndSaveAccessToken(ClientDetails clientDetails, boolean includeRefreshToken, String username, String authenticationId) throws OAuthSystemException {
        AccessToken accessToken = new AccessToken()
                .clientId(clientDetails.getClientId())
                .username(username)
                .tokenId(oAuthIssuer.accessToken())
                .authenticationId(authenticationId)
                .updateByClientDetails(clientDetails);

        if (includeRefreshToken) {
            accessToken.refreshToken(oAuthIssuer.refreshToken());
        }

        this.oauthRepository.saveAccessToken(accessToken);
        LOG.debug("Save AccessToken: {}", accessToken);
        return accessToken;
    }

    private OauthCode createOauthCode(ClientDetails clientDetails) throws OAuthSystemException {
        OauthCode oauthCode;
        final String authCode = oAuthIssuer.authorizationCode();

        LOG.debug("Save authorizationCode '{}' of ClientDetails '{}'", authCode, clientDetails);
        oauthCode = this.saveAuthorizationCode(authCode, clientDetails);
        return oauthCode;
    }


}
