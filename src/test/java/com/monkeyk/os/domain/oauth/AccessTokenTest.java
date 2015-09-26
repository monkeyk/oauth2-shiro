package com.monkeyk.os.domain.oauth;

import com.monkeyk.os.infrastructure.DateUtils;
import org.testng.annotations.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Shengzhao Li
 */
public class AccessTokenTest {


    @Test
    public void decode() throws Exception {

        String url = "http://localhost:7777/spring-oauth-client/authorization_code_callback";
        final String decode = URLEncoder.encode(url, "UTF-8");
        System.out.println(decode);
    }


    @Test
    public void refreshTokenExpired() {


        AccessToken token = new AccessToken();

        token.createTime(DateUtils.getDate("2015-01-01 12:12:12", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        token.tokenExpiredSeconds(10);

        assertTrue(token.refreshTokenExpired());


        token.createTime(DateUtils.now());
        token.tokenExpiredSeconds(12);

        assertFalse(token.refreshTokenExpired());


        token.createTime(DateUtils.now());
        token.tokenExpiredSeconds(0);

        assertFalse(token.refreshTokenExpired());


    }

}