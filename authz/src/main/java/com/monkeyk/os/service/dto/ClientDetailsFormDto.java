package com.monkeyk.os.service.dto;

import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.shared.GuidGenerator;
import com.monkeyk.os.domain.users.Roles;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import java.util.ArrayList;
import java.util.List;

/**
 * 2016/6/8
 *
 * @author Shengzhao Li
 */
public class ClientDetailsFormDto extends ClientDetailsDto {


    private static final long serialVersionUID = -5827571696766683709L;


    private List<RolesDto> rolesDtoList = new ArrayList<>();


    public ClientDetailsFormDto() {
        //set  default value
        this.setClientId(GuidGenerator.generateClientId());
        this.setClientSecret(GuidGenerator.generateClientSecret());
    }

    public ClientDetailsFormDto(ClientDetails details) {
        super(details);
    }

    public ClientDetailsFormDto(List<Roles> rolesList) {
        this();
        this.rolesDtoList = RolesDto.toDtos(rolesList);
    }


    public GrantType[] getAvailableGrantTypes() {
        return GrantType.values();
    }

    public List<RolesDto> getRolesDtoList() {
        return rolesDtoList;
    }

    public void setRolesDtoList(List<RolesDto> rolesDtoList) {
        this.rolesDtoList = rolesDtoList;
    }

    public ClientDetails newClientDetails() {
        final ClientDetails clientDetails = new ClientDetails();
        clientDetails.setClientId(getClientId());
        clientDetails.setClientSecret(getClientSecret());
        clientDetails.setClientUri(getClientUri());
        clientDetails.setName(getClientName());

        clientDetails.setDescription(getDescription());
        clientDetails.setIconUri(getClientIconUri());
        clientDetails.setRedirectUri(getRedirectUri());
        clientDetails.resourceIds(getResourceIds());

        clientDetails.scope(getScope());
        clientDetails.grantTypes(getGrantTypes());
        final String roles = getRoles();
        clientDetails.roles(StringUtils.isEmpty(roles) ? null : roles);
        clientDetails.accessTokenValidity(getAccessTokenValidity());

        clientDetails.refreshTokenValidity(getRefreshTokenValidity());
        clientDetails.trusted(isTrusted());
        return clientDetails;
    }
}
