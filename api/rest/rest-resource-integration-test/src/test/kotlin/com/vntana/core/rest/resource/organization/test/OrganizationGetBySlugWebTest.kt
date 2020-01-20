package com.vntana.core.rest.resource.organization.test

import com.vntana.core.rest.resource.organization.AbstractOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 5:53 PM
 */
class OrganizationGetBySlugWebTest : AbstractOrganizationWebTest() {

    @Test
    fun `test getBySlug`() {
        val slug = uuid()
        val name = uuid()
        val imageBlobId = uuid()
        val uuid = resourceTestHelper
                .persistOrganization(slug = slug, name = name, imageBlobId = imageBlobId).response().uuid
        val response = organizationResourceClient.getBySlug(slug)
        assertBasicSuccessResultResponse(response)
        assertThat(response.response().uuid).isEqualTo(uuid)
        assertThat(response.response().name).isEqualTo(name)
        assertThat(response.response().slug).isEqualTo(slug)
        assertThat(response.response().imageBlobId).isEqualTo(imageBlobId)
    }

    @Test
    fun `test getBySlug null imageBlobId`() {
        val slug = uuid()
        val name = uuid()
        val uuid = resourceTestHelper
                .persistOrganization(slug = slug, name = name, imageBlobId = null).response().uuid
        val response = organizationResourceClient.getBySlug(slug)
        assertBasicSuccessResultResponse(response)
        assertThat(response.response().uuid).isEqualTo(uuid)
        assertThat(response.response().name).isEqualTo(name)
        assertThat(response.response().slug).isEqualTo(slug)
        assertThat(response.response().imageBlobId).isEqualTo(null)
    }
}