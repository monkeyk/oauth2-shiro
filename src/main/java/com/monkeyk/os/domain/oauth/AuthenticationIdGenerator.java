package com.monkeyk.os.domain.oauth;

/**
 * 15-6-20
 *
 * @author Shengzhao Li
 */

public interface AuthenticationIdGenerator {

    public String generate(String clientId, String username, String scope);

}