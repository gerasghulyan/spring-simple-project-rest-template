package com.vntana.core.rest.resource.organization.test

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
        val imageId = uuid()
        val uuid = resourceTestHelper.persistOrganization(
                slug = slug,
                name = name,
                imageId = imageId
        ).response().uuid
        val response = organizationResourceClient.getByUuid(uuid)
        assertBasicSuccessResultResponse(response)
        assertThat(response.response().uuid).isEqualTo(uuid)
        assertThat(response.response().name).isEqualTo(name)
        assertThat(response.response().slug).isEqualTo(slug)
        assertThat(response.response().imageId).isEqualTo(imageId)
        assertThat(response.response().created).isNotNull()
    }
}