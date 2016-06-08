package com.monkeyk.os.service.dto;

import com.monkeyk.os.domain.users.Roles;
import com.monkeyk.os.infrastructure.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 2016/6/3
 *
 * @author Shengzhao Li
 */
public class RolesDto implements Serializable {
    private static final long serialVersionUID = -6808878470603114069L;


    private int id;
    private String guid;
    private String createTime;

    private String roleName;

    private List<String> permissions = new ArrayList<>();

    public RolesDto() {
    }

    public RolesDto(Roles roles) {
        this.id = roles.id();
        this.guid = roles.guid();
        this.createTime = DateUtils.toDateTime(roles.createTime());

        this.roleName = roles.roleName();
        this.permissions = roles.permissions();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public static List<RolesDto> toDtos(List<Roles> roleses) {
        List<RolesDto> dtos = new ArrayList<>(roleses.size());
        for (Roles rolese : roleses) {
            dtos.add(new RolesDto(rolese));
        }
        return dtos;
    }
}
