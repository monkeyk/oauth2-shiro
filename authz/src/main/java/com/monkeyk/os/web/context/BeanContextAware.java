package com.monkeyk.os.web.context;

import com.monkeyk.os.domain.shared.BeanProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import static com.monkeyk.os.domain.oauth.Constants.VERSION;


/**
 * 2020/7/14
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
@Component
public class BeanContextAware implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(BeanContextAware.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanProvider.initialize(applicationContext);
        if (LOG.isInfoEnabled()) {
            LOG.info("Deploy [authz] version: {}", VERSION);
        }
    }
}
