package com.monkeyk.os.web;

import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.junit.jupiter.api.Test;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class WebUtilsTest {


    @Test
    public void testDecodeHeader() throws Exception {

        String text = "Basic dXNlcm5hbWU6cGFzc3dvcmQ=";

        final String[] strings = OAuthUtils.decodeClientAuthenticationHeader(text);
        assertNotNull(strings);

        System.out.println(Arrays.toString(strings));
    }

}