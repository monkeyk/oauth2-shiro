package com.monkeyk.os.web.controller;

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.service.OauthService;
import com.monkeyk.os.web.WebUtils;
import com.monkeyk.os.web.oauth.OAuthAuthxRequest;
import com.monkeyk.os.web.oauth.OauthAuthorizeValidator;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        OAuthResponse oAuthResponse;
        try {
            OAuthAuthxRequest oauthRequest = new OAuthAuthxRequest(request);

            // Validating
            OauthAuthorizeValidator oauthAuthorizeValidator = new OauthAuthorizeValidator(oauthRequest);
            oAuthResponse = oauthAuthorizeValidator.validate();
            if (oAuthResponse != null) {
                LOG.debug("Validate OAuthAuthzRequest(client_id={}) failed", oauthRequest.getClientId());
                WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
                return;
            }

            boolean needApproval = false;
            //Checking login
            final Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()) {
                if (isPost(request)) {
                    //login flow
                    try {
                        UsernamePasswordToken token = createToken(request);
                        subject.login(token);
                        LOG.debug("Submit login successful");
                        needApproval = true;
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


            final ClientDetails clientDetails = oauthService.loadClientDetails(oauthRequest.getClientId());

            //Checking approval
            if (needApproval && !clientDetails.trusted()) {
                //go to approval
                LOG.debug("Go to oauth_approval, clientId: '{}'", clientDetails.getClientId());

                request.getRequestDispatcher("oauth_approval")
                        .forward(request, response);
                return;
            }

            if (isPost(request) && !clientDetails.trusted()) {
                //submit approval
                final String oauthApproval = request.getParameter("user_oauth_approval");
                if (!"true".equalsIgnoreCase(oauthApproval)) {
                    //Deny action
                    LOG.debug("User '{}' deny access", subject.getPrincipal());
                    responseApprovalDeny(clientDetails, oauthRequest, response);
                    return;
                }
            }

            // Dispatch to  'code' or 'token'
            if (oauthRequest.isCode()) {
                LOG.debug("Response to 'code' response_type");
                responseCode(clientDetails, oauthRequest, response);
            } else if (oauthRequest.isToken()) {
                LOG.debug("Response to 'token' response_type");
                responseToken(clientDetails, oauthRequest, response);
            } else {
                throw new IllegalStateException("Unsupport 'response_type': " + oauthRequest.getResponseType());
            }


        } catch (OAuthProblemException e) {
            oAuthResponse = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_FOUND)
                    .location(e.getRedirectUri())
                    .error(e)
                    .buildJSONMessage();
            WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
        }


    }

    private void responseToken(ClientDetails clientDetails, OAuthAuthxRequest oauthRequest, HttpServletResponse response) throws OAuthSystemException {
        AccessToken accessToken = oauthService.retrieveAccessToken(clientDetails, oauthRequest.getScopes(), false);

        if (accessToken.tokenExpired()) {
            LOG.debug("AccessToken {} is expired", accessToken);
            final OAuthResponse oAuthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                    .setError(OAuthError.ResourceResponse.EXPIRED_TOKEN)
                    .setErrorDescription("access_token '" + accessToken.tokenId() + "' expired")
                    .setErrorUri(clientDetails.getRedirectUri())
                    .buildJSONMessage();

            WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
        } else {
            final OAuthResponse oAuthResponse = OAuthASResponse
                    .tokenResponse(HttpServletResponse.SC_OK)
                    .location(clientDetails.getRedirectUri())
                    .setAccessToken(accessToken.tokenId())
                    .setExpiresIn(String.valueOf(accessToken.currentTokenExpiredSeconds()))
                    .setTokenType(accessToken.tokenType())
                    .buildQueryMessage();
            LOG.debug("Response 'token' is: {}", oAuthResponse);

            WebUtils.writeOAuthQueryResponse(response, oAuthResponse);
        }
    }


    private void responseCode(ClientDetails clientDetails, OAuthAuthxRequest oauthRequest, HttpServletResponse response) throws OAuthSystemException, IOException {
        final String authCode = oauthService.retrieveAuthCode(clientDetails);

        final OAuthResponse oAuthResponse = OAuthASResponse
                .authorizationResponse(oauthRequest.request(), HttpServletResponse.SC_OK)
                .location(clientDetails.getRedirectUri())
                .setCode(authCode)
                .buildQueryMessage();
        LOG.debug("Response 'code' is: {}", oAuthResponse);

        WebUtils.writeOAuthQueryResponse(response, oAuthResponse);
    }


    private void responseApprovalDeny(ClientDetails clientDetails, OAuthAuthxRequest oauthRequest, HttpServletResponse response) throws IOException, OAuthSystemException {

        final OAuthResponse oAuthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                .setError(OAuthError.CodeResponse.ACCESS_DENIED)
                .setErrorDescription("User denied access")
                .location(clientDetails.getRedirectUri())
                .setState(oauthRequest.getState())
                .buildQueryMessage();
        LOG.debug("Response 'ACCESS_DENIED' is: {}", oAuthResponse);

        WebUtils.writeOAuthQueryResponse(response, oAuthResponse);
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


    @RequestMapping(value = "oauth_approval")
    public String oauthApproval() {
        return "oauth/oauth_approval";
    }


}