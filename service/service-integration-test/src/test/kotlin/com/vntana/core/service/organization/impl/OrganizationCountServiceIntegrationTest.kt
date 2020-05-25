package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 3/24/2020
 * Time: 3:52 PM
 */
class OrganizationCountServiceIntegrationTest : AbstractOrganizationServiceIntegrationTest() {

    @Test
    fun `test count`() {
        for (x in 0..9) {
            integrationTestHelper.persistOrganization()
        }
        assertThat(organizationService.count()).isEqualTo(10L)
    }
}