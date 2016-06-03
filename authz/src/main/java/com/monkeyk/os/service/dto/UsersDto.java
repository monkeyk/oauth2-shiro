package com.monkeyk.os.service.dto;

import com.monkeyk.os.domain.users.Users;
import com.monkeyk.os.infrastructure.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 2016/6/3
 *
 * @author Shengzhao Li
 */
public class UsersDto implements Serializable {
    private static final long serialVersionUID = -2959913020700099652L;

    private String guid;
    private String createTime;


    private String username;
    private String password;


    private boolean defaultUser;


    private List<RolesDto> rolesDtos = new ArrayList<>();

    public UsersDto() {
    }

    public UsersDto(Users users, boolean fully) {
        this.guid = users.guid();
        this.createTime = DateUtils.toDateTime(users.createTime());

        this.username = users.username();
        this.password = users.password();
        this.defaultUser = users.defaultUser();

        if (fully) {
            this.rolesDtos = RolesDto.toDtos(users.rolesList());
        }
    }


    public List<RolesDto> getRolesDtos() {
        return rolesDtos;
    }

    public void setRolesDtos(List<RolesDto> rolesDtos) {
        this.rolesDtos = rolesDtos;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDefaultUser() {
        return defaultUser;
    }

    public void setDefaultUser(boolean defaultUser) {
        this.defaultUser = defaultUser;
    }

    public static List<UsersDto> toDtos(List<Users> usersList) {
        List<UsersDto> dtos = new ArrayList<>(usersList.size());
        for (Users users : usersList) {
            dtos.add(new UsersDto(users, true));
        }
        return dtos;
    }
}
