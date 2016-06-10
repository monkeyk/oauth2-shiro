package com.monkeyk.os.web.controller;

import com.monkeyk.os.service.dto.SystemTimeDto;
import org.apache.oltu.oauth2.common.OAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Mobile resource API
 * Protect by Oauth
 * <p/>
 * <p/>
 * resource-id: mobile-resource
 *
 * @author Shengzhao Li
 * @see org.apache.oltu.oauth2.rsfilter.OAuthFilter
 */
@Controller
@RequestMapping("/mobile/")
public class MobileResourcesController {


    private static final Logger LOG = LoggerFactory.getLogger(MobileResourcesController.class);


    /**
     * Return system time API
     *
     * @param request HttpServletRequest
     */
    @RequestMapping(value = "system_time", method = RequestMethod.GET)
    @ResponseBody
    public SystemTimeDto systemTime(HttpServletRequest request) {

        final String clientId = (String) request.getAttribute(OAuth.OAUTH_CLIENT_ID);
        LOG.debug("Current clientId: {}", clientId);

        final String username = request.getUserPrincipal().getName();
        LOG.debug("Current username: {}", username);

        return new SystemTimeDto();
    }


}