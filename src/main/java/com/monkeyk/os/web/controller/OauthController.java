package com.monkeyk.os.web.controller;

import com.monkeyk.os.web.WebUtils;
import com.monkeyk.os.web.oauth.OauthAuthorizeValidator;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("oauth/")
public class OauthController {


    private static final Logger LOG = LoggerFactory.getLogger(OauthController.class);


//    @Autowired
//    private OauthService oauthService;

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

        OAuthResponse oAuthResponse;
        try {
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

            // Validating
            OauthAuthorizeValidator oauthAuthorizeValidator = new OauthAuthorizeValidator(oauthRequest);
            oAuthResponse = oauthAuthorizeValidator.validate();
            if (oAuthResponse != null) {
                LOG.debug("Validate OAuthAuthzRequest(client_id={}) failed", oauthRequest.getClientId());
                WebUtils.writeOAuthResponse(response, oAuthResponse);
                return;
            }

            //Checking login
            final Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()) {
                if (isPost(request)) {
                    //login flow
                    try {
                        UsernamePasswordToken token = createToken(request);
                        subject.login(token);
                        LOG.debug("Submit login successful");
                    } catch (Exception ex) {
                        //login failed
                        LOG.debug("Login failed, back to login page too");
                        request.setAttribute("oauth_login_error", true);
                        request.getRequestDispatcher("oauth_login")
                                .forward(request, response);
                        return;
                    }
                } else {
                    //go to login
                    LOG.debug("Forward to Oauth login by client_id '{}'", oauthRequest.getClientId());
                    request.getRequestDispatcher("oauth_login")
                            .forward(request, response);
                    return;
                }
            }

            // Dispatch to  'code' or 'token'
            LOG.debug("Generate code or token");


        } catch (OAuthProblemException e) {
            oAuthResponse = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_FOUND)
                    .location(e.getRedirectUri())
                    .error(e)
                    .buildJSONMessage();
            WebUtils.writeOAuthResponse(response, oAuthResponse);
        }


//        final String clientId = oauthRequest.getClientId();
//        final String clientSecret = oauthRequest.getClientSecret();
//        final Set<String> scopes = oauthRequest.getScopes();
//
//
//        final String grantType = oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE);
//        String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
//
//
////            validateRedirectionURI(oauthRequest);
//
//        OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
//        //build OAuth response
//        OAuthResponse resp = OAuthASResponse
//                .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
//                .setError(OAuthError.TokenResponse.INVALID_CLIENT)
//                .setErrorDescription(String.format("Invalid Client '%s'", oauthRequest.getClientId()))
//                .buildJSONMessage();


    }

    private UsernamePasswordToken createToken(HttpServletRequest request) {
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");
        return new UsernamePasswordToken(username, password);
    }

    private boolean isPost(HttpServletRequest request) {
        return RequestMethod.POST.name().equalsIgnoreCase(request.getMethod());
    }


    @RequestMapping(value = "oauth_login")
    public String oauthLogin() {
        return "oauth/oauth_login";
    }


}