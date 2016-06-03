package com.monkeyk.os.infrastructure.jdbc;

import com.monkeyk.os.domain.users.Roles;
import com.monkeyk.os.domain.users.Users;
import com.monkeyk.os.domain.users.UsersAuthzRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 2016/6/3
 *
 * @author Shengzhao Li
 */
@Repository("usersJdbcAuthzRepository")
public class UsersJdbcAuthzRepository extends AbstractJdbcRepository implements UsersAuthzRepository {


    private static UsersRowMapper usersRowMapper = new UsersRowMapper();
    private static RolesRowMapper rolesRowMapper = new RolesRowMapper();


    @Override
    public List<Users> findUsersByUsername(String username) {
        String sql = " select u.* from users u where u.archived = 0 ";

        if (StringUtils.isNotEmpty(username)) {
            sql += " and u.username = ? ";
            return this.jdbcTemplate.query(sql, usersRowMapper, username);
        }

        return this.jdbcTemplate.query(sql, usersRowMapper);
    }

    @Override
    public List<Roles> findUsersRolesList(String usersGuid) {
        String sql = " select r.* from roles r where r.archived = 0 and r.id in (" +
                " select roles_id from user_roles where users_id = (" +
                " select id from users where guid = ? and archived = 0))";
        return this.jdbcTemplate.query(sql, rolesRowMapper, usersGuid);
    }
}
