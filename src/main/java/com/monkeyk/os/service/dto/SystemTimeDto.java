package com.monkeyk.os.service.dto;

import com.monkeyk.os.infrastructure.DateUtils;

import java.io.Serializable;

/**
 * 2015/9/29
 *
 * @author Shengzhao Li
 */
public class SystemTimeDto implements Serializable {

    private long time = DateUtils.now().getTime();


    public SystemTimeDto() {
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
