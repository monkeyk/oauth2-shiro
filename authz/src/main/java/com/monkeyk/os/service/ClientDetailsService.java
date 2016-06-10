package com.monkeyk.os.service;

import com.monkeyk.os.service.dto.ClientDetailsDto;
import com.monkeyk.os.service.dto.ClientDetailsFormDto;
import com.monkeyk.os.service.dto.ClientDetailsListDto;

/**
 * 2016/6/8
 *
 * @author Shengzhao Li
 */

public interface ClientDetailsService {

    ClientDetailsListDto loadClientDetailsListDto(String clientId);

    ClientDetailsFormDto loadClientDetailsFormDto();

    String saveClientDetails(ClientDetailsFormDto formDto);

    ClientDetailsDto loadClientDetailsDto(String clientId);
}