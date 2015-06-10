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

import com.monkeyk.os.service.dto.LoginDto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 15-6-10
 *
 * @author Shengzhao Li
 */
@Controller
public class ShiroController {

    private static final Logger LOG = LoggerFactory.getLogger(ShiroController.class);


    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    /*
     * Logout
     */
//    @RequestMapping("logout")
//    public String logout() {
//        final Subject subject = SecurityUtils.getSubject();
//        LOG.debug("{} is logout", subject.getPrincipal());
//        subject.logout();
//        return "redirect:/";
//    }

    /*
     * Go login page
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("formDto", new LoginDto());
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute("formDto") LoginDto formDto, BindingResult errors) {

        UsernamePasswordToken token = formDto.token();
        token.setRememberMe(true);

        try {
            SecurityUtils.getSubject().login(token);
        } catch (Exception e) {
            LOG.debug("Error authenticating.", e);
            errors.rejectValue("username", null, "The username or password was not correct.");
            return "login";
        }

        return "redirect:index";
    }


}
