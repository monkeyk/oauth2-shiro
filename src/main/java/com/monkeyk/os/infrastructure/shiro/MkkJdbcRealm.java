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
package com.monkeyk.os.infrastructure.shiro;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.springframework.beans.factory.InitializingBean;

/**
 * 15-6-10
 *
 * @author Shengzhao Li
 */
public class MkkJdbcRealm extends JdbcRealm implements InitializingBean {

    public static final String AUTHENTICATION_QUERY = "select password from users where archived = 0 and username = ?";

    public static final String USER_ROLES_QUERY = "select r.role_name from user_roles ur,users u,roles r  where ur.users_id = u.id and ur.roles_id = r.id and u.username = ?";

    public static final String PERMISSIONS_QUERY = "select rp.permission from roles_permissions rp,roles r where r.id = rp.roles_id and r.role_name = ?";


    public MkkJdbcRealm() {
        super();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //override
        setAuthenticationQuery(AUTHENTICATION_QUERY);
        setUserRolesQuery(USER_ROLES_QUERY);
        setPermissionsQuery(PERMISSIONS_QUERY);
    }
}
