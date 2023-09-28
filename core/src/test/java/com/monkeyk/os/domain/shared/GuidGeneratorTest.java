package com.monkeyk.os.domain.shared;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @author Shengzhao Li
 */
public class GuidGeneratorTest {


    @Test
    void generate() {

        String uuid = GuidGenerator.generate();
        assertNotNull(uuid);
//        System.out.println(uuid);

    }


    @Test
    void nextSaltHex() {

        String salt = GuidGenerator.nextSaltHex();
        assertNotNull(salt);
        assertTrue(salt.length() > 8);
    }

    @Test
    public void testGenerateClientId() throws Exception {
        final String clientId = GuidGenerator.generateClientId();
        assertNotNull(clientId);
        assertTrue(clientId.length() == 20);
//        System.out.println(clientId);
    }

    @Test
    public void testGenerateClientSecret() throws Exception {
        final String clientSecret = GuidGenerator.generateClientSecret();
        assertNotNull(clientSecret);
        assertTrue(clientSecret.length() == 20);
//        System.out.println(clientSecret);
//
//        for (int i = 0; i < 5; i++) {
//            System.out.println(GuidGenerator.generateClientSecret());
//        }
    }
}