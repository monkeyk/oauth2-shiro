package com.monkeyk.os.oauth.shiro;

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.infrastructure.shiro.MkkJdbcRealm;
import com.monkeyk.os.service.OAuthRSService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * 2015/9/29
 * <p/>
 * Ext. {@link org.apache.shiro.realm.jdbc.JdbcRealm}
 *
 * @author Shengzhao Li
 * @see org.apache.shiro.realm.jdbc.JdbcRealm
 */
public class OAuth2JdbcRealm extends MkkJdbcRealm {


    private static final Logger LOG = LoggerFactory.getLogger(OAuth2JdbcRealm.class);


    private OAuthRSService rsService;


    public OAuth2JdbcRealm() {
        super();
        setAuthenticationTokenClass(OAuth2Token.class);
    }


    private void validateToken(String token, AccessToken accessToken) throws OAuth2AuthenticationException {
        if (accessToken == null) {
            LOG.debug("Invalid access_token: {}, because it is null", token);
            throw new OAuth2AuthenticationException("Invalid access_token: " + token);
        }
        if (accessToken.tokenExpired()) {
            LOG.debug("Invalid access_token: {}, because it is expired", token);
            throw new OAuth2AuthenticationException("Invalid access_token: " + token);
        }
    }

    private void validateClientDetails(String token, AccessToken accessToken, ClientDetails clientDetails) throws OAuth2AuthenticationException {
        if (clientDetails == null || clientDetails.archived()) {
            LOG.debug("Invalid ClientDetails: {} by client_id: {}, it is null or archived", clientDetails, accessToken.clientId());
            throw new OAuth2AuthenticationException("Invalid client by token: " + token);
        }
    }

    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        OAuth2Token upToken = (OAuth2Token) token;
        final String accessToken = (String) upToken.getCredentials();

        if (StringUtils.isEmpty(accessToken)) {
            throw new OAuth2AuthenticationException("Invalid access_token: " + accessToken);
        }
        //Validate access token
        AccessToken aToken = rsService.loadAccessTokenByTokenId(accessToken);
        validateToken(accessToken, aToken);

        //Validate client details by resource-id
        final ClientDetails clientDetails = rsService.loadClientDetails(aToken.clientId(), upToken.getResourceId());
        validateClientDetails(accessToken, aToken, clientDetails);

        String username = aToken.username();

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        return new SimpleAuthenticationInfo(username, accessToken, getName());
    }


    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //null usernames are invalid
        if (principals == null) {
            throw new OAuth2AuthenticationException("PrincipalCollection method argument cannot be null.");
        }

        String username = (String) getAvailablePrincipal(principals);

        Connection conn = null;
        Set<String> roleNames = null;
        Set<String> permissions = null;
        try {
            conn = dataSource.getConnection();

            // Retrieve roles and permissions from database
            roleNames = getRoleNamesForUser(conn, username);
            if (permissionsLookupEnabled) {
                permissions = getPermissions(conn, username, roleNames);
            }

        } catch (SQLException e) {
            final String message = "There was a SQL error while authorizing user [" + username + "]";
            if (LOG.isErrorEnabled()) {
                LOG.error(message, e);
            }

            // Rethrow any SQL errors as an authorization exception
            throw new OAuth2AuthenticationException(message, e);
        } finally {
            JdbcUtils.closeConnection(conn);
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;

    }

    public void setRsService(OAuthRSService rsService) {
        this.rsService = rsService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();

        Assert.notNull(this.rsService);
    }
}
