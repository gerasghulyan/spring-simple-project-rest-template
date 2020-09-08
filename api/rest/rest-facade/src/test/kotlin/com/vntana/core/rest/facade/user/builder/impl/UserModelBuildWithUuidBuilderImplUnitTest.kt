package com.vntana.core.rest.facade.user.builder.impl

import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:55 PM
 */
class UserModelBuildWithUuidBuilderImplUnitTest : AbstractUserModelBuilderImplUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertIllegalArgumentException { builder.build(emptyString()) }
        verifyAll()
    }

    @Test
    fun test() {
        val user = commonTestHelper.buildUser()
        val userUuid = uuid()
        resetAll()
        expect(userService.getByUuid(userUuid)).andReturn(user)
        replayAll()
        builder.build(userUuid)
                .apply { assertThat(this.uuid).isEqualTo(user.uuid) }
                .apply { assertThat(this.fullName).isEqualTo(user.fullName) }
        verifyAll()
    }
}