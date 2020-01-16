package com.vntana.core.rest.resource.client.test

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.rest.resource.client.AbstractClientOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 5:53 PM
 */
class ClientOrganizationUpdateWebTest : AbstractClientOrganizationWebTest() {

    @Test
    fun `test update with invalid arguments`() {
        clientOrganizationResourceClient.update(clientOrganizationResourceTestHelper.buildUpdateClientOrganizationRequest(uuid = null)).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_UUID)

        }
        clientOrganizationResourceClient.update(clientOrganizationResourceTestHelper.buildUpdateClientOrganizationRequest(uuid = "")).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_UUID)

        }
        clientOrganizationResourceClient.update(clientOrganizationResourceTestHelper.buildUpdateClientOrganizationRequest(slug = null)).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_SLUG)

        }
        clientOrganizationResourceClient.update(clientOrganizationResourceTestHelper.buildUpdateClientOrganizationRequest(slug = "")).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_SLUG)

        }
        clientOrganizationResourceClient.update(clientOrganizationResourceTestHelper.buildUpdateClientOrganizationRequest(name = null)).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_NAME)
        }
        clientOrganizationResourceClient.update(clientOrganizationResourceTestHelper.buildUpdateClientOrganizationRequest(name = "")).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_NAME)
        }
    }

    @Test
    fun `test when slug name already exists for same organization`() {
        val slugName = uuid()
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val clientOrganization1Uuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid).response().uuid
        clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid, slug = slugName).response().uuid
        val request = clientOrganizationResourceTestHelper.buildUpdateClientOrganizationRequest(uuid = clientOrganization1Uuid, slug = slugName)
        clientOrganizationResourceClient.update(request).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.SLUG_ALREADY_EXISTS)
        }
    }

    @Test
    fun test() {
        val slugName = uuid()
        clientOrganizationResourceTestHelper.persistClientOrganization(slug = slugName)
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val clientOrganizationUuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid).response().uuid
        val request = clientOrganizationResourceTestHelper.buildUpdateClientOrganizationRequest(uuid = clientOrganizationUuid, slug = slugName)
        val response = clientOrganizationResourceClient.update(request)
        clientOrganizationResourceTestHelper.assertBasicSuccessResultResponse(response)
        assertThat(response.response().uuid).isNotBlank()
    }
}