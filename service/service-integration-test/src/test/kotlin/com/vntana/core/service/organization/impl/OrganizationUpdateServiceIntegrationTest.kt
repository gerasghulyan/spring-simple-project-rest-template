package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 3:57 PM
 */
class OrganizationUpdateServiceIntegrationTest : AbstractOrganizationServiceIntegrationTest() {

    @Test
    fun test() {
        val organization = integrationTestHelper.persistOrganization()
        flushAndClear()
        val updateDto = integrationTestHelper.buildUpdateOrganizationDto(uuid = organization.uuid)
        organizationService.update(updateDto).let {
            flushAndClear()
            assertThat(it.uuid).isEqualTo(organization.uuid)
            assertThat(it.slug).isEqualTo(organization.slug)
            assertThat(it.imageBlobId).isEqualTo(updateDto.imageBlobId)
            assertThat(it.name).isEqualTo(updateDto.name)
            assertThat(it.status).isEqualTo(updateDto.status.get())
        }
    }

    @Test
    fun `test update without status`() {
        val organization = integrationTestHelper.persistOrganization()
        flushAndClear()
        val updateDto = integrationTestHelper.buildUpdateOrganizationDto(uuid = organization.uuid, status = null)
        organizationService.update(updateDto).let {
            flushAndClear()
            assertThat(it.uuid).isEqualTo(organization.uuid)
            assertThat(it.slug).isEqualTo(organization.slug)
            assertThat(it.imageBlobId).isEqualTo(updateDto.imageBlobId)
            assertThat(it.name).isEqualTo(updateDto.name)
            assertThat(it.status).isEqualTo(organization.status)
        }
    }
}