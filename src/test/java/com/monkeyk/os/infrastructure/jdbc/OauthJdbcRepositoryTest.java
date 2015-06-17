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
package com.monkeyk.os.infrastructure.jdbc;

import com.monkeyk.os.ContextTest;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.OauthCode;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * 15-6-13
 *
 * @author Shengzhao Li
 */
public class OauthJdbcRepositoryTest extends ContextTest {

    @Autowired
    private OauthJdbcRepository oauthJdbcRepository;

    @Test
    public void testFindClientDetails() throws Exception {
        String clientId = "oaoedd";
        final ClientDetails clientDetails = oauthJdbcRepository.findClientDetails(clientId);
        assertNull(clientDetails);

        ClientDetails clientDetails1 = new ClientDetails();
        clientDetails1.setClientId(clientId);
        clientDetails1.setClientSecret("Ole397dde2");

        final int i = oauthJdbcRepository.saveClientDetails(clientDetails1);
        assertEquals(i, 1);

        final ClientDetails clientDetails2 = oauthJdbcRepository.findClientDetails(clientId);
        assertNotNull(clientDetails2);
        assertNotNull(clientDetails2.getClientId());

    }


    @Test
    public void saveOauthCode() throws Exception {

        final OAuthIssuerImpl oAuthIssuer = new OAuthIssuerImpl(new MD5Generator());
        OauthCode oauthCode = new OauthCode()
                .username("test")
                .code(oAuthIssuer.authorizationCode())
                .clientId("client+id");
        oauthJdbcRepository.saveOauthCode(oauthCode);


        final OauthCode oauthCode1 = oauthJdbcRepository.findOauthCode(oauthCode.code(), oauthCode.clientId());
        assertNotNull(oauthCode1);
        assertNotNull(oauthCode1.code());
        assertNotNull(oauthCode1.username());
        System.out.println(oauthCode1.code());

        final OauthCode oauthCode2 = oauthJdbcRepository.findOauthCodeByUsernameClientId(oauthCode.username(), oauthCode.clientId());
        assertNotNull(oauthCode2);


        final int i = oauthJdbcRepository.deleteOauthCode(oauthCode);
        assertEquals(i, 1);

        final OauthCode oauthCode3 = oauthJdbcRepository.findOauthCodeByUsernameClientId("add", "ddood");
        assertNull(oauthCode3);
    }
}
