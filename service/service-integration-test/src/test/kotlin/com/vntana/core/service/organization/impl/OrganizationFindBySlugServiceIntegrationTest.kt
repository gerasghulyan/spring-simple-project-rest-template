package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 12:20 PM
 */
class OrganizationFindBySlugServiceIntegrationTest : AbstractOrganizationServiceIntegrationTest() {
    @Test
    fun `test findByUuid`() {
        // given
        integrationTestHelper.persistOrganization().let { organization ->
            // when
            flushAndClear()
            organizationService.findBySlug(organization.slug).let {
                // then
                assertThat(it).hasValue(organization)
            }
        }
    }
}