package com.vntana.core.rest.resource.organization.test

import com.vntana.core.model.organization.response.get.model.OrganizationStatusModel
import com.vntana.core.rest.resource.organization.AbstractOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 5:53 PM
 */
class OrganizationGetByUuidWebTest : AbstractOrganizationWebTest() {

    @Test
    fun `test getByUuid`() {
        val slug = uuid()
        val name = uuid()
        val imageBlobId = uuid()
        val uuid = resourceTestHelper.persistOrganization(
                slug = slug,
                name = name,
                imageBlobId = imageBlobId
        ).response().uuid
        val resultResponse = organizationResourceClient.getByUuid(uuid)
        assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().uuid).isEqualTo(uuid)
        assertThat(resultResponse.response().name).isEqualTo(name)
        assertThat(resultResponse.response().slug).isEqualTo(slug)
        assertThat(resultResponse.response().imageBlobId).isEqualTo(imageBlobId)
        assertThat(resultResponse.response().status).isEqualTo(OrganizationStatusModel.ACTIVE)
        assertThat(resultResponse.response().created).isNotNull()
    }
}