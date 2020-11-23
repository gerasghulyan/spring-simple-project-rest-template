package com.vntana.core.service.client.test

import com.vntana.core.service.client.AbstractClientOrganizationServiceIntegrationTest
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 10:51 AM
 */
class ClientOrganizationCreateServiceIntegrationTest : AbstractClientOrganizationServiceIntegrationTest() {

    @Test
    fun `test 2 different organizations with same slug name`() {
        val slug = " ${uuid()} "
        val organization1 = organizationIntegrationTestHelper.persistOrganization()
        val organization2 = organizationIntegrationTestHelper.persistOrganization()
        integrationTestHelper.persistClientOrganization(
                dto = commonTestHelper.buildCreateClientOrganizationDto(name = " ${uuid()} ", slug = slug, organizationUuid = organization1.uuid)
        )
        integrationTestHelper.persistClientOrganization(
                dto = commonTestHelper.buildCreateClientOrganizationDto(name = " ${uuid()} ", slug = slug, organizationUuid = organization2.uuid)
        )
    }

    @Test
    fun `test 1 organization with same slug name`() {
        val slug = " ${uuid()} "
        val organization = organizationIntegrationTestHelper.persistOrganization()
        integrationTestHelper.persistClientOrganization(
                dto = commonTestHelper.buildCreateClientOrganizationDto(name = " ${uuid()} ", slug = slug, organizationUuid = organization.uuid)
        )
        assertThatThrownBy {
            organizationClientService.create(
                    commonTestHelper.buildCreateClientOrganizationDto(name = " ${uuid()} ", slug = slug, organizationUuid = organization.uuid)
            )
        }.isExactlyInstanceOf(IllegalStateException::class.java)
    }

    @Test
    fun `test same organization with different slug names`() {
        val organization = organizationIntegrationTestHelper.persistOrganization()
        organizationClientService.create(
                commonTestHelper.buildCreateClientOrganizationDto(name = " ${uuid()} ", slug = " ${uuid()} ", organizationUuid = organization.uuid)
        )
        flushAndClear()
        val dto = commonTestHelper.buildCreateClientOrganizationDto(name = " ${uuid()} ", slug = " ${uuid()} ", organizationUuid = organization.uuid)
        val clientOrganization = organizationClientService.create(dto)
        flushAndClear()
        assertThat(clientOrganization)
                .hasFieldOrPropertyWithValue("name", StringUtils.trim(dto.name))
                .hasFieldOrPropertyWithValue("slug", StringUtils.trim(dto.slug))
    }
}