package com.monkeyk.os.oauth.authorize;

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.web.WebUtils;
import com.monkeyk.os.oauth.OAuthAuthxRequest;
import com.monkeyk.os.oauth.validator.AbstractClientDetailsValidator;
import com.monkeyk.os.oauth.validator.TokenClientDetailsValidator;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 2015/6/25
 * <p/>
 * Handle response_type = 'token'
 *
 * @author Shengzhao Li
 */
public class TokenAuthorizeHandler extends AbstractAuthorizeHandler {

    private static final Logger LOG = LoggerFactory.getLogger(TokenAuthorizeHandler.class);


    public TokenAuthorizeHandler(OAuthAuthxRequest oauthRequest, HttpServletResponse response) {
        super(oauthRequest, response);
    }

    @Override
    protected AbstractClientDetailsValidator getValidator() {
        return new TokenClientDetailsValidator(oauthRequest, false);
    }

    /*
    *  response token
    *
    *  If it is the first logged or first approval , always return newly AccessToken
    *  Always exclude refresh_token
    *
    * */
    @Override
    protected void handleResponse() throws OAuthSystemException, IOException {

        if (forceNewAccessToken()) {
            forceTokenResponse();
        } else {
            AccessToken accessToken = oauthService.retrieveAccessToken(clientDetails(), oauthRequest.getScopes(), false);

            if (accessToken.tokenExpired()) {
                expiredTokenResponse(accessToken);
            } else {
                normalTokenResponse(accessToken);
            }
        }
    }

    private void forceTokenResponse() throws OAuthSystemException {
        AccessToken accessToken = oauthService.retrieveNewAccessToken(clientDetails(), oauthRequest.getScopes());
        normalTokenResponse(accessToken);
    }

    private void normalTokenResponse(AccessToken accessToken) throws OAuthSystemException {

        final OAuthResponse oAuthResponse = createTokenResponse(accessToken, true);
        LOG.debug("'token' response: {}", oAuthResponse);

        WebUtils.writeOAuthQueryResponse(response, oAuthResponse);
    }

    private void expiredTokenResponse(AccessToken accessToken) throws OAuthSystemException {
        final ClientDetails clientDetails = clientDetails();
        LOG.debug("AccessToken {} is expired", accessToken);

        final OAuthResponse oAuthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                .setError(OAuthError.ResourceResponse.EXPIRED_TOKEN)
                .setErrorDescription("access_token '" + accessToken.tokenId() + "' expired")
                .setErrorUri(clientDetails.getRedirectUri())
                .buildJSONMessage();

        WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
    }

    private boolean forceNewAccessToken() {
        final ClientDetails clientDetails = clientDetails();
        if (clientDetails.trusted()) {
            return userFirstLogged;
        } else {
            return userFirstApproved;
        }
    }
}
