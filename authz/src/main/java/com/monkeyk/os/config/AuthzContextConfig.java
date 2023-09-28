package com.monkeyk.os.config;

import com.monkeyk.os.domain.oauth.AuthenticationIdGenerator;
import com.monkeyk.os.domain.oauth.DefaultAuthenticationIdGenerator;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

/**
 * 2020/7/14
 * <p>
 * Replace authz-context.xml
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
@Configuration
public class AuthzContextConfig {


    /**
     * 事务配置
     */
    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }


    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }


    /**
     * AuthenticationId 的生成器
     */
    @Bean
    public AuthenticationIdGenerator authenticationIdGenerator() {
        return new DefaultAuthenticationIdGenerator();
    }

    /**
     * 默认使用MD5 OAuthIssuer, 生成随机值,如 access_token, refresh_token
     * 可根据需要扩展使用其他的实现
     */
    @Bean
    public OAuthIssuer oAuthIssuer() {
        MD5Generator md5Generator = new MD5Generator();
        return new OAuthIssuerImpl(md5Generator);
    }


}
