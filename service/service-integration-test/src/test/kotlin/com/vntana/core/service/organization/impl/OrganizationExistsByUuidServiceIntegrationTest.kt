package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 10:51 AM
 */
class OrganizationExistsByUuidServiceIntegrationTest : AbstractOrganizationServiceIntegrationTest() {

    @Test
    fun `test when does not exist`() {
        assertThat(organizationService.existsByUuid(uuid())).isFalse()
    }

    @Test
    fun `test when found`() {
        val organization = integrationTestHelper.persistOrganization()
        assertThat(organizationService.existsByUuid(organization.uuid)).isTrue()
    }
}