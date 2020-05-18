package com.vntana.core.service.user.test

import com.vntana.core.service.user.AbstractUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 4:48 PM
 */
class UserFindByEmailAndOrganizationUuidServiceIntegrationTest : AbstractUserServiceIntegrationTest() {
    
    @Test
    fun test() {
        val organization = organizationIntegrationTest.persistOrganization()
        val user = integrationTestHelper.persistUserWithOwnerRole(organizationUuid = organization.uuid)
        flushAndClear()
        userService.findByEmailAndOrganizationUuid(user.email, organization.uuid).let { u ->
            assertThat(u.isPresent).isTrue()
            assertThat(u.get().email).isEqualTo(user.email)
        }
    }
}