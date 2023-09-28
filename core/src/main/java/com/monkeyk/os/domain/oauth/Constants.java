package com.monkeyk.os.domain.oauth;

/**
 * 2015/6/25
 * <p/>
 * 定义常量
 *
 * @author Shengzhao Li
 */
public interface Constants {


    String REQUEST_USERNAME = "username";
    String REQUEST_PASSWORD = "password";

    String REQUEST_USER_OAUTH_APPROVAL = "user_oauth_approval";

    String OAUTH_LOGIN_VIEW = "oauth_login";
    String OAUTH_APPROVAL_VIEW = "oauth_approval";

    /**
     * label: jwt
     *
     * @since 2.0.0
     */
    String JWT = "jwt";


    /**
     * Version
     * 与pom.xml中一致
     *
     * @since 2.0.0
     */
    String VERSION = "2.0.0";


    /**
     * Fixed  keyId
     *
     * @since 2.0.0
     */
    String DEFAULT_KEY_ID = "oauth2-shiro-keyid";


}
