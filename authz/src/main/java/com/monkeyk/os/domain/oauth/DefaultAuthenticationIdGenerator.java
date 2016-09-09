package com.monkeyk.os.domain.oauth;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 15-6-20
 * <p/>
 * AuthenticationId 的生成器
 * MD5加密
 *
 * @author Shengzhao Li
 */
public class DefaultAuthenticationIdGenerator implements AuthenticationIdGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAuthenticationIdGenerator.class);

    public DefaultAuthenticationIdGenerator() {
    }


    public String generate(String clientId, String username, String scope) {
        Map<String, String> map = new HashMap<>();
        map.put(OAuth.OAUTH_CLIENT_ID, clientId);
        //check it is client only
        if (!clientId.equals(username)) {
            map.put(OAuth.OAUTH_USERNAME, username);
        }
        if (!OAuthUtils.isEmpty(scope)) {
            map.put(OAuth.OAUTH_SCOPE, scope);
        }

        return digest(map);
    }


    protected String digest(Map<String, String> map) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn("Digest error", e);
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(map.toString().getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn("Encoding error", e);
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }

}
