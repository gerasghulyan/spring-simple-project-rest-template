package com.vntana.core.rest.resource.client.test

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.rest.resource.client.AbstractClientOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 5:53 PM
 */
class ClientOrganizationCreateWebTest : AbstractClientOrganizationWebTest() {

    @Test
    fun `test create with invalid arguments`() {
        val response0 = clientOrganizationResourceClient.create(
                helper.buildCreateClientOrganizationRequest(organizationUuid = null)
        )
        assertBasicErrorResultResponse(response0, ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID)
        val response1 = clientOrganizationResourceClient.create(
                helper.buildCreateClientOrganizationRequest(slug = null)
        )
        assertBasicErrorResultResponse(response1, ClientOrganizationErrorResponseModel.MISSING_SLUG)
        val response2 = clientOrganizationResourceClient.create(
                helper.buildCreateClientOrganizationRequest(name = null)
        )
        assertBasicErrorResultResponse(response2, ClientOrganizationErrorResponseModel.MISSING_NAME)
    }

    @Test
    fun `test when slug name already exists for same organization`() {
        val slugName = uuid()
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        helper.persistClientOrganization(organizationUuid = organizationUuid, slug = slugName)
        val request = helper.buildCreateClientOrganizationRequest(organizationUuid = organizationUuid, slug = slugName)
        clientOrganizationResourceClient.create(request).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.SLUG_ALREADY_EXISTS)
        }
    }

    @Test
    fun `test create same slug different organizations`() {
        val slugName = uuid()
        helper.persistClientOrganization(slug = slugName)
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val request = helper.buildCreateClientOrganizationRequest(organizationUuid = organizationUuid, slug = slugName)
        val response = clientOrganizationResourceClient.create(request)
        helper.assertBasicSuccessResultResponse(response)
        assertThat(response.response().uuid).isNotBlank()
    }
}