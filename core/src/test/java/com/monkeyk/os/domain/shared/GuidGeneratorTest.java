package com.monkeyk.os.domain.shared;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/*
  * @author Shengzhao Li
  */
public class GuidGeneratorTest {

    @Test
    public void testGenerateClientId() throws Exception {
        final String clientId = GuidGenerator.generateClientId();
        assertNotNull(clientId);
        assertTrue(clientId.length() == 20);
        System.out.println(clientId);
    }

    @Test
    public void testGenerateClientSecret() throws Exception {
        final String clientSecret = GuidGenerator.generateClientSecret();
        assertNotNull(clientSecret);
        assertTrue(clientSecret.length() == 20);
        System.out.println(clientSecret);

        for (int i = 0; i < 5; i++) {
            System.out.println(GuidGenerator.generateClientSecret());
        }
    }
}