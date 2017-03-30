/*
 * Copyright (c) 2015 MONKEYK Information Technology Co. Ltd
 * www.monkeyk.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * MONKEYK Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with MONKEYK Information Technology Co. Ltd.
 */
package com.monkeyk.os.oauth.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2015/10/16
 * <p/>
 * A dispatcher for OAUTH credentials matcher when if the {@link org.apache.shiro.authc.AuthenticationToken} is {@link com.monkeyk.os.oauth.shiro.OAuth2Token} or not;
 * <p/>
 * Different  AuthenticationToken  that different  module(authz -> authzCredentialsMatcher, resources ->  resourcesCredentialsMatcher)
 * <p/>
 * Use it if  the project only one module (authz and resources in the module);
 * 如果不想将 authz 与 resources 模块分开成不同的模块 而是在同一个模块中, 则需要使用该对象.
 * <p/>
 * E.g.
 * <code>
 * <bean id="authzCredentialsMatcher" class="org.apache.shiro.authc.credential.HashedCredentialsMatcher">
 * <property name="hashAlgorithmName" value="MD5"/>
 * </bean>
 * <p/>
 * <bean id="resourcesCredentialsMatcher" class="org.apache.shiro.authc.credential.SimpleCredentialsMatcher"/>
 * <p/>
 * <bean class="com.monkeyk.os.oauth.shiro.OAuth2CredentialsMatcher" id="credentialsMatcher">
 * <property name="authzCredentialsMatcher" ref="authzCredentialsMatcher"/>
 * <property name="resourcesCredentialsMatcher" ref="resourcesCredentialsMatcher"/>
 * </bean>
 * <p/>
 * </code>
 * <p/>
 * 具体的配置请查看文章: https://andaily.com/blog/?p=712
 *
 * @author Shengzhao Li
 * @see com.monkeyk.os.oauth.shiro.OAuth2Token
 * @see org.apache.shiro.authc.AuthenticationToken
 */
public class OAuth2CredentialsMatcher implements CredentialsMatcher {

    private static final Logger LOG = LoggerFactory.getLogger(OAuth2CredentialsMatcher.class);

    // authz module
    private CredentialsMatcher authzCredentialsMatcher;
    //resources module
    private CredentialsMatcher resourcesCredentialsMatcher;

    public OAuth2CredentialsMatcher() {
    }


    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        LOG.debug("Do credentials match, token: {}, info: {}", token, info);

        if (token instanceof OAuth2Token) {
            LOG.debug("Call [resources] CredentialsMatcher: {}", resourcesCredentialsMatcher);
            return resourcesCredentialsMatcher.doCredentialsMatch(token, info);
        } else {
            LOG.debug("Call [authz] CredentialsMatcher: {}", authzCredentialsMatcher);
            return authzCredentialsMatcher.doCredentialsMatch(token, info);
        }

    }


    public void setAuthzCredentialsMatcher(CredentialsMatcher authzCredentialsMatcher) {
        this.authzCredentialsMatcher = authzCredentialsMatcher;
    }

    public void setResourcesCredentialsMatcher(CredentialsMatcher resourcesCredentialsMatcher) {
        this.resourcesCredentialsMatcher = resourcesCredentialsMatcher;
    }
}
