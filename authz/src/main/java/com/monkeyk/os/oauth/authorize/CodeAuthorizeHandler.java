package com.monkeyk.os.oauth.authorize;

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.web.WebUtils;
import com.monkeyk.os.oauth.OAuthAuthxRequest;
import com.monkeyk.os.oauth.validator.AbstractClientDetailsValidator;
import com.monkeyk.os.oauth.validator.CodeClientDetailsValidator;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 2015/6/25
 * <p/>
 * Handle response_type = 'code'
 *
 * @author Shengzhao Li
 */
public class CodeAuthorizeHandler extends AbstractAuthorizeHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CodeAuthorizeHandler.class);


    public CodeAuthorizeHandler(OAuthAuthxRequest oauthRequest, HttpServletResponse response) {
        super(oauthRequest, response);
    }

    @Override
    protected AbstractClientDetailsValidator getValidator() {
        return new CodeClientDetailsValidator(oauthRequest);
    }


    //response code
    @Override
    protected void handleResponse() throws OAuthSystemException, IOException {
        final ClientDetails clientDetails = clientDetails();
        final String authCode = oauthService.retrieveAuthCode(clientDetails);

        final OAuthResponse oAuthResponse = OAuthASResponse
                .authorizationResponse(oauthRequest.request(), HttpServletResponse.SC_OK)
                .location(clientDetails.getRedirectUri())
                .setCode(authCode)
                .buildQueryMessage();
        LOG.debug(" 'code' response: {}", oAuthResponse);

        WebUtils.writeOAuthQueryResponse(response, oAuthResponse);
    }


}
