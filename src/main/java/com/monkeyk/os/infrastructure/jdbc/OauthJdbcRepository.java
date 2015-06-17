/*
 * Copyright (c) 2013 Andaily Information Technology Co. Ltd
 * www.andaily.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Andaily Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with Andaily Information Technology Co. Ltd.
 */
package com.monkeyk.os.infrastructure.jdbc;

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.OauthCode;
import com.monkeyk.os.domain.oauth.OauthRepository;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 15-6-13
 *
 * @author Shengzhao Li
 */
@Repository("oauthJdbcRepository")
public class OauthJdbcRepository extends AbstractJdbcRepository implements OauthRepository {


    private static ClientDetailsRowMapper clientDetailsRowMapper = new ClientDetailsRowMapper();
    private static OauthCodeRowMapper oauthCodeRowMapper = new OauthCodeRowMapper();


    @Override
    public ClientDetails findClientDetails(String clientId) {
        final String sql = " select * from oauth_client_details where archived = 0 and client_id = ? ";
        final List<ClientDetails> list = jdbcTemplate.query(sql, clientDetailsRowMapper, clientId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int saveClientDetails(final ClientDetails clientDetails) {
        final String sql = " insert into oauth_client_details(client_id,client_secret) values (?,?)";

        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, clientDetails.getClientId());
                ps.setString(2, clientDetails.getClientSecret());
                //more setter
            }
        });
    }

    @Override
    public int saveOauthCode(final OauthCode oauthCode) {
        final String sql = " insert into oauth_code(code,username,client_id,expired_seconds) values (?,?,?,?)";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, oauthCode.code());
                ps.setString(2, oauthCode.username());
                ps.setString(3, oauthCode.clientId());
                ps.setInt(4, oauthCode.expiredSeconds());
            }
        });
    }

    @Override
    public OauthCode findOauthCode(String code, String clientId) {
        final String sql = " select * from oauth_code where code = ? and client_id = ? ";
        final List<OauthCode> list = jdbcTemplate.query(sql, oauthCodeRowMapper, code, clientId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public OauthCode findOauthCodeByUsernameClientId(String username, String clientId) {
        final String sql = " select * from oauth_code where username = ? and client_id = ? ";
        final List<OauthCode> list = jdbcTemplate.query(sql, oauthCodeRowMapper, username, clientId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int deleteOauthCode(final OauthCode oauthCode) {
        final String sql = " delete from oauth_code where code = ? and client_id = ? and username = ?";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, oauthCode.code());
                ps.setString(2, oauthCode.clientId());
                ps.setString(3, oauthCode.username());
            }
        });
    }
}
