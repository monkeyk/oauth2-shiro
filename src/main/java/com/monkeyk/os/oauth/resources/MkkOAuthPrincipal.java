/*
 * Copyright (c) 2015 MONKEYK Information Technology Co. Ltd
 * www.monkeyk.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * MONKEYK Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with MONKEYK Information Technology Co. Ltd.
 */
package com.monkeyk.os.oauth.resources;

import java.security.Principal;

/**
 * 2015/9/25
 *
 * @author Shengzhao Li
 */
public class MkkOAuthPrincipal implements Principal {


    private String name;

    public MkkOAuthPrincipal() {
    }

    public MkkOAuthPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }


}
