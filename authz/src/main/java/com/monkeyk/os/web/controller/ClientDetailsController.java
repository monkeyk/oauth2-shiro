package com.monkeyk.os.web.controller;

import com.monkeyk.os.service.ClientDetailsService;
import com.monkeyk.os.service.dto.ClientDetailsListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 2016/6/8
 *
 * @author Shengzhao Li
 */
@Controller
public class ClientDetailsController {


    @Autowired
    private ClientDetailsService clientDetailsService;


    @RequestMapping(value = "client_details", method = RequestMethod.GET)
    public String clientDetails(String clientId, Model model) {
        ClientDetailsListDto listDto = clientDetailsService.loadClientDetailsListDto(clientId);
        model.addAttribute("listDto", listDto);
        return "oauth/client_details";
    }

}
