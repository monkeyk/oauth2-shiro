package com.monkeyk.os.web.controller;

import com.monkeyk.os.service.UserService;
import com.monkeyk.os.service.dto.UsersFormDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * 2016/6/7
 *
 * @author Shengzhao Li
 */
@Component
public class UsersFormDtoValidator implements Validator {


    @Autowired
    private UserService userService;


    @Override
    public boolean supports(Class<?> clazz) {
        return UsersFormDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UsersFormDto formDto = (UsersFormDto) target;
        validatePassword(errors, formDto);
        validateUsername(errors, formDto);
        validateRoles(errors, formDto);
    }

    private void validateRoles(Errors errors, UsersFormDto formDto) {
        final List<String> roleGuids = formDto.getRoleGuids();
        if(roleGuids==null||roleGuids.isEmpty()){
            errors.rejectValue("roleGuids", null, "Roles is required");
        }
    }


    private void validatePassword(Errors errors, UsersFormDto formDto) {
        final String password = formDto.getPassword();
        if (StringUtils.isEmpty(password)) {
            errors.rejectValue("password", null, "Password is required");
        }
    }

    private void validateUsername(Errors errors, UsersFormDto formDto) {
        final String username = formDto.getUsername();
        if (StringUtils.isEmpty(username)) {
            errors.rejectValue("username", null, "Username is required");
            return;
        }

        boolean existed = userService.isExistedUsername(username);
        if (existed) {
            errors.rejectValue("username", null, "Username already existed");
        }

    }
}
