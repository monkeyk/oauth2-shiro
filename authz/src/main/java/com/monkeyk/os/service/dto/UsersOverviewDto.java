package com.monkeyk.os.service.dto;

import com.monkeyk.os.domain.users.Users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 2016/6/3
 *
 * @author Shengzhao Li
 */
public class UsersOverviewDto implements Serializable {
    private static final long serialVersionUID = 4895901148003956952L;

    private List<UsersDto> usersDtos = new ArrayList<>();

    private String username;

    public UsersOverviewDto() {
    }

    public UsersOverviewDto(List<Users> usersList, String username) {
        this.usersDtos = UsersDto.toDtos(usersList);
        this.username = username;
    }

    public int getUsersSize() {
        return usersDtos.size();
    }


    public List<UsersDto> getUsersDtos() {
        return usersDtos;
    }

    public void setUsersDtos(List<UsersDto> usersDtos) {
        this.usersDtos = usersDtos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
