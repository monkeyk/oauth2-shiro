package com.monkeyk.os.domain.users;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/*
  * @author Shengzhao Li
  */
public class PasswordHandlerTest {

    @Test
    public void testMd5() throws Exception {


        final String admin = PasswordHandler.md5("admin");
        assertNotNull(admin);
        assertEquals(admin, "21232f297a57a5a743894a0e4a801fc3");


        final String test = PasswordHandler.md5("test");
        assertNotNull(test);
        assertEquals(test, "098f6bcd4621d373cade4e832627b4f6");
    }
}