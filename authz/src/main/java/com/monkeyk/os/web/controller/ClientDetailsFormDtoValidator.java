package com.monkeyk.os.web.controller;

import com.monkeyk.os.service.OauthService;
import com.monkeyk.os.service.dto.ClientDetailsFormDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 2016/6/8
 *
 * @author Shengzhao Li
 */
@Component
public class ClientDetailsFormDtoValidator implements Validator {


    @Autowired
    private OauthService oauthService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ClientDetailsFormDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClientDetailsFormDto formDto = (ClientDetailsFormDto) target;
        validateClientId(formDto, errors);
        validateClientSecret(formDto, errors);

        validateGrantTypes(formDto, errors);
    }


    private void validateGrantTypes(ClientDetailsFormDto clientDetailsDto, Errors errors) {
        final String grantTypes = clientDetailsDto.getGrantTypes();
        if (StringUtils.isEmpty(grantTypes)) {
            errors.rejectValue("grantTypes", null, "grant_type(s) is required");
            return;
        }

        if ("refresh_token".equalsIgnoreCase(grantTypes)) {
            errors.rejectValue("grantTypes", null, "grant_type(s) 不能只是[refresh_token]");
        }
    }

    private void validateClientSecret(ClientDetailsFormDto clientDetailsDto, Errors errors) {
        final String clientSecret = clientDetailsDto.getClientSecret();
        if (StringUtils.isEmpty(clientSecret)) {
            errors.rejectValue("clientSecret", null, "client_secret is required");
            return;
        }

        if (clientSecret.length() < 8) {
            errors.rejectValue("clientSecret", null, "client_secret 长度至少8位");
        }
    }

    private void validateClientId(ClientDetailsFormDto clientDetailsDto, Errors errors) {
        final String clientId = clientDetailsDto.getClientId();
        if (StringUtils.isEmpty(clientId)) {
            errors.rejectValue("clientId", null, "client_id is required");
            return;
        }

        if (clientId.length() < 5) {
            errors.rejectValue("clientId", null, "client_id 长度至少5位");
            return;
        }

        boolean existed = oauthService.isExistedClientId(clientId);
        if (existed) {
            errors.rejectValue("clientId", null, "client_id [" + clientId + "] 已存在");
        }

    }
}
