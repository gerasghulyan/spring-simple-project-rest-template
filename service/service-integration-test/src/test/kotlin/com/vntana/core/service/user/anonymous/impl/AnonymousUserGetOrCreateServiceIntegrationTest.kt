package com.vntana.core.service.user.anonymous.impl

import com.vntana.core.domain.user.anonymousUser.AnonymousUserSource
import com.vntana.core.service.user.anonymous.AbstractAnonymousUserServiceIntegrationTest
import com.vntana.core.service.user.anonymous.dto.GetOrCreateAnonymousUserDto
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

/**
 * Created by Diana Gevorgyan
 * Date: 7/13/2021
 * Time: 4:03 PM
 */
class AnonymousUserGetOrCreateServiceIntegrationTest : AbstractAnonymousUserServiceIntegrationTest() {

    @Test
    fun `test when anonymous user is not present`() {
        // test data
        val externalUuid = RandomStringUtils.random(8)
        val source = AnonymousUserSource.OTTO
        val dto = GetOrCreateAnonymousUserDto(externalUuid, source, organization)
        assertFalse(anonymousUserService.findByExternalUuidAndSource(externalUuid, source).isPresent)
        // test scenario
        anonymousUserService.getOrCreate(dto).let {
            assertEquals(externalUuid, it.externalUuid)
            assertNotNull(it.targetUser)
        }
    }

//    @Test
//    fun `test when anonymous user is already present`() {
//        // test data
//        val externalUuid = RandomStringUtils.random(8)
//        val source = AnonymousUserSource.OTTO
//        val dto = GetOrCreateAnonymousUserDto(externalUuid, source)
//        val user = integrationTestHelper.persistUser()
//        flushAndClear()
//        repository.save(AnonymousUser(externalUuid, user, source))
//        flushAndClear()
//        assertTrue(anonymousUserService.findByExternalUuidAndSource(externalUuid, source).isPresent)
//        // test scenario
//        anonymousUserService.getOrCreate(dto).let {
//            assertEquals(externalUuid, it.externalUuid)
//            as(it.targetUser)
//        }
//    }
}