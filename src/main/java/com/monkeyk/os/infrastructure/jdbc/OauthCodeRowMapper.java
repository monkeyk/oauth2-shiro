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

import com.monkeyk.os.domain.oauth.OauthCode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 15-6-13
 *
 * @author Shengzhao Li
 */
public class OauthCodeRowMapper implements RowMapper<OauthCode> {


    public OauthCodeRowMapper() {
    }

    @Override
    public OauthCode mapRow(ResultSet rs, int rowNum) throws SQLException {
        final OauthCode oauthCode = new OauthCode()
                .clientId(rs.getString("client_id"))
                .username(rs.getString("username"))
                .code(rs.getString("code"));

        oauthCode.createTime(rs.getTimestamp("create_time"));
        return oauthCode;

    }
}
