package com.monkeyk.os.service;

import com.monkeyk.os.service.dto.UsersFormDto;
import com.monkeyk.os.service.dto.UsersOverviewDto;

/**
 * 2016/6/3
 *
 * @author Shengzhao Li
 */

public interface UserService {

    UsersOverviewDto loadUsersOverviewDto(String username);

    UsersFormDto loadUsersFormDto();

    boolean isExistedUsername(String username);

    String saveUsers(UsersFormDto formDto);
}