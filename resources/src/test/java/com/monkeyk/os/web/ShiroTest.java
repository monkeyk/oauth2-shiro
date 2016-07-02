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
package com.monkeyk.os.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * 15-6-10
 *
 * @author Shengzhao Li
 */
public class ShiroTest {


    @Test(enabled = false)
    public void login() {
        String username = "abc";
        //init SecurityManager
        SimpleAccountRealm realm = new SimpleAccountRealm("simple-realm");
        realm.addAccount(username, "abc", "USER");

        SimpleAccountRealm realm2 = new SimpleAccountRealm("simple-realm2");
        realm2.addAccount(username, "abc", "USER", "ADMIN");

        List<Realm> realmList = new ArrayList<>();
        realmList.add(realm);
        realmList.add(realm2);

        SecurityManager securityManager = new DefaultSecurityManager(realmList);
        SecurityUtils.setSecurityManager(securityManager);

        UsernamePasswordToken token = new UsernamePasswordToken(username, "abcdd");

        final Subject subject = SecurityUtils.getSubject();
        subject.login(token);


        final Subject subject1 = SecurityUtils.getSubject();
        assertTrue(subject1.isAuthenticated());

        assertFalse(subject1.isPermitted("OK"));
        assertTrue(subject1.hasRole("USER"));

//        assertTrue(subject1.isPermitted("USER:c,u"));


    }


    @Test
    public void md5() {

        Md5Hash md5Hash = new Md5Hash("admin");
        System.out.println(md5Hash.toString());
        System.out.println(md5Hash.toHex());
        System.out.println(md5Hash.toBase64());

    }

}
