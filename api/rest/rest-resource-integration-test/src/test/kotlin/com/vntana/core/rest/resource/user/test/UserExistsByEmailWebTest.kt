package com.vntana.core.rest.resource.user.test

import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 4/6/20
 * Time: 10:45 AM
 */
class UserExistsByEmailWebTest : AbstractUserWebTest() {

    @Test
    fun `test does not exist`() {
        val email = uuid()
        userResourceClient.existsByEmail(email).let {
            assertBasicSuccessResultResponse(it)
            Assertions.assertThat(it?.body?.response()?.exists).isFalse()
        }
    }
    
    @Test
    fun `test exists`() {
        val email = resourceHelper.email()
        resourceHelper.persistUser(resourceHelper.buildCreateUserRequest(email = email))
        userResourceClient.existsByEmail(email).let {
            assertBasicSuccessResultResponse(it)
            Assertions.assertThat(it?.body?.response()?.exists).isTrue()
        }
    }
}