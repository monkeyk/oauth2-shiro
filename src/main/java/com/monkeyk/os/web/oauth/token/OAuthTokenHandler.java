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
package com.monkeyk.os.web.oauth.token;

import com.monkeyk.os.web.oauth.OAuthTokenxRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;

import javax.servlet.http.HttpServletResponse;

/**
 * 2015/7/3
 *
 * @author Shengzhao Li
 */

public interface OAuthTokenHandler {


    boolean support(OAuthTokenxRequest tokenRequest) throws OAuthProblemException;

    void handle(OAuthTokenxRequest tokenRequest, HttpServletResponse response) throws OAuthProblemException;


}