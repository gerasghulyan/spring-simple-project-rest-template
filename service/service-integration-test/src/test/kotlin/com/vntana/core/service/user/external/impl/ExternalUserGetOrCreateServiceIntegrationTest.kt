package com.vntana.core.service.user.external.impl

import com.vntana.core.domain.user.external.ExternalUserSource
import com.vntana.core.service.user.external.AbstractExternalUserServiceIntegrationTest
import com.vntana.core.service.user.external.dto.GetOrCreateExternalUserDto
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

/**
 * Created by Diana Gevorgyan
 * Date: 7/13/2021
 * Time: 4:03 PM
 */
class ExternalUserGetOrCreateServiceIntegrationTest : AbstractExternalUserServiceIntegrationTest() {

    @Test
    fun `test when anonymous user is not present`() {
        // test data
        val externalUuid = RandomStringUtils.random(8)
        val source = ExternalUserSource.OTTO
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val dto = GetOrCreateExternalUserDto(
            externalUuid,
            source,
            organization
        )
        assertFalse(externalUserService.findByExternalUuidAndSource(externalUuid, source).isPresent)
        // test scenario
        externalUserService.getOrCreate(dto).let {
            assertEquals(externalUuid, it.uuid)
            assertNotNull(it.targetUser)
        }
    }
}