package com.vntana.core.service.user.external.impl

import com.vntana.core.domain.user.User
import com.vntana.core.domain.user.external.ExternalUser
import com.vntana.core.service.user.external.AbstractExternalUserServiceUnitTest
import com.vntana.core.service.user.external.dto.GetOrCreateExternalUserDto
import org.easymock.EasyMock.*
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 7/13/2021
 * Time: 3:25 PM
 */
class ExternalUserGetOrCreateServiceUnitTest : AbstractExternalUserServiceUnitTest() {

    @Test
    fun `test when anonymous user is not present`() {
        // test data
        resetAll()
        val externalUuid = uuid()
        val organization = organizationTestHelper.buildOrganization()
        val clientOrganization = organizationClientTestHelper.buildClientOrganization(organization = organization)
        val dto = GetOrCreateExternalUserDto(
            externalUuid,
            organization,
            clientOrganization
        )
        val user = helper.buildUser()
        val anonymousUser =
            ExternalUser(externalUuid, user)
        // expectations
        expect(repository.findByExternalUuid(eq(dto.externalUuid)))
            .andReturn(Optional.empty())
        expect(userRepository.save(isA(User::class.java))).andReturn(user)
        expect(repository.save(isA(ExternalUser::class.java))).andReturn(anonymousUser)
        replayAll()
        // test scenario
        externalUserService.getOrCreate(dto).let {
            assertEquals(externalUuid, it.externalUuid)
            assertEquals(user, it.targetUser)
        }
        verifyAll()
    }

    @Test
    fun `test when anonymous user is already present`() {
        // test data
        resetAll()
        val externalUuid = uuid()
        val organization = organizationTestHelper.buildOrganization()
        val clientOrganization = organizationClientTestHelper.buildClientOrganization(organization = organization)
        val dto = GetOrCreateExternalUserDto(
            externalUuid,
            organization,
            clientOrganization
        )
        val user = helper.buildUser()
        val anonymousUser =
            ExternalUser(externalUuid, user)
        // expectations
        expect(repository.findByExternalUuid(eq(dto.externalUuid)))
            .andReturn(Optional.of(anonymousUser))
        replayAll()
        // test scenario
        externalUserService.getOrCreate(dto).let {
            assertEquals(externalUuid, it.externalUuid)
            assertEquals(user, it.targetUser)
        }
        verifyAll()
    }
}