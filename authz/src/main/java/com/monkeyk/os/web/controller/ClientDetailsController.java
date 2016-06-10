package com.monkeyk.os.web.controller;

import com.monkeyk.os.service.ClientDetailsService;
import com.monkeyk.os.service.dto.ClientDetailsDto;
import com.monkeyk.os.service.dto.ClientDetailsFormDto;
import com.monkeyk.os.service.dto.ClientDetailsListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private ClientDetailsFormDtoValidator validator;


    @RequestMapping(value = "client_details", method = RequestMethod.GET)
    public String clientDetails(String clientId, Model model) {
        ClientDetailsListDto listDto = clientDetailsService.loadClientDetailsListDto(clientId);
        model.addAttribute("listDto", listDto);
        return "oauth/client_details";
    }


    @RequestMapping(value = "client_details/form/plus", method = RequestMethod.GET)
    public String plusUser(Model model) {
        ClientDetailsFormDto formDto = clientDetailsService.loadClientDetailsFormDto();
        model.addAttribute("formDto", formDto);
        return "oauth/client_details_plus";
    }


    @RequestMapping(value = "client_details/form/plus", method = RequestMethod.POST)
    public String submitRegisterClient(@ModelAttribute("formDto") ClientDetailsFormDto formDto, BindingResult result) {
        validator.validate(formDto, result);
        if (result.hasErrors()) {
            return "oauth/client_details_plus";
        }
        clientDetailsService.saveClientDetails(formDto);
        return "redirect:../../client_details";
    }


    /*
    * Test client
    * */
    @RequestMapping("client_details/test_client/{clientId}")
    public String testClient(@PathVariable("clientId") String clientId, Model model) {
        ClientDetailsDto clientDetailsDto = clientDetailsService.loadClientDetailsDto(clientId);
        model.addAttribute("clientDetailsDto", clientDetailsDto);
        return "oauth/test_client";
    }

}
