package com.monkeyk.os.oauth;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.servlet.http.HttpServletRequest;

/**
 * 2015/7/3
 * <p/>
 * Ext  OAuthTokenRequest
 *
 * @author Shengzhao Li
 */
public class OAuthTokenxRequest extends OAuthTokenRequest {

    /**
     * Create an OAuth Token request from a given HttpSerlvetRequest
     *
     * @param request the httpservletrequest that is validated and transformed into the OAuth Token Request
     * @throws org.apache.oltu.oauth2.common.exception.OAuthSystemException  if an unexpected exception was thrown
     * @throws org.apache.oltu.oauth2.common.exception.OAuthProblemException if the request was not a valid Token request this exception is thrown.
     */
    public OAuthTokenxRequest(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
        super(request);
    }

    public HttpServletRequest request() {
        return this.request;
    }
}
