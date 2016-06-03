package com.monkeyk.os.infrastructure.jdbc;

import com.monkeyk.os.domain.users.Users;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 2016/6/3
 *
 * @author Shengzhao Li
 */
public class UsersRowMapper implements RowMapper<Users> {

    public UsersRowMapper() {
    }

    @Override
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
        Users users = new Users()
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .defaultUser(rs.getBoolean("default_user"))
                .lastLoginTime(rs.getTimestamp("last_login_time"));

        users.guid(rs.getString("guid"))
                .createTime(rs.getTimestamp("create_time"));
        return users;
    }
}
