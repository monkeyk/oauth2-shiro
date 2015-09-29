package com.monkeyk.os.infrastructure.jdbc;

import com.monkeyk.os.domain.oauth.ClientDetails;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 15-6-13
 *
 * @author Shengzhao Li
 */
public class ClientDetailsRowMapper implements RowMapper<ClientDetails> {


    public ClientDetailsRowMapper() {
    }

    @Override
    public ClientDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        ClientDetails details = new ClientDetails();
        details.setClientId(rs.getString("client_id"));
        details.setClientSecret(rs.getString("client_secret"));

        details.setName(rs.getString("client_name"));
        details.setClientUri(rs.getString("client_uri"));
        details.setIconUri(rs.getString("client_icon_uri"));

        details.resourceIds(rs.getString("resource_ids"));
        details.scope(rs.getString("scope"));
        details.grantTypes(rs.getString("grant_types"));

        details.setRedirectUri(rs.getString("redirect_uri"));
        details.roles(rs.getString("roles"));
        details.accessTokenValidity(rs.getInt("access_token_validity"));

        details.refreshTokenValidity(rs.getInt("refresh_token_validity"));
        details.setDescription(rs.getString("description"));
        details.createTime(rs.getTimestamp("create_time"));

        details.archived(rs.getBoolean("archived"));
        details.trusted(rs.getBoolean("trusted"));

        return details;
    }
}
