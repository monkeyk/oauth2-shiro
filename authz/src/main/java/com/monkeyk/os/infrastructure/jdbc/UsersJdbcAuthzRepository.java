package com.monkeyk.os.infrastructure.jdbc;

import com.monkeyk.os.domain.users.Roles;
import com.monkeyk.os.domain.users.Users;
import com.monkeyk.os.domain.users.UsersAuthzRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 2016/6/3
 * <p/>
 * 账号(Users) 相关的 JDBC操作实现
 *
 * @author Shengzhao Li
 */
@Repository("usersJdbcAuthzRepository")
public class UsersJdbcAuthzRepository extends AbstractJdbcRepository implements UsersAuthzRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UsersJdbcAuthzRepository.class);

    private final UsersRowMapper usersRowMapper = new UsersRowMapper();
    private final RolesRowMapper rolesRowMapper = new RolesRowMapper();


    @Override
    public List<Users> findUsersByUsername(String username) {
        String sql = " select u.* from users u where u.archived = 0 ";

        if (StringUtils.isNotEmpty(username)) {
            sql += " and u.username = ?  order by u.id desc ";
            return this.jdbcTemplate.query(sql, usersRowMapper, username);
        }
        sql += " order by u.id desc ";
        return this.jdbcTemplate.query(sql, usersRowMapper);
    }

    @Override
    public List<Roles> findAvailableRolesList() {
        String sql = " select r.* from roles r where r.archived = 0 ";
        return this.jdbcTemplate.query(sql, rolesRowMapper);
    }

    @Override
    public Users findByUsername(String username) {
        String sql = " select * from users where username = ? ";
        final List<Users> list = this.jdbcTemplate.query(sql, usersRowMapper, username);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int saveUsers(final Users users) {
        String sql = " insert into users(guid,create_time, username,password, password_salt) values (?,?,?,?,?) ";
        int row = this.jdbcTemplate.update(sql, ps -> {
            ps.setString(1, users.guid());
            ps.setTimestamp(2, new Timestamp(users.createTime().getTime()));
            ps.setString(3, users.username());

            ps.setString(4, users.password());
            ps.setString(5, users.passwordSalt());
        });
        if (LOG.isDebugEnabled()) {
            LOG.debug("Insert into users -> row: {}", row);
        }

        return this.jdbcTemplate.queryForObject("select id from users where guid = ?", Integer.class, new Object[]{users.guid()});
    }

    @Override
    public Roles findRolesByGuid(String guid) {
        final List<Roles> list = this.jdbcTemplate.query(" select * from roles where guid = ?", rolesRowMapper, guid);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void insertUserRoles(final int userId, final int rolesId) {
        String sql = "insert into user_roles(users_id,roles_id) values (?,?) ";
        int row = this.jdbcTemplate.update(sql, ps -> {
            ps.setInt(1, userId);
            ps.setInt(2, rolesId);
        });
        if (LOG.isDebugEnabled()) {
            LOG.debug("Insert into user_roles -> row: {}", row);
        }
    }

    @Override
    public List<Roles> findUsersRolesList(String usersGuid) {
        String sql = " select r.* from roles r where r.archived = 0 and r.id in (" +
                " select roles_id from user_roles where users_id = (" +
                " select id from users where guid = ? and archived = 0))";
        return this.jdbcTemplate.query(sql, rolesRowMapper, usersGuid);
    }

    @Override
    public List<String> findPermissionsByRoles(String rolesGuid) {
        String sql = " select p.permission from roles_permissions p where p.roles_id = (" +
                " select id from roles where guid = ? and archived = 0 )";
        return this.jdbcTemplate.queryForList(sql, String.class, new Object[]{rolesGuid});
    }
}
