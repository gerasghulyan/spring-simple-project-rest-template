package com.vntana.core.rest.resource.organization.test

import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.rest.resource.organization.AbstractOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

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
        val newImageId = uuid()
        val slugName = uuid()
        val organizationUuid = resourceTestHelper.persistOrganization(
                slug = slugName,
                imageId = null
        ).response().uuid

        organizationResourceClient.update(
                resourceTestHelper.buildUpdateOrganizationRequest(uuid = organizationUuid, name = newName, imageId = newImageId)
        ).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(organizationUuid)
            organizationResourceClient.getByUuid(organizationUuid).let {
                assertThat(it.response().name).isEqualTo(newName)
                assertThat(it.response().imageId).isEqualTo(newImageId)
                assertThat(it.response().slug).isEqualTo(slugName)
            }
        }
    }
}