package com.vntana.core.service.user.anonymous.impl

import com.vntana.core.domain.user.anonymousUser.AnonymousUser
import com.vntana.core.domain.user.anonymousUser.AnonymousUserSource
import com.vntana.core.domain.user.User
import com.vntana.core.service.user.anonymous.AbstractAnonymousUserServiceUnitTest
import com.vntana.core.service.user.anonymous.dto.GetOrCreateAnonymousUserDto
import org.easymock.EasyMock.*
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 7/13/2021
 * Time: 3:25 PM
 */
class AnonymousUserGetOrCreateServiceUnitTest : AbstractAnonymousUserServiceUnitTest() {

    @Test
    fun `test when anonymous user is not present`() {
        // test data
        resetAll()
        val externalUuid = uuid()
        val source = AnonymousUserSource.OTTO
        val dto = GetOrCreateAnonymousUserDto(externalUuid, source, organization)
        val user = helper.buildUser()
        val anonymousUser =
            AnonymousUser(externalUuid, user, source)
        // expectations
        expect(repository.findByExternalUuidAndAndAnonymousUserSource(eq(dto.externalUuid), eq(dto.source)))
            .andReturn(Optional.empty())
        expect(userRepository.save(isA(User::class.java))).andReturn(user)
        expect(repository.save(isA(AnonymousUser::class.java))).andReturn(anonymousUser)
        replayAll()
        // test scenario
        anonymousUserService.getOrCreate(dto).let {
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
        val source = AnonymousUserSource.OTTO
        val dto = GetOrCreateAnonymousUserDto(externalUuid, source, organization)
        val user = helper.buildUser()
        val anonymousUser =
            AnonymousUser(externalUuid, user, source)
        // expectations
        expect(repository.findByExternalUuidAndAndAnonymousUserSource(eq(dto.externalUuid), eq(dto.source)))
            .andReturn(Optional.of(anonymousUser))
        replayAll()
        // test scenario
        anonymousUserService.getOrCreate(dto).let {
            assertEquals(externalUuid, it.externalUuid)
            assertEquals(user, it.targetUser)
        }
        verifyAll()
    }
}