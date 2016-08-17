package com.monkeyk.os.service.impl;

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.OauthRepository;
import com.monkeyk.os.domain.users.Roles;
import com.monkeyk.os.domain.users.UsersAuthzRepository;
import com.monkeyk.os.service.ClientDetailsService;
import com.monkeyk.os.service.business.ClientDetailsFormSaver;
import com.monkeyk.os.service.dto.ClientDetailsDto;
import com.monkeyk.os.service.dto.ClientDetailsFormDto;
import com.monkeyk.os.service.dto.ClientDetailsListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 2016/6/8
 *
 * @author Shengzhao Li
 */
@Service("clientDetailsService")
public class ClientDetailsServiceImpl implements ClientDetailsService {


    @Autowired
    private OauthRepository oauthRepository;
    @Autowired
    private UsersAuthzRepository usersAuthzRepository;


    @Override
    public ClientDetailsListDto loadClientDetailsListDto(String clientId) {
        List<ClientDetails> clientDetailsList = oauthRepository.findClientDetailsListByClientId(clientId);
        return new ClientDetailsListDto(clientId, clientDetailsList);
    }

    @Override
    public ClientDetailsFormDto loadClientDetailsFormDto() {
        List<Roles> rolesList = usersAuthzRepository.findAvailableRolesList();
        return new ClientDetailsFormDto(rolesList);
    }

    @Override
    public String saveClientDetails(ClientDetailsFormDto formDto) {
        ClientDetailsFormSaver saver = new ClientDetailsFormSaver(formDto);
        return saver.save();
    }

    @Override
    public ClientDetailsDto loadClientDetailsDto(String clientId) {
        ClientDetails clientDetails = oauthRepository.findClientDetails(clientId);
        return new ClientDetailsDto(clientDetails);
    }
}
