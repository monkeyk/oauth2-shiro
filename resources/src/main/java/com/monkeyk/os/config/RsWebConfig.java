package com.monkeyk.os.config;

import com.monkeyk.os.web.context.MkkCharacterEncodingFilter;
import com.monkeyk.os.web.context.OAuthShiroHandlerExceptionResolver;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 2023/9/21 12:01
 * <p>
 * replaced web.xml, mkk-servlet.xml
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
@Configuration
public class RsWebConfig {


    /**
     * 字符编码配置 UTF-8
     */
    @Bean
    public FilterRegistrationBean<MkkCharacterEncodingFilter> encodingFilter() {
        FilterRegistrationBean<MkkCharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MkkCharacterEncodingFilter());
        registrationBean.addUrlPatterns("/*");
        //值越小越靠前
        registrationBean.setOrder(1);
        return registrationBean;
    }


    /**
     * 异常处理配置
     */
    @Bean
    public HandlerExceptionResolver handlerExceptionResolver() {
        return new OAuthShiroHandlerExceptionResolver();
    }


    /**
     * Shiro AOP
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * Enable Shiro Annotations for Spring-configured beans.  Only run after
     * the lifecycleBeanProcessor has run:
     */
//    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
//        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        autoProxyCre
//        return autoProxyCreator;
//    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        sourceAdvisor.setSecurityManager(securityManager);
        return sourceAdvisor;
    }


}
