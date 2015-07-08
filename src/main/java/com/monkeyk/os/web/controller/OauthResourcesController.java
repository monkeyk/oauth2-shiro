package com.monkeyk.os.web.controller;

import com.monkeyk.os.web.WebUtils;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Custom resource API
 * Protect by Oauth
 *
 * @author Shengzhao Li
 */
@Controller
public class OauthResourcesController {


    private static final Logger LOG = LoggerFactory.getLogger(OauthResourcesController.class);


    /**
     * Return username API
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping("user_name")
    public void username(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            OAuthAccessResourceRequest resourceRequest = new OAuthAccessResourceRequest(request);

            final String accessToken = resourceRequest.getAccessToken();


        } catch (OAuthProblemException e) {
            //exception
            OAuthResponse oAuthResponse = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_FOUND)
                    .location(e.getRedirectUri())
                    .error(e)
                    .buildJSONMessage();
            WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
        }


    }


}