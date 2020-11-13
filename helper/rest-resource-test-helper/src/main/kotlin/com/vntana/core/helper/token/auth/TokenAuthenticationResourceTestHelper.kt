package com.vntana.core.helper.token.auth

import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.rest.client.token.auth.TokenAuthenticationResourceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 12:12 PM
 */
@Component
class TokenAuthenticationResourceTestHelper : TokenAuthenticationRestTestHelper() {

    @Autowired
    private lateinit var tokenAuthenticationResourceClient: TokenAuthenticationResourceClient

    @Autowired
    private lateinit var userResourceTestHelper: UserResourceTestHelper

    @Autowired
    private lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper

    fun persistToken(userUuid: String = userResourceTestHelper.persistUser().response().uuid, token: String = uuid()): String {
        val request = buildTokenAuthenticationPersistRequest(userUuid, token)
        tokenAuthenticationResourceClient.persist(request)
        return token
    }

    
    fun persistTokenWithOrganization(
            userUuid: String = userResourceTestHelper.persistUser().response().uuid,
            token: String = uuid(),
            organizationUuid: String = organizationResourceTestHelper.persistOrganization().response().uuid
    ): String {
        val request = buildTokenAuthenticationPersistWithOrganizationRequest(userUuid, token, organizationUuid)
        tokenAuthenticationResourceClient.persistWithOrganization(request)
        return token
    }
}