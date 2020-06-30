package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceIntegrationTest
import com.vntana.core.service.organization.exception.OrganizationNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
                assertThat(it.isPresent).isTrue()
                assertThat(it.get()).isEqualTo(organization)
            }
        }
    }
}