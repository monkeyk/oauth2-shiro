package com.monkeyk.os.oauth;

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.shared.BeanProvider;
import com.monkeyk.os.service.OauthService;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * 2015/7/3
 * <p/>
 * 对OAUTH各种流程的操作进行抽象,
 * 将没用的行为(方法) 放于此
 *
 * @author Shengzhao Li
 */
public abstract class OAuthHandler {


    private static final Logger LOG = LoggerFactory.getLogger(OAuthHandler.class);

    protected transient OauthService oauthService = BeanProvider.getBean(OauthService.class);


    private ClientDetails clientDetails;


    protected ClientDetails clientDetails() {
        if (clientDetails == null) {
            final String clientId = clientId();
            clientDetails = oauthService.loadClientDetails(clientId);
            LOG.debug("Load ClientDetails: {} by clientId: {}", clientDetails, clientId);
        }
        return clientDetails;
    }


    /**
     * Create  AccessToken response
     *
     * @param accessToken AccessToken
     * @param queryOrJson True is QueryMessage, false is JSON message
     * @return OAuthResponse
     * @throws org.apache.oltu.oauth2.common.exception.OAuthSystemException
     */
    protected OAuthResponse createTokenResponse(AccessToken accessToken, boolean queryOrJson) throws OAuthSystemException {
        final ClientDetails tempClientDetails = clientDetails();

        final OAuthASResponse.OAuthTokenResponseBuilder builder = OAuthASResponse
                .tokenResponse(HttpServletResponse.SC_OK)
                .location(tempClientDetails.getRedirectUri())
                .setAccessToken(accessToken.tokenId())
                .setExpiresIn(String.valueOf(accessToken.currentTokenExpiredSeconds()))
                .setTokenType(accessToken.tokenType());

        final String refreshToken = accessToken.refreshToken();
        if (StringUtils.isNotEmpty(refreshToken)) {
            builder.setRefreshToken(refreshToken);
        }

        return queryOrJson ? builder.buildQueryMessage() : builder.buildJSONMessage();
    }


    protected abstract String clientId();

}
