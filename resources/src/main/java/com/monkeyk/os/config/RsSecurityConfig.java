package com.monkeyk.os.config;

import com.monkeyk.os.oauth.shiro.OAuth2Filter;
import com.monkeyk.os.oauth.shiro.OAuth2JdbcRealm;
import com.monkeyk.os.oauth.shiro.OAuth2SubjectFactory;
import com.monkeyk.os.service.OAuthRSService;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 2023/9/21 11:30
 * <p>
 * replaced old rs-security.xml
 * <p>
 * <p>
 * RS(资源服务器)的安全配置, 基于SHIRO;
 * 这儿主要是对access_token进行消费(即使用access_token),
 * 将对access_token 进行效验, 以及通过access_token获取用户相关信息(如果支持)
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
@Configuration
public class RsSecurityConfig {


    @Autowired
    private OAuthRSService oAuthRSService;

    /**
     * 凭证(access_token)的匹配使用直接比较是否相等
     */
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        return new SimpleCredentialsMatcher();
    }


    @Bean
    public JdbcRealm jdbcRealm(DataSource dataSource) {
        OAuth2JdbcRealm jdbcRealm = new OAuth2JdbcRealm();
        jdbcRealm.setName("jdbcRealm");
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setCredentialsMatcher(credentialsMatcher());
        jdbcRealm.setPermissionsLookupEnabled(true);
        jdbcRealm.setRsService(oAuthRSService);
        return jdbcRealm;
    }


    /**
     * 若在集群环境下，推荐使用如 redis 之类支持集群的缓存实现
     */
    @Bean
    public CacheManager shiroCacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public OAuth2SubjectFactory subjectFactory() {
        return new OAuth2SubjectFactory();
    }

    @Bean
    public DefaultWebSecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(shiroCacheManager());
        securityManager.setSubjectFactory(subjectFactory());
        return securityManager;
    }

    /**
     * 对于每一个对外提供的Resource(资源), 都需要在此定义, 每个资源对应不同的URL pattern.
     * 且有唯一的 resource id,  具体的拦截处理见 OAuth2Filter.java 类
     * 可根据实际需要 进行扩展
     * <p>
     * Single resource
     * <p>
     * resourceId 在新的OAuth2实现中是可选的了 @since 2.0.0
     * 注意：不配置为bean
     */
    OAuth2Filter auth2Filter() {
        OAuth2Filter oAuth2Filter = new OAuth2Filter();
        oAuth2Filter.setResourceId("os-resource");
        oAuth2Filter.setRsService(oAuthRSService);
        return oAuth2Filter;
    }

    /**
     * mobile resource
     * 注意：不配置为bean
     */
    OAuth2Filter mobileOauth2Filter() {
        OAuth2Filter oAuth2Filter = new OAuth2Filter();
        oAuth2Filter.setResourceId("mobile-resource");
        oAuth2Filter.setRsService(oAuthRSService);
        return oAuth2Filter;
    }


    /**
     * SHIRO 核心配置 拦截器(或过滤器)
     * 扩展添加自定义的 Resource Filter(如 oauth2Filter) 用于实现对 access_token的校验与处理
     * 在filterChainDefinitions的配置中增加对具体 URL pattern的 OAUTH 保护拦截
     * 可根据实际需求进行扩展
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/index");
        bean.setUnauthorizedUrl("/unauthorized");

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("oauth", auth2Filter());
        filters.put("mOauth", mobileOauth2Filter());
//        filters.put("oauth2",xxxOauth2Filter());
//        filters.put("oauth3",yyyyyOauth2Filter());
        bean.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //#oauth
        filterChainDefinitionMap.put("/rs/**", "oauth");
        filterChainDefinitionMap.put("/mobile/**", "mOauth");
        // # everything else allow
        filterChainDefinitionMap.put("/**", "anon");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return bean;
    }


}
