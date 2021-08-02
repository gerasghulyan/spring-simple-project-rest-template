package com.vntana.core.service.user.test.external

import com.vntana.core.domain.user.UserSource
import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import com.vntana.core.service.user.dto.GetOrCreateExternalUserDto
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

/**
 * Created by Diana Gevorgyan
 * Date: 7/13/2021
 * Time: 4:03 PM
 */
class ExternalUserGetOrCreateServiceIntegrationTest : AbstractUserServiceIntegrationTest() {

    @Test
    fun `test when external user is not present`() {
        // test data
        val externalUuid = uuid()
        val email = null
        val fullName = uuid()
        val organization = organizationIntegrationTest.persistOrganization()
        val clientOrganization =
            clientOrganizationIntegrationTestHelper.persistClientOrganization(organizationUuid = organization.uuid)
        val dto = GetOrCreateExternalUserDto(
            externalUuid,
            organization,
            clientOrganization,
            fullName,
            email
        )
        assertFalse(userService.findByUuid(externalUuid).isPresent)
        // test scenario
        userService.getOrCreateExternalUser(dto).let {
            assertEquals(externalUuid, it.uuid)
            assertEquals(fullName, it.fullName)
            assertEquals(email, it.email)
            assertEquals(UserSource.EXTERNAL, it.source)
        }
    }

    @Test
    fun `test when external user is present`() {
        // test data
        val externalUuid = uuid()
        val email = null
        val fullName = uuid()
        val organization = organizationIntegrationTest.persistOrganization()
        val clientOrganization =
            clientOrganizationIntegrationTestHelper.persistClientOrganization(organizationUuid = organization.uuid)
        val userCreateDto = GetOrCreateExternalUserDto(
            externalUuid,
            organization,
            clientOrganization,
            fullName,
            email
        )
        val userGetDto = GetOrCreateExternalUserDto(
            externalUuid,
            organization,
            clientOrganization,
            fullName,
            email
        )
        assertFalse(userService.findByUuid(externalUuid).isPresent)
        userService.getOrCreateExternalUser(userCreateDto)
        assertTrue(userService.findByUuid(externalUuid).isPresent)
        // test scenario
        userService.getOrCreateExternalUser(userGetDto).let {
            assertEquals(externalUuid, it.uuid)
            assertEquals(fullName, it.fullName)
            assertEquals(email, it.email)
            assertEquals(UserSource.EXTERNAL, it.source)
        }
    }
}