package com.vntana.core.service.organization.test

import com.vntana.core.service.organization.AbstractOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
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
                assertThat(it).hasValue(organization)
            }
        }
    }
}