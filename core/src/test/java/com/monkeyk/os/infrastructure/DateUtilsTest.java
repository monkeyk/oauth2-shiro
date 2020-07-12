package com.monkeyk.os.infrastructure;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Shengzhao Li
 */
public class DateUtilsTest {


    @Test
    public void isDate() {

        assertTrue(DateUtils.isDate("2016-12-12"));
        assertTrue(DateUtils.isDate("2016-01-01"));
        assertTrue(DateUtils.isDate("0000-12-12"));
        assertTrue(DateUtils.isDate("3652-02-11"));

    }

}