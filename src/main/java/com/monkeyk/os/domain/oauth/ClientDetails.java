/*
 * Copyright (c) 2013 Andaily Information Technology Co. Ltd
 * www.andaily.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Andaily Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with Andaily Information Technology Co. Ltd.
 */
package com.monkeyk.os.domain.oauth;

import com.monkeyk.os.infrastructure.DateUtils;
import org.apache.oltu.oauth2.common.domain.client.BasicClientInfo;

import java.util.Date;

/**
 * 15-6-12
 * <p/>
 * DBTable: oauth_client_details
 *
 * @author Shengzhao Li
 */
public class ClientDetails extends BasicClientInfo {


    private String resourceIds;

    private String scope;

    private String grantTypes;

    /*
   * Shiro roles
   * */
    private String roles;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private boolean trusted = false;

    private boolean archived = false;

    private Date createTime = DateUtils.now();

    public ClientDetails() {
    }


}
