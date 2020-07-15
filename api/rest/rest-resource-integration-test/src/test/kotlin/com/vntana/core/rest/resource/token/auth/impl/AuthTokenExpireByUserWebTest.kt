package com.vntana.core.rest.resource.token.auth.impl

import com.vntana.core.model.token.auth.error.AuthTokenErrorResponseModel
import com.vntana.core.rest.resource.token.auth.AbstractAuthTokenWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 2:11 PM
 */
class AuthTokenExpireByUserWebTest : AbstractAuthTokenWebTest() {

    @Test
    fun `test when user not found`() {
        assertBasicErrorResultResponse(authTokenResourceClient.expireByUser(uuid()), AuthTokenErrorResponseModel.USER_NOT_FOUND)
    }

    @Test
    fun test() {
        val userUuid1 = userResourceTestHelper.persistUser().response().uuid
        val token1 = uuid()
        val token2 = uuid()
        val token3 = uuid()
        resourceTestHelper.persistToken(userUuid = userUuid1, token = token1)
        resourceTestHelper.persistToken(userUuid = userUuid1, token = token2)
        resourceTestHelper.persistToken(token = token3)
        authTokenResourceClient.expireByUser(userUuid1).let {
            assertThat(authTokenResourceClient.isExpired(token1)?.body?.response()?.expired).isTrue()
            assertThat(authTokenResourceClient.isExpired(token2)?.body?.response()?.expired).isTrue()
            assertThat(authTokenResourceClient.isExpired(token3)?.body?.response()?.expired).isFalse()
        }
    }

    @Test
    fun `test when token is with organization`() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val token1 = uuid()
        val token2 = uuid()
        val token3 = uuid()
        resourceTestHelper.persistTokenWithOrganization(userUuid = userUuid, token = token1, organizationUuid = organizationUuid)
        resourceTestHelper.persistTokenWithOrganization(userUuid = userUuid, token = token2, organizationUuid = organizationUuid)
        resourceTestHelper.persistTokenWithOrganization(token = token3)
        authTokenResourceClient.expireByUser(userUuid).let {
            assertThat(authTokenResourceClient.isExpired(token1)?.body?.response()?.expired).isTrue()
            assertThat(authTokenResourceClient.isExpired(token2)?.body?.response()?.expired).isTrue()
            assertThat(authTokenResourceClient.isExpired(token3)?.body?.response()?.expired).isFalse()
        }
    }
}