package com.monkeyk.os.domain.users;

import com.monkeyk.os.domain.AbstractDomain;

/**
 * 2016/6/3
 * Table: roles
 *
 * @author Shengzhao Li
 */
public class Roles extends AbstractDomain {
    private static final long serialVersionUID = 8762398291767207066L;

    private String roleName;

    public Roles() {
    }


    public String roleName() {
        return roleName;
    }

    public Roles roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }
}
