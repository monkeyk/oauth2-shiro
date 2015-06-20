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

import com.monkeyk.os.domain.oauth.AccessToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 15-6-20
 *
 * @author Shengzhao Li
 */
public class AccessTokenRowMapper implements RowMapper<AccessToken> {


    public AccessTokenRowMapper() {
    }

    @Override
    public AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
        final AccessToken oauthCode = new AccessToken()
                .tokenId(rs.getString("token_id"))
                .tokenExpiredSeconds(rs.getInt("token_expired_seconds"))
                .authenticationId(rs.getString("authentication_id"))

                .username(rs.getString("username"))
                .clientId(rs.getString("client_id"))
                .tokenType(rs.getString("token_type"))

                .refreshTokenExpiredSeconds(rs.getInt("refresh_token_expired_seconds"))
                .refreshToken(rs.getString("refresh_token"));

        oauthCode.createTime(rs.getTimestamp("create_time"));


        return oauthCode;

    }
}
