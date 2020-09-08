package com.vntana.core.rest.facade.user.builder.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:55 PM
 */
class UserModelBuildBuilderImplUnitTest : AbstractUserModelBuilderImplUnitTest() {

    @Test
    fun test() {
        val user = commonTestHelper.buildUser()
        resetAll()
        replayAll()
        builder.build(user)
                .apply { assertThat(this.uuid).isEqualTo(user.uuid) }
                .apply { assertThat(this.fullName).isEqualTo(user.fullName) }
        verifyAll()
    }
}