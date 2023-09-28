package com.monkeyk.os.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.monkeyk.os.domain.oauth.Constants.VERSION;

/**
 * 2023/9/21 12:22
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
@Controller
public class WelcomeController {


    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("version", VERSION);
        return "welcome";
    }

}
