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

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * 15-6-20
 *
 * @author Shengzhao Li
 */
public class AuthenticationIdGeneratorTest {

    @Test
    public void testGenerate() throws Exception {

        DefaultAuthenticationIdGenerator generator = new DefaultAuthenticationIdGenerator();
        final String generate = generator.generate("clientid", "username", "authid");

        assertNotNull(generate);
        System.out.println(generate);

    }
}
