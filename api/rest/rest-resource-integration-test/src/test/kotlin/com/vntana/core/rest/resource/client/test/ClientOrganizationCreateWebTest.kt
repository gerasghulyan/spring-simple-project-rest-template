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
        clientOrganizationResourceClient.create(clientOrganizationResourceTestHelper.buildCreateClientOrganizationRequest(slug = null)).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_SLUG)

        }
        clientOrganizationResourceClient.create(clientOrganizationResourceTestHelper.buildCreateClientOrganizationRequest(slug = "")).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_SLUG)

        }
        clientOrganizationResourceClient.create(clientOrganizationResourceTestHelper.buildCreateClientOrganizationRequest(name = null)).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_NAME)
        }
        clientOrganizationResourceClient.create(clientOrganizationResourceTestHelper.buildCreateClientOrganizationRequest(name = "")).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_NAME)
        }
        clientOrganizationResourceClient.create(clientOrganizationResourceTestHelper.buildCreateClientOrganizationRequest(imageId = null)).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_IMAGE_ID)
        }
        clientOrganizationResourceClient.create(clientOrganizationResourceTestHelper.buildCreateClientOrganizationRequest(imageId = "")).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_IMAGE_ID)
        }
    }

    @Test
    fun `test when slug name already exists for same organization`() {
        val slugName = uuid()
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid, slug = slugName)
        val request = clientOrganizationResourceTestHelper.buildCreateClientOrganizationRequest(organizationUuid = organizationUuid, slug = slugName)
        clientOrganizationResourceClient.create(request).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.SLUG_ALREADY_EXISTS)
        }
    }

    @Test
    fun `test create same slug different organizations`() {
        val slugName = uuid()
        clientOrganizationResourceTestHelper.persistClientOrganization(slug = slugName)
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val request = clientOrganizationResourceTestHelper.buildCreateClientOrganizationRequest(organizationUuid = organizationUuid, slug = slugName)
        val response = clientOrganizationResourceClient.create(request)
        clientOrganizationResourceTestHelper.assertBasicSuccessResultResponse(response)
        assertThat(response.response().uuid).isNotBlank()
    }
}