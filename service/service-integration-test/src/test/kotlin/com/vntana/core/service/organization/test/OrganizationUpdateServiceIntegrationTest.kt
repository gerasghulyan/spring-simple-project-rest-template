package com.vntana.core.service.organization.test

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
            assertThat(it.imageId).isEqualTo(updateDto.imageId)
            assertThat(it.name).isEqualTo(updateDto.name)
        }
    }
}