package com.monkeyk.os.domain.rs;

import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.shared.Repository;

/**
 * 2015/10/7
 *
 * @author Shengzhao Li
 */

public interface OAuthRSRepository extends Repository {

    AccessToken findAccessTokenByTokenId(String tokenId);

    ClientDetails findClientDetailsByClientIdAndResourceIds(String clientId, String resourceIds);
}