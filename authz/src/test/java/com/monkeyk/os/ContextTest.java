package com.monkeyk.os;

import com.monkeyk.os.domain.shared.BeanProvider;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.BeforeTransaction;

/**
 * @author Shengzhao Li
 */
@ContextConfiguration(locations = {"classpath:/spring/*.xml"}, initializers = {TestApplicationContextInitializer.class})
public abstract class ContextTest extends AbstractTransactionalJUnit4SpringContextTests {


    @BeforeTransaction
    public void before() throws Exception {
        BeanProvider.initialize(applicationContext);
    }
}