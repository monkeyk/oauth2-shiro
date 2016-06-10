package com.monkeyk.os.infrastructure.jdbc;

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;

import com.monkeyk.os.domain.rs.OAuthRSRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 15-6-13
 *
 * @author Shengzhao Li
 */
@Repository("oauthRSJdbcRepository")
public class OAuthRSJdbcRepository extends AbstractJdbcRepository implements OAuthRSRepository {


    private static ClientDetailsRowMapper clientDetailsRowMapper = new ClientDetailsRowMapper();
    private static AccessTokenRowMapper accessTokenRowMapper = new AccessTokenRowMapper();


    @Override
    public AccessToken findAccessTokenByTokenId(String tokenId) {
        final String sql = " select * from oauth_access_token where token_id = ?";
        final List<AccessToken> list = jdbcTemplate.query(sql, accessTokenRowMapper, tokenId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public ClientDetails findClientDetailsByClientIdAndResourceIds(String clientId, String resourceIds) {
        final String sql = " select * from oauth_client_details where archived = 0 and client_id = ? and resource_ids = ? ";
        final List<ClientDetails> list = jdbcTemplate.query(sql, clientDetailsRowMapper, clientId, resourceIds);
        return list.isEmpty() ? null : list.get(0);
    }
}
