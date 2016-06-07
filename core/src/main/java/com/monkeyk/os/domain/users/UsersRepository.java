package com.monkeyk.os.domain.users;

import com.monkeyk.os.domain.shared.Repository;

import java.util.List;

/**
 * 2016/6/3
 *
 * @author Shengzhao Li
 */

public interface UsersRepository extends Repository {

    List<Roles> findUsersRolesList(String usersGuid);

    List<String> findPermissionsByRoles(String rolesGuid);
}