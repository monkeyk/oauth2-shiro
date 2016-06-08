package com.monkeyk.os.service.impl;

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.OauthRepository;
import com.monkeyk.os.service.ClientDetailsService;
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


    @Override
    public ClientDetailsListDto loadClientDetailsListDto(String clientId) {
        List<ClientDetails> clientDetailsList = oauthRepository.findClientDetailsListByClientId(clientId);
        return new ClientDetailsListDto(clientId, clientDetailsList);
    }
}
