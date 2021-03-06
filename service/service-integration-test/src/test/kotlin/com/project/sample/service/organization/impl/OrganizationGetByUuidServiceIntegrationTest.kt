package com.project.sample.service.organization.impl

import com.project.sample.service.organization.AbstractOrganizationServiceIntegrationTest
import com.project.sample.service.organization.exception.OrganizationNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 12:16 PM
 */
class OrganizationGetByUuidServiceIntegrationTest : AbstractOrganizationServiceIntegrationTest() {
    @Test
    fun `test getByUuid`() {
        // given
        integrationTestHelper.persistOrganization().let { organization ->
            // when
            flushAndClear()
            organizationService.getByUuid(organization.uuid).let {
                // then
                assertThat(it).isEqualTo(organization)
            }
        }
    }

    @Test
    fun `test getByUuid with invalid organization uuid`() {
        assertThatThrownBy { organizationService.getByUuid(uuid()) }
                .isExactlyInstanceOf(OrganizationNotFoundForUuidException::class.java)
    }
}