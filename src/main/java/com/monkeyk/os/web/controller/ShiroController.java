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
package com.monkeyk.os.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 15-6-10
 *
 * @author Shengzhao Li
 */
@Controller
public class ShiroController {


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(String username, String password, Model model) {

//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//
//        final Subject subject = SecurityUtils.getSubject();
//        subject.login(token);

        return "login";
    }


}
