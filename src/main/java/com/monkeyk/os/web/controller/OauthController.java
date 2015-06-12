package com.monkeyk.os.web.controller;

import com.monkeyk.os.service.OauthService;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("oauth/")
public class OauthController {


    private static final Logger LOG = LoggerFactory.getLogger(OauthController.class);


    @Autowired
    private OauthService oauthService;

    /**
     * Must handle the grant_type as follow:
     * grant_type="authorization_code" -> response_type="code"
     * ?response_type=code&scope=read,write&client_id=[client_id]&redirect_uri=[redirect_uri]&state=[state]
     * <p/>
     * grant_type="implicit"   -> response_type="token"
     * ?response_type=token&scope=read,write&client_id=[client_id]&client_secret=[client_secret]&redirect_uri=[redirect_uri]
     * <p/>
     * <p/>
     * Steps:
     * 1. Check client_details, if it is unavailable, return directly
     * 2. Validate ResponseType
     * 3.Checking login or not
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping("authorize")
    public void authorize(HttpServletRequest request, HttpServletResponse response) throws Exception {

        OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

        final String clientId = oauthRequest.getClientId();
        final String clientSecret = oauthRequest.getClientSecret();
        final Set<String> scopes = oauthRequest.getScopes();



        final String grantType = oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE);
        String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);


//            validateRedirectionURI(oauthRequest);

        OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
        //build OAuth response
        OAuthResponse resp = OAuthASResponse
                .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                .setErrorDescription(String.format("Invalid Client '%s'", oauthRequest.getClientId()))
                .buildJSONMessage();


    }


}