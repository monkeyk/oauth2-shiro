package com.monkeyk.os.service.business;

import com.monkeyk.os.domain.shared.BeanProvider;
import com.monkeyk.os.domain.users.Roles;
import com.monkeyk.os.domain.users.UsersAuthzRepository;
import com.monkeyk.os.domain.users.UsersRepository;
import com.monkeyk.os.service.dto.RolesDto;
import com.monkeyk.os.service.dto.UsersFormDto;

import java.util.List;

/**
 * 2016/6/7
 *
 * @author Shengzhao Li
 */
public class UsersFormDtoLoader {

    private transient UsersAuthzRepository usersRepository = BeanProvider.getBean(UsersAuthzRepository.class);

    public UsersFormDtoLoader() {
    }

    public UsersFormDto load() {

        UsersFormDto formDto = new UsersFormDto();

        List<Roles> rolesList = usersRepository.findAvailableRolesList();
        formDto.setRolesDtos(RolesDto.toDtos(rolesList));

        return formDto;
    }
}
