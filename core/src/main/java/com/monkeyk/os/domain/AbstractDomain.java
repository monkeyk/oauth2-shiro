package com.monkeyk.os.domain;

import com.monkeyk.os.infrastructure.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 使用DDD(领域驱动设计)模式中, 定义抽象的Domain,
 * 包括公共的基础属性
 *
 * @author Shengzhao Li
 */
public abstract class AbstractDomain implements Serializable {


    private static final long serialVersionUID = 7787898374385773471L;
    /**
     * The domain create time.
     */
    protected Date createTime = DateUtils.now();

    /**
     * Domain的业务id
     */
    protected String guid;

    public AbstractDomain() {
    }

    public String guid() {
        return guid;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractDomain> T guid(String guid) {
        this.guid = guid;
        return (T) this;
    }

    public Date createTime() {
        return createTime;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractDomain> T createTime(Date createTime) {
        this.createTime = createTime;
        return (T) this;
    }


    @Override
    public String toString() {
        return "{" +
                "createTime=" + createTime +
                ", guid='" + guid + '\'' +
                '}';
    }
}