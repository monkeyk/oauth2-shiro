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
package com.monkeyk.os.web.oauth.token;

import com.monkeyk.os.web.oauth.OAuthTokenxRequest;
import com.monkeyk.os.web.oauth.validator.AbstractClientDetailsValidator;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

/**
 * 2015/7/3
 * <p/>
 * grant_type=refresh_token
 *
 * @author Shengzhao Li
 */
public class RefreshTokenHandler extends AbstractOAuthTokenHandler {

    @Override
    public boolean support(OAuthTokenxRequest tokenRequest) throws OAuthProblemException {
        final String grantType = tokenRequest.getGrantType();
        return GrantType.REFRESH_TOKEN.toString().equalsIgnoreCase(grantType);
    }

    @Override
    public void handleAfterValidation() throws OAuthProblemException {


    }

    @Override
    protected AbstractClientDetailsValidator getValidator() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
