package com.monkeyk.os.infrastructure.jdbc;

import com.monkeyk.os.ContextTest;
import com.monkeyk.os.domain.oauth.AccessToken;
import com.monkeyk.os.domain.oauth.ClientDetails;
import com.monkeyk.os.domain.oauth.OauthCode;
import com.monkeyk.os.domain.shared.GuidGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * 15-6-13
 *
 * @author Shengzhao Li
 */
public class OAuthRSJdbcRepositoryTest extends ContextTest {

    @Autowired
    private OAuthRSJdbcRepository oauthJdbcRepository;


    @Test
    public void findAccessTokenByTokenId() throws Exception {
        String tokenId = GuidGenerator.generate();

        final AccessToken accessToken = oauthJdbcRepository.findAccessTokenByTokenId(tokenId);
        assertNull(accessToken);

    }

    @Test
    public void findClientDetailsByClientIdAndResourceIds() throws Exception {

        final ClientDetails clientDetails = oauthJdbcRepository.findClientDetailsByClientIdAndResourceIds("clientId", "resourceId");
        assertNull(clientDetails);

    }


}
