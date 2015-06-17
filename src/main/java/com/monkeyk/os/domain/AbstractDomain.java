package com.monkeyk.os.domain;

import com.monkeyk.os.infrastructure.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Shengzhao Li
 */
public abstract class AbstractDomain implements Serializable {


    /**
     * The domain create time.
     */
    protected Date createTime = DateUtils.now();

    public AbstractDomain() {
    }


    public Date createTime() {
        return createTime;
    }

}