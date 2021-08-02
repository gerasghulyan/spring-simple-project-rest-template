package com.vntana.core.service.user.impl.external

import com.vntana.core.domain.user.User
import com.vntana.core.domain.user.UserSource
import com.vntana.core.service.user.AbstractUserServiceUnitTest
import com.vntana.core.service.user.dto.GetOrCreateExternalUserDto
import org.easymock.EasyMock.*
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 7/13/2021
 * Time: 3:25 PM
 */
class ExternalUserGetOrCreateServiceUnitTest : AbstractUserServiceUnitTest() {

    @Test
    fun `test when anonymous user is not present`() {
        // test data
        resetAll()
        val externalUuid = uuid()
        val organization = organizationHelper.buildOrganization()
        val clientOrganization = clientOrganizationHelper.buildClientOrganization(organization = organization)
        val fullName = uuid()
        val email = null
        val dto = GetOrCreateExternalUserDto(
            externalUuid,
            organization,
            clientOrganization,
            fullName,
            email
        )
        val externalUser = User(fullName, email, null)
        externalUser.source = UserSource.EXTERNAL
        externalUser.uuid = externalUuid
        // expectations
        expect(userRepository.findByUuid(eq(dto.externalUuid)))
            .andReturn(Optional.empty())
        expect(userRepository.save(isA(User::class.java))).andReturn(externalUser)
        replayAll()
        // test scenario
        userService.getOrCreateExternalUser(dto).let {
            assertEquals(externalUuid, it.uuid)
            assertEquals(fullName, it.fullName)
            assertEquals(UserSource.EXTERNAL, it.source)
            assertEquals(email, it.email)
        }
        verifyAll()
    }

    @Test
    fun `test when anonymous user is already present`() {
        // test data
        resetAll()
        val externalUuid = uuid()
        val fullName = uuid()
        val email = null
        val organization = organizationHelper.buildOrganization()
        val clientOrganization = clientOrganizationHelper.buildClientOrganization(organization = organization)
        val dto = GetOrCreateExternalUserDto(
            externalUuid,
            organization,
            clientOrganization,
            email,
            fullName
        )
        val externalUser = User(fullName, null, null)
        externalUser.source = UserSource.EXTERNAL
        externalUser.uuid = externalUuid
        // expectations
        expect(userRepository.findByUuid(eq(dto.externalUuid)))
            .andReturn(Optional.of(externalUser))
        replayAll()
        // test scenario
        userService.getOrCreateExternalUser(dto).let {
            assertEquals(externalUuid, it.uuid)
            assertEquals(fullName, it.fullName)
            assertEquals(UserSource.EXTERNAL, it.source)
            assertEquals(email, it.email)
        }
        verifyAll()
    }
}