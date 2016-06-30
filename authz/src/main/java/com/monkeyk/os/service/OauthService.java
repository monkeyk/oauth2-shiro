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
package com.monkeyk.os.service;

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.OauthCode;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.util.Set;

/**
 * 15-6-10
 *
 * @author Shengzhao Li
 */
public interface OauthService {

    ClientDetails loadClientDetails(String clientId);

    OauthCode saveAuthorizationCode(String authCode, ClientDetails clientDetails);

    String retrieveAuthCode(ClientDetails clientDetails) throws OAuthSystemException;

    AccessToken retrieveAccessToken(ClientDetails clientDetails, Set<String> scopes, boolean includeRefreshToken) throws OAuthSystemException;

    //Always return new AccessToken, exclude refreshToken
    AccessToken retrieveNewAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException;

    OauthCode loadOauthCode(String code, ClientDetails clientDetails);

    boolean removeOauthCode(String code, ClientDetails clientDetails);

    //Always return new AccessToken,  grant_type=authorization_code
    AccessToken retrieveAuthorizationCodeAccessToken(ClientDetails clientDetails, String code) throws OAuthSystemException;

    //grant_type=password AccessToken
    AccessToken retrievePasswordAccessToken(ClientDetails clientDetails, Set<String> scopes, String username) throws OAuthSystemException;

    //grant_type=client_credentials
    AccessToken retrieveClientCredentialsAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException;

    AccessToken loadAccessTokenByRefreshToken(String refreshToken, String clientId);

    AccessToken changeAccessTokenByRefreshToken(String refreshToken, String clientId) throws OAuthSystemException;

    boolean isExistedClientId(String clientId);
}
