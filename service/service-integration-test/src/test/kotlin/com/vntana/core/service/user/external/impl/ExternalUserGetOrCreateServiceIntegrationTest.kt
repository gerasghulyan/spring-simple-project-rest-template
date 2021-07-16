package com.vntana.core.service.user.external.impl

import com.vntana.core.service.user.external.AbstractExternalUserServiceIntegrationTest
import com.vntana.core.service.user.external.dto.GetOrCreateExternalUserDto
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

/**
 * Created by Diana Gevorgyan
 * Date: 7/13/2021
 * Time: 4:03 PM
 */
class ExternalUserGetOrCreateServiceIntegrationTest : AbstractExternalUserServiceIntegrationTest() {

    @Test
    fun `test when anonymous user is not present`() {
        // test data
        val externalUuid = uuid()
        val organization = organizationIntegrationTestHelper.persistOrganization()
        val dto = GetOrCreateExternalUserDto(
            externalUuid,
            organization
        )
        assertFalse(externalUserService.findByExternalUuidAndSource(externalUuid).isPresent)
        // test scenario
        externalUserService.getOrCreate(dto).let {
            assertEquals(externalUuid, it.externalUuid)
            assertNotNull(it.targetUser)
        }
    }
}