package com.monkeyk.os.config;

import com.monkeyk.os.infrastructure.shiro.MkkJdbcRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 2020/7/14
 * <p>
 * Replace authz-security.xml
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
@Configuration
public class AuthzSecurityConfig {

    /*
     *  这是一个标准的 SHIRO 安全配置
     注意OAuth的URL配置:   /oauth/** = anon
     *
     * */

    /**
     * 存储凭证（如密码）的加密算法（如 MD5,SHA-256,SHA-384,SHA-512）
     * 默认 SHA-256
     * <p>
     * 若是旧版本升级后继续使用MD5，请将配置值更新为MD5
     * <code>
     * authz.store.credentials.alg=MD5
     * </code>
     *
     * @since 2.0.0
     */
    @Value("${authz.store.credentials.alg:" + Sha256Hash.ALGORITHM_NAME + "}")
    private String storeCredentialsAlg;

    /**
     * 使用指定算法 进行密码的加密与匹配
     */
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(this.storeCredentialsAlg);
//        credentialsMatcher.setStoredCredentialsHexEncoded(false);
        return credentialsMatcher;
    }

    /**
     * 扩展的 SHIRO Realm
     * 使用JDBC实现, 并添加 逻辑删除 (archived = 0) 的处理
     */
    @Bean
    public AuthorizingRealm jdbcRealm(DataSource dataSource) {
        MkkJdbcRealm realm = new MkkJdbcRealm();
        realm.setName("jdbcRealm");
        realm.setDataSource(dataSource);
        realm.setCredentialsMatcher(credentialsMatcher());
        realm.setPermissionsLookupEnabled(true);
        return realm;
    }

    /**
     * 使用基于内存的缓存 SHIRO 相关数据
     */
    @Bean
    public AbstractCacheManager shiroCacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    /**
     * SHIRO SecurityManager 配置
     */
    @Bean
    public DefaultWebSecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(shiroCacheManager());
        return securityManager;
    }


    /**
     * SHIRO安全机制拦截器 Filter实现, 注意id必须与 web.xml 中的 shiroFilter 一致
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setLoginUrl("/login");
        factoryBean.setSuccessUrl("/index");
        factoryBean.setUnauthorizedUrl("/unauthorized");
        //权限控制, map必须要有顺序
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/favicon.ico", "anon");
        map.put("/css/**", "anon");
        map.put("/js/**", "anon");
        map.put("/html/**", "anon");
        map.put("/login", "anon");
        map.put("/unauthorized", "anon");
        // # OAuth anon
        map.put("/oauth/**", "anon");
        map.put("/users/**", "anon");
        map.put("/client_details*", "anon");
        map.put("/client_details/**", "anon");
        map.put("/logout", "logout");
        //# admin role
        map.put("/admin/**", "authc, roles[\"Admin\"]");
        // #user permissions
        map.put("/user/list", "authc, perms[\"user:list\"]");
        map.put("/user/create", "authc, perms[\"user:create\"]");
        // # everything else requires authentication:
        map.put("/**", "authc");
        factoryBean.setFilterChainDefinitionMap(map);
//        factoryBean.setFilterChainDefinitions("                /favicon.ico = anon\n" +
//                "                /resources/** = anon\n" +
//                "                /statics/** = anon\n" +
//                "                /login = anon\n" +
//                "                /unauthorized = anon\n" +
//                "                # OAuth anon\n" +
//                "                /oauth/** = anon\n" +
//                "                /users/** = anon\n" +
//                "                /client_details* = anon\n" +
//                "                /client_details/** = anon\n" +
//                "                /logout = logout\n" +
//                "                # admin role\n" +
//                "                /admin/** = authc, roles[\"Admin\"]\n" +
//                "                #user permissions\n" +
//                "                /user/list = authc, perms[\"user:list\"]\n" +
//                "                /user/create = authc, perms[\"user:create\"]\n" +
//                "                # everything else requires authentication:\n" +
//                "                /** = authc");
        return factoryBean;
    }


}
