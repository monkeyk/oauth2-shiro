package com.monkeyk.os.domain.oauth;

import com.monkeyk.os.infrastructure.DateUtils;
import org.apache.oltu.oauth2.common.domain.client.BasicClientInfo;
import org.apache.oltu.oauth2.common.message.types.GrantType;


import java.util.Date;

/**
 * 15-6-12
 * <p/>
 * DBTable: oauth_client_details
 * <p/>
 * 定义OAuth2 的客户端(client details)
 *
 * @author Shengzhao Li
 */
public class ClientDetails extends BasicClientInfo {


    /**
     * 客户端所拥有的资源ID(resource-id), 至少有一个,
     * 多个ID时使用逗号(,)分隔, 如:  os,mobile
     */
    private String resourceIds;

    private String scope;

    /**
     * 客户端所支持的授权模式(grant_type),
     * 至少一个, 多个值时使用逗号(,)分隔, 如: password,refresh_token
     */
    private String grantTypes;

    /*
   * Shiro roles
   * */
    private String roles;

    /**
     * access_token 的有效时长, 单位: 秒.
     * 若不填或为空(null)则使用默认值: 12小时
     */
    private Integer accessTokenValidity;

    /**
     * refresh_token的 有效时长, 单位: 秒
     * 若不填或为空(null)则使用默认值: 30天
     */
    private Integer refreshTokenValidity;

    /**
     * 该 客户端是否为授信任的,
     * 若为信任的,, 则在 grant_type = authorization_code 时将跳过用户同意/授权 步骤
     */
    private boolean trusted = false;

    /**
     * 逻辑删除的标识, true表示已经删除
     */
    private boolean archived = false;

    /**
     * 创建时间
     */
    private Date createTime = DateUtils.now();


    public ClientDetails() {
    }


    public String resourceIds() {
        return resourceIds;
    }

    public ClientDetails resourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
        return this;
    }

    public String scope() {
        return scope;
    }

    public ClientDetails scope(String scope) {
        this.scope = scope;
        return this;
    }

    public String grantTypes() {
        return grantTypes;
    }

    public ClientDetails grantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
        return this;
    }

    public String roles() {
        return roles;
    }

    public ClientDetails roles(String roles) {
        this.roles = roles;
        return this;
    }

    public Integer accessTokenValidity() {
        return accessTokenValidity;
    }

    public ClientDetails accessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
        return this;
    }

    public Integer refreshTokenValidity() {
        return refreshTokenValidity;
    }

    public ClientDetails refreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
        return this;
    }

    public boolean trusted() {
        return trusted;
    }

    public ClientDetails trusted(boolean trusted) {
        this.trusted = trusted;
        return this;
    }

    public boolean archived() {
        return archived;
    }

    public ClientDetails archived(boolean archived) {
        this.archived = archived;
        return this;
    }

    public Date createTime() {
        return createTime;
    }

    public ClientDetails createTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public boolean supportRefreshToken() {
        return this.grantTypes != null && this.grantTypes.contains(GrantType.REFRESH_TOKEN.toString());
    }
}
