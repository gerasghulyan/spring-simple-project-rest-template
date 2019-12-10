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
        val uuid = organizationResourceTestHelper.persistOrganization(slug = slug, name = name).response().uuid
        val response = organizationResourceClient.getByUuid(uuid)
        restHelper.assertBasicSuccessResultResponse(response)
        assertThat(response.response().uuid).isEqualTo(uuid)
        assertThat(response.response().name).isEqualTo(name)
        assertThat(response.response().slug).isEqualTo(slug)
    }
}