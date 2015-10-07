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

/**
 * 2015/7/8
 *
 * @author Shengzhao Li
 */

public interface OAuthRSService {

    AccessToken loadAccessTokenByTokenId(String tokenId);

    ClientDetails loadClientDetails(String clientId, String resourceIds);

}