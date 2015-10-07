package com.monkeyk.os.oauth.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 2015/9/29
 *
 * @author Shengzhao Li
 */
public class OAuth2AuthenticationException extends AuthenticationException {

    public OAuth2AuthenticationException() {
    }

    public OAuth2AuthenticationException(String message) {
        super(message);
    }

    public OAuth2AuthenticationException(Throwable cause) {
        super(cause);
    }

    public OAuth2AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
