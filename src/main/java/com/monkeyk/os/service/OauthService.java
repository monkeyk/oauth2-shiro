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

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.OauthCode;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * 15-6-10
 *
 * @author Shengzhao Li
 */
public interface OauthService {

    ClientDetails loadClientDetails(String clientId);

    OauthCode saveAuthorizationCode(String authCode, ClientDetails clientDetails);

    String retrieveAuthCode(ClientDetails clientDetails) throws OAuthSystemException;
}
