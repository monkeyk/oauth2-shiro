package com.monkeyk.os.web.context;

import com.monkeyk.os.infrastructure.ThreadLocalHolder;
import com.monkeyk.os.web.WebUtils;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Wrap the spring <i>CharacterEncodingFilter</i>, add retrieve client ip action
 *
 * @author Shengzhao Li
 */
public class MkkCharacterEncodingFilter extends CharacterEncodingFilter {


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        persistIp(request);
        super.doFilterInternal(request, response, filterChain);

    }

    private void persistIp(HttpServletRequest request) {
        final String clientIp = WebUtils.retrieveClientIp(request);
        ThreadLocalHolder.clientIp(clientIp);
    }


}