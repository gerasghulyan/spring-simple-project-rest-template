package com.vntana.core.service.organization.test

import com.vntana.core.service.organization.AbstractOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 12/28/19
 * Time: 11:55 AM
 */
class OrganizationGetOrganizationOwnerEmailServiceIntegrationTest : AbstractOrganizationServiceIntegrationTest() {

    @Test
    fun `test when found`() {
        val userEmail = uuid()
        val organization = integrationTestHelper.persistOrganization()
        val user = userIntegrationTestHelper.persistUser(email = userEmail)
        user.grantOrganizationRole(organization)
        flushAndClear()
        assertThat(organizationService.getOrganizationOwnerEmail(organization.uuid)).isEqualTo(userEmail)
    }
}