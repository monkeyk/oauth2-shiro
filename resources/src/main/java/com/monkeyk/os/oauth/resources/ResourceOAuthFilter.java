package com.monkeyk.os.oauth.resources;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.apache.oltu.oauth2.rsfilter.OAuthDecision;
import org.apache.oltu.oauth2.rsfilter.OAuthRSProvider;
import org.apache.oltu.oauth2.rsfilter.OAuthUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * 2015/9/26
 * <p/>
 * <p/>
 * 针对每一个资源(resource-id) 的Filter
 * <p/>
 * 扩展至OLTU默认提供的 {@link org.apache.oltu.oauth2.rsfilter.OAuthFilter}
 * 将默认的从ServletContext加载的配置参数修改为单个Filter自定义配置
 * <p/>
 * 若系统中只有一个资源(resource-id),建议使用 {@link org.apache.oltu.oauth2.rsfilter.OAuthFilter}
 * 若系统中有多个资源(resource-id), 建议使用 ResourceOAuthFilter
 *
 * @author Shengzhao Li
 * @see org.apache.oltu.oauth2.rsfilter.OAuthFilter
 */
public class ResourceOAuthFilter implements Filter {


    public static final String OAUTH_RS_PROVIDER_CLASS = "oauth.rs.provider-class";

    public static final String RS_REALM = "oauth.rs.realm";
    public static final String RS_REALM_DEFAULT = "OAuth Protected Service";

    public static final String RS_TOKENS = "oauth.rs.tokens";
    public static final ParameterStyle RS_TOKENS_DEFAULT = ParameterStyle.HEADER;

    private static final String TOKEN_DELIMITER = ",";

    private String realm;

    private OAuthRSProvider provider;

    private ParameterStyle[] parameterStyles;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        provider = OAuthUtils
                .initiateServletContext(filterConfig.getServletContext(), OAUTH_RS_PROVIDER_CLASS,
                        OAuthRSProvider.class);
        //resource-id
        realm = filterConfig.getInitParameter(RS_REALM);
        if (OAuthUtils.isEmpty(realm)) {
            realm = RS_REALM_DEFAULT;
        }

        //token parameter style
        String parameterStylesString = filterConfig.getInitParameter(RS_TOKENS);
        if (OAuthUtils.isEmpty(parameterStylesString)) {
            parameterStyles = new ParameterStyle[]{RS_TOKENS_DEFAULT};
        } else {
            String[] parameters = parameterStylesString.split(TOKEN_DELIMITER);
            if (parameters.length > 0) {
                parameterStyles = new ParameterStyle[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    ParameterStyle tempParameterStyle = ParameterStyle.valueOf(parameters[i]);
                    if (tempParameterStyle != null) {
                        parameterStyles[i] = tempParameterStyle;
                    } else {
                        throw new ServletException("Incorrect ParameterStyle: " + parameters[i]);
                    }
                }
            }
        }

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        try {

            // Make an OAuth Request out of this servlet request
            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(req,
                    parameterStyles);

            // Get the access token
            String accessToken = oauthRequest.getAccessToken();

            final OAuthDecision decision = provider.validateRequest(realm, accessToken, req);

            final Principal principal = decision.getPrincipal();

            HttpServletRequest newRequest = new HttpServletRequestWrapper((HttpServletRequest) request) {
                @Override
                public String getRemoteUser() {
                    return principal != null ? principal.getName() : null;
                }

                @Override
                public Principal getUserPrincipal() {
                    return principal;
                }

            };

            newRequest.setAttribute(OAuth.OAUTH_CLIENT_ID, decision.getOAuthClient().getClientId());

            chain.doFilter(newRequest, response);

        } catch (OAuthSystemException e1) {
            throw new ServletException(e1);
        } catch (OAuthProblemException e) {
            respondWithError(res, e);
        }

    }


    @Override
    public void destroy() {
        //nothing
    }

    /*
     * 默认的 ERROR 返回信息在header中,
     * 可根据实际需要修改为JSON或XML数据格式返回
     */
    private void respondWithError(HttpServletResponse resp, OAuthProblemException error)
            throws IOException, ServletException {

        OAuthResponse oauthResponse;

        try {
            if (OAuthUtils.isEmpty(error.getError())) {
                oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setRealm(realm)
                        .buildHeaderMessage();

            } else {

                int responseCode = 401;
                if (error.getError().equals(OAuthError.CodeResponse.INVALID_REQUEST)) {
                    responseCode = 400;
                } else if (error.getError().equals(OAuthError.ResourceResponse.INSUFFICIENT_SCOPE)) {
                    responseCode = 403;
                }

                oauthResponse = OAuthRSResponse
                        .errorResponse(responseCode)
                        .setRealm(realm)
                        .setError(error.getError())
                        .setErrorDescription(error.getDescription())
                        .setErrorUri(error.getUri())
                        .buildHeaderMessage();
            }
            resp.addHeader(OAuth.HeaderType.WWW_AUTHENTICATE,
                    oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
            resp.sendError(oauthResponse.getResponseStatus());
        } catch (OAuthSystemException e) {
            throw new ServletException(e);
        }
    }


}
