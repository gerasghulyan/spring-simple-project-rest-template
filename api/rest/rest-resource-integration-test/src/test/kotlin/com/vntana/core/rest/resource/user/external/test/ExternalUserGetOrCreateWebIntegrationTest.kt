package com.vntana.core.rest.resource.user.external.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.model.user.external.request.GetOrCreateExternalUserRequest
import com.vntana.core.rest.resource.user.external.AbstractExternalUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 7/16/2021
 * Time: 3:04 PM
 */
class ExternalUserGetOrCreateWebIntegrationTest : AbstractExternalUserWebTest() {

    @Test
    fun `test when external uuid is null`() {
        assertBasicErrorResultResponse(
            externalUserClient.getOrCreateExternalUser(
                GetOrCreateExternalUserRequest(
                    null,
                    uuid(),
                    uuid()
                )
            ), UserErrorResponseModel.MISSING_UUID
        )
    }

    @Test
    fun `test when organization uuid is null`() {
        assertBasicErrorResultResponse(
            externalUserClient.getOrCreateExternalUser(
                GetOrCreateExternalUserRequest(
                    uuid(),
                    null,
                    uuid()
                )
            ), UserErrorResponseModel.MISSING_ORGANIZATION
        )
    }

    @Test
    fun `test when organization not found`() {
        assertBasicErrorResultResponse(
            externalUserClient.getOrCreateExternalUser(
                GetOrCreateExternalUserRequest(
                    uuid(),
                    uuid(),
                    uuid()
                )
            ), UserErrorResponseModel.ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun test() {
        // test data
        val organization = organizationResourceTestHelper.persistOrganization().response()
        val clientOrganization =
            clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organization.uuid)
                .response()
        val externalUuid = uuid()
        val request = GetOrCreateExternalUserRequest(
            externalUuid,
            organization.uuid,
            clientOrganization.uuid
        )
        // test scenario
        externalUserClient.getOrCreateExternalUser(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body!!.response().externalUuid).isEqualTo(externalUuid)
            assertThat(it.body!!.response().userUuid).isNotBlank()
        }
    }
}