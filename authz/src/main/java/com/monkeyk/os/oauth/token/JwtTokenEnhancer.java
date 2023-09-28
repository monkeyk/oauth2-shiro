package com.monkeyk.os.oauth.token;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import static com.monkeyk.os.domain.oauth.Constants.DEFAULT_KEY_ID;

/**
 * 2023/9/26 15:22
 * <p>
 * JWT token 增强实现
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
@Component
public class JwtTokenEnhancer implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenEnhancer.class);


    /**
     * Jwt hamc key ; 长度至少32位
     * TODO: 不同的部署环境请使用不同的hmac key
     *
     * @since 2.0.0
     */
    @Value("${authz.token.jwt.hmac.key:Bl0depAUL2DRPZnR0DJThK9a9KSJF4Xr}")
    private String jwtHmacKey;


    private HmacKey hmacKey;


    public JwtTokenEnhancer() {
    }


    /**
     * enhance jti
     *
     * @param jti            token
     * @param subject        payload subject
     * @param audience       payload audience
     * @param expiredSeconds token expired seconds
     * @return jwt
     */
    public String enhance(String jti, String subject, String audience, int expiredSeconds) {
        return this.enhance(jti, subject, audience, expiredSeconds, Collections.emptyMap());
    }


    /**
     * enhance jti
     *
     * @param jti            token
     * @param subject        payload subject
     * @param audience       payload audience
     * @param expiredSeconds token expired seconds
     * @param extPayloadMap  ext  payload map
     * @return jwt
     */
    public String enhance(String jti, String subject, String audience, int expiredSeconds, Map<String, String> extPayloadMap) {

        JsonWebSignature jws = new JsonWebSignature();
        jws.setKeyIdHeaderValue(DEFAULT_KEY_ID);
        jws.setKey(hmacKey);
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);

        JwtClaims claims = new JwtClaims();
        claims.setSubject(subject);
        claims.setJwtId(jti);
//        claims.setIssuer("https://myoidc.com");
        claims.setIssuedAtToNow();

        NumericDate now = NumericDate.now();
        now.addSeconds(expiredSeconds);
        claims.setExpirationTime(now);
        claims.setAudience(audience);

        for (String key : extPayloadMap.keySet()) {
            claims.setStringClaim(key, extPayloadMap.get(key));
        }

        jws.setPayload(claims.toJson());

        try {
            return jws.getCompactSerialization();
        } catch (JoseException e) {
            throw new IllegalStateException("Jwt enhance error", e);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.hmacKey = new HmacKey(jwtHmacKey.getBytes(StandardCharsets.UTF_8));
        if (LOG.isDebugEnabled()) {
            LOG.debug("Initialized hmacKey: {}", this.hmacKey);
        }
    }
}
