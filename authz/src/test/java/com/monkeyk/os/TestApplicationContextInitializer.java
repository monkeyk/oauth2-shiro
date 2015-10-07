package com.monkeyk.os;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Shengzhao Li
 */
public class TestApplicationContextInitializer implements ApplicationContextInitializer<AbstractApplicationContext> {

    @Override
    public void initialize(AbstractApplicationContext applicationContext) {
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        //load database.properties
        propertyPlaceholderConfigurer.setLocation(new ClassPathResource("test.properties"));

        applicationContext.addBeanFactoryPostProcessor(propertyPlaceholderConfigurer);


    }
}