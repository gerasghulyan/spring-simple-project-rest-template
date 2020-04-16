package com.vntana.core.rest.resource.organization.test

import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.model.organization.response.get.model.OrganizationStatusModel
import com.vntana.core.rest.resource.organization.AbstractOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.verify

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 4:43 PM
 */
class OrganizationUpdateWebTest : AbstractOrganizationWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(organizationResourceClient.update(resourceTestHelper.buildUpdateOrganizationRequest(uuid = null)),
                OrganizationErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(organizationResourceClient.update(resourceTestHelper.buildUpdateOrganizationRequest(uuid = "")),
                OrganizationErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(organizationResourceClient.update(resourceTestHelper.buildUpdateOrganizationRequest(name = null)),
                OrganizationErrorResponseModel.MISSING_NAME
        )
        assertBasicErrorResultResponse(organizationResourceClient.update(resourceTestHelper.buildUpdateOrganizationRequest(name = "")),
                OrganizationErrorResponseModel.MISSING_NAME
        )
    }

    @Test
    fun `test when organization not found`() {
        assertBasicErrorResultResponse(organizationResourceClient.update(resourceTestHelper.buildUpdateOrganizationRequest()),
                OrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val newName = uuid()
        val newImageBlobId = uuid()
        val newStatus = OrganizationStatusModel.DISABLED
        val slugName = uuid()
        val organizationUuid = resourceTestHelper.persistOrganization(
                slug = slugName,
                imageBlobId = null
        ).response().uuid
        organizationResourceClient.update(
                resourceTestHelper.buildUpdateOrganizationRequest(uuid = organizationUuid, name = newName, imageBlobId = newImageBlobId, status = newStatus)
        ).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(organizationUuid)
            organizationResourceClient.getByUuid(organizationUuid).let {
                assertThat(it.response().name).isEqualTo(newName)
                assertThat(it.response().imageBlobId).isEqualTo(newImageBlobId)
                assertThat(it.response().status).isEqualTo(newStatus)
                assertThat(it.response().slug).isEqualTo(slugName)
            }
        }
        verify(customerResourceClient).update(ArgumentMatchers.argThat { inRequest ->
            inRequest.organizationName == newName &&
                    inRequest.organizationUuid == organizationUuid
        })
    }

    @Test
    fun `test update when status is null`() {
        val newName = uuid()
        val newImageBlobId = uuid()
        val slugName = uuid()
        val organizationUuid = resourceTestHelper.persistOrganization(
                slug = slugName,
                imageBlobId = null
        ).response().uuid
        organizationResourceClient.update(
                resourceTestHelper.buildUpdateOrganizationRequest(uuid = organizationUuid, name = newName, imageBlobId = newImageBlobId, status = null)
        ).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(organizationUuid)
            organizationResourceClient.getByUuid(organizationUuid).let {
                assertThat(it.response().name).isEqualTo(newName)
                assertThat(it.response().imageBlobId).isEqualTo(newImageBlobId)
                assertThat(it.response().slug).isEqualTo(slugName)
            }
        }
        verify(customerResourceClient).update(ArgumentMatchers.argThat { inRequest ->
            inRequest.organizationName == newName &&
                    inRequest.organizationUuid == organizationUuid
        })
    }
}