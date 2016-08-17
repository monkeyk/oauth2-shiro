package com.monkeyk.os.service.impl;

import com.monkeyk.os.domain.users.Users;
import com.monkeyk.os.domain.users.UsersAuthzRepository;
import com.monkeyk.os.service.UserService;
import com.monkeyk.os.service.business.UsersFormDtoLoader;
import com.monkeyk.os.service.business.UsersFormSaver;
import com.monkeyk.os.service.dto.UsersFormDto;
import com.monkeyk.os.service.dto.UsersOverviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 2016/6/3
 *
 * @author Shengzhao Li
 */
@Service("userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private UsersAuthzRepository usersAuthzRepository;

    @Override
    public UsersOverviewDto loadUsersOverviewDto(String username) {
        List<Users> usersList = usersAuthzRepository.findUsersByUsername(username);
        return new UsersOverviewDto(usersList, username);
    }

    @Override
    public UsersFormDto loadUsersFormDto() {
        UsersFormDtoLoader dtoLoader = new UsersFormDtoLoader();
        return dtoLoader.load();
    }

    @Override
    public boolean isExistedUsername(String username) {
        final Users user = usersAuthzRepository.findByUsername(username);
        return user != null;
    }

    @Override
    public String saveUsers(UsersFormDto formDto) {
        UsersFormSaver saver = new UsersFormSaver(formDto);
        return saver.save();
    }
}
