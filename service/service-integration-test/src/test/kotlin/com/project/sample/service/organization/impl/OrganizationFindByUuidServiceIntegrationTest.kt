package com.project.sample.service.organization.impl

import com.project.sample.service.organization.AbstractOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 12:16 PM
 */
class OrganizationFindByUuidServiceIntegrationTest : AbstractOrganizationServiceIntegrationTest() {
    @Test
    fun `test findByUuid`() {
        // given
        integrationTestHelper.persistOrganization().let { organization ->
            // when
            flushAndClear()
            organizationService.findByUuid(organization.uuid).let {
                // then
                assertThat(it.isPresent).isTrue()
                assertThat(it.get()).isEqualTo(organization)
            }
        }
    }
}