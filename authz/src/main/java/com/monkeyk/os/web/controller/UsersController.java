package com.monkeyk.os.web.controller;

import com.monkeyk.os.service.UserService;
import com.monkeyk.os.service.dto.UsersOverviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 2016/6/3
 *
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("/users/")
public class UsersController {


    @Autowired
    private UserService userService;


    @RequestMapping(value = "overview", method = RequestMethod.GET)
    public String overview(String username, Model model) {
        UsersOverviewDto overviewDto = userService.loadUsersOverviewDto(username);
        model.addAttribute("overviewDto", overviewDto);
        return "users/users_overview";
    }


}
