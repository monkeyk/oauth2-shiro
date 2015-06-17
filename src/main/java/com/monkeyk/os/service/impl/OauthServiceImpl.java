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
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 15-6-10
 *
 * @author Shengzhao Li
 */
@Service("oauthService")
public class OauthServiceImpl implements OauthService {


    @Autowired
    private OauthRepository oauthRepository;

    @Override
    public ClientDetails loadClientDetails(String clientId) {
        return oauthRepository.findClientDetails(clientId);
    }

    @Override
    public void saveAuthorizationCode(String authCode, ClientDetails clientDetails) {
        final String username = (String) SecurityUtils.getSubject().getPrincipal();
        OauthCode oauthCode = new OauthCode()
                .code(authCode).username(username)
                .clientId(clientDetails.getClientId());
        oauthRepository.saveOauthCode(oauthCode);
    }
}
