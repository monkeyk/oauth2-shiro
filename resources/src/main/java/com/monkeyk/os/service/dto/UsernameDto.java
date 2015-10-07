package com.monkeyk.os.service.dto;

import java.io.Serializable;

/**
 * 2015/9/29
 *
 * @author Shengzhao Li
 */
public class UsernameDto implements Serializable {

    private String clientId;

    private String username;

    public UsernameDto(String clientId, String username) {
        this.clientId = clientId;
        this.username = username;
    }


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
