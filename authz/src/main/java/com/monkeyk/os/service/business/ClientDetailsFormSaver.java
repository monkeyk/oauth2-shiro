package com.monkeyk.os.service.business;

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.OauthRepository;
import com.monkeyk.os.domain.shared.BeanProvider;
import com.monkeyk.os.service.dto.ClientDetailsFormDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2016/6/9
 *
 * @author Shengzhao Li
 */
public class ClientDetailsFormSaver {


    private static final Logger LOG = LoggerFactory.getLogger(ClientDetailsFormSaver.class);

    private transient OauthRepository oauthRepository = BeanProvider.getBean(OauthRepository.class);

    private ClientDetailsFormDto formDto;

    public ClientDetailsFormSaver(ClientDetailsFormDto formDto) {
        this.formDto = formDto;
    }

    public String save() {

        ClientDetails clientDetails = formDto.newClientDetails();
        oauthRepository.saveClientDetails(clientDetails);
        LOG.debug("Save ClientDetails: {}", clientDetails);
        return clientDetails.getClientId();
    }
}
