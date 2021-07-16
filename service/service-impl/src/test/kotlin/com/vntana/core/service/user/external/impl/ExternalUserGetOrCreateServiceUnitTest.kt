package com.vntana.core.service.user.external.impl

import com.vntana.core.domain.user.external.ExternalUser
import com.vntana.core.domain.user.external.ExternalUserSource
import com.vntana.core.domain.user.User
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
        val source = ExternalUserSource.OTTO
        val organization = organizationTestHelper.buildOrganization()
        val dto = GetOrCreateExternalUserDto(
            externalUuid,
            source,
            organization
        )
        val user = helper.buildUser()
        val anonymousUser =
            ExternalUser(externalUuid, user, source)
        // expectations
        expect(repository.findByExternalUuidAndSource(eq(dto.externalUuid), eq(dto.source)))
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
        val source = ExternalUserSource.OTTO
        val organization = organizationTestHelper.buildOrganization()
        val dto = GetOrCreateExternalUserDto(
            externalUuid,
            source,
            organization
        )
        val user = helper.buildUser()
        val anonymousUser =
            ExternalUser(externalUuid, user, source)
        // expectations
        expect(repository.findByExternalUuidAndSource(eq(dto.externalUuid), eq(dto.source)))
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