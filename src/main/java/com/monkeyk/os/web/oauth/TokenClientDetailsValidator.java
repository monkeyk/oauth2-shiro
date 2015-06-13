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
package com.monkeyk.os.web.oauth;

import com.monkeyk.os.domain.oauth.ClientDetails;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

/**
 * 15-6-13
 *
 * @author Shengzhao Li
 */
public class TokenClientDetailsValidator extends AbstractClientDetailsValidator {


    protected TokenClientDetailsValidator(OAuthAuthzRequest oauthRequest) {
        super(oauthRequest);
    }

    @Override
    public OAuthResponse validateSelf(ClientDetails clientDetails) {

        return null;
    }


}
