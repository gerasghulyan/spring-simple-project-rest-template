package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 3/24/2020
 * Time: 3:21 PM
 */
class OrganizationGetAllServiceIntegrationTest : AbstractOrganizationServiceIntegrationTest() {

    @Test
    fun `test getAll`() {
        val organizations = listOf(
                integrationTestHelper.persistOrganization(),
                integrationTestHelper.persistOrganization(),
                integrationTestHelper.persistOrganization(),
                integrationTestHelper.persistOrganization(),
                integrationTestHelper.persistOrganization()
        )
        val dto = integrationTestHelper.buildGetAllOrganizationDto()
        organizationService.getAll(dto).let {
            assertThat(it.content).isNotEmpty
            assertThat(it.totalElements).isEqualTo(dto.size.toLong())
            assertThat(it.totalPages).isEqualTo(dto.page + 1)
            assertThat(it).containsExactlyElementsOf(organizations)
        }
    }
}