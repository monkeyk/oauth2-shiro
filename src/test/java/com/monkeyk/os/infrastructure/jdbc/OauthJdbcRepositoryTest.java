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
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

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
}
