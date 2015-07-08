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
package com.monkeyk.os.domain.oauth;

import com.monkeyk.os.domain.shared.Repository;

/**
 * 15-6-13
 *
 * @author Shengzhao Li
 */
public interface OauthRepository extends Repository {

    ClientDetails findClientDetails(String clientId);

    int saveClientDetails(ClientDetails clientDetails);

    int saveOauthCode(OauthCode oauthCode);

    OauthCode findOauthCode(String code, String clientId);

    OauthCode findOauthCodeByUsernameClientId(String username, String clientId);

    int deleteOauthCode(OauthCode oauthCode);

    AccessToken findAccessToken(String clientId, String username, String authenticationId);

    int deleteAccessToken(AccessToken accessToken);

    int saveAccessToken(AccessToken accessToken);

    AccessToken findAccessTokenByRefreshToken(String refreshToken, String clientId);

    AccessToken findAccessTokenByTokenId(String tokenId);
}
