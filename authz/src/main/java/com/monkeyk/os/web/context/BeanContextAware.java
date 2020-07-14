package com.monkeyk.os.web.context;

import com.monkeyk.os.domain.shared.BeanProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 2020/7/14
 *
 * @author Shengzhao Li
 * @since 2.0.0
 */
@Component
public class BeanContextAware implements ApplicationContextAware {


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanProvider.initialize(applicationContext);
    }
}
