package com.monkeyk.os.infrastructure;

import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.testng.annotations.Test;

import java.security.Key;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * 2016/12/15
 *
 * @author Shengzhao Li
 */
public class Jose4JTest {


    /*
    * AES 加密与解密, 128位
    * */
    @Test
    public void aesEncryptDecrypt128() throws Exception {

        String keyText = "iue98623diDEs096";
        String data = "I am marico";
        Key key = new AesKey(keyText.getBytes());

        //加密
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        jwe.setKey(key);
        jwe.setPayload(data);

        String idToken = jwe.getCompactSerialization();
        assertNotNull(idToken);
        System.out.println(data + " idToken: " + idToken);

        //解密
        JsonWebEncryption jwe2 = new JsonWebEncryption();
        jwe2.setKey(key);
        jwe2.setCompactSerialization(idToken);

        final String payload = jwe2.getPayload();
        assertNotNull(payload);
        assertEquals(payload, data);

    }


    /*
    * AES 加密与解密, 256位
    * */
    @Test
    public void aesEncryptDecrypt256() throws Exception {

        String keyText = "iue98623diDEs096_8u@idls(*JKse09";
        String data = "I am marico";
        Key key = new AesKey(keyText.getBytes());

        //加密
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A256KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_256_CBC_HMAC_SHA_512);
        jwe.setKey(key);
        jwe.setPayload(data);

        String idToken = jwe.getCompactSerialization();
        assertNotNull(idToken);
        System.out.println(data + " idToken: " + idToken);

        //解密
        JsonWebEncryption jwe2 = new JsonWebEncryption();
        jwe2.setKey(key);
        jwe2.setCompactSerialization(idToken);

        final String payload = jwe2.getPayload();
        assertNotNull(payload);
        assertEquals(payload, data);

    }


}
