package com.monkeyk.os.service.business;

import com.monkeyk.os.domain.users.AuthzPasswordEncoder;
import com.monkeyk.os.domain.users.Roles;
import com.monkeyk.os.domain.users.Users;
import com.monkeyk.os.domain.users.UsersAuthzRepository;
import com.monkeyk.os.service.dto.UsersFormDto;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 2016/6/7
 *
 * @author Shengzhao Li
 */
@Component
public class UsersFormSaver {

    @Autowired
    private UsersAuthzRepository usersRepository;

    @Autowired
    private AuthzPasswordEncoder passwordEncoder;


    public UsersFormSaver() {
    }


    public String save(UsersFormDto formDto) {

        Users users = formDto.newUsers();
        //用指定的加密方式
        ByteSource salt = ByteSource.Util.bytes(Base64.decode(users.passwordSalt()));
        users.password(this.passwordEncoder.encode(formDto.getPassword(), salt));

        final int id = usersRepository.saveUsers(users);

        final List<String> roleGuids = formDto.getRoleGuids();
        for (String roleGuid : roleGuids) {
            Roles roles = usersRepository.findRolesByGuid(roleGuid);
            usersRepository.insertUserRoles(id, roles.id());
        }

        return users.guid();
    }
}
