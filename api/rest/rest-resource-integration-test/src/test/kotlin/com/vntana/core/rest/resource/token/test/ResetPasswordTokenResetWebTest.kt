package com.vntana.core.rest.resource.token.test

import com.sflpro.notifier.api.model.common.result.ResultResponseModel
import com.sflpro.notifier.api.model.email.response.CreateEmailNotificationResponse
import com.vntana.core.helper.rest.user.UserRestTestHelper
import com.vntana.core.model.token.error.TokenErrorResponseModel
import com.vntana.core.rest.client.user.UserResourceClient
import com.vntana.core.rest.resource.token.AbstractTokenWebTest
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired


/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:53 PM
 */
class ResetPasswordTokenResetWebTest : AbstractTokenWebTest() {
    @Autowired
    private lateinit var userResourceClient: UserResourceClient

    private val userRestTestHelper = UserRestTestHelper()

    @Test
    fun `test reset with invalid arguments`() {
        restTestHelper.assertBasicErrorResultResponse(resetPasswordTokenResourceClient.reset(restTestHelper.buildResetPasswordRequest(null)), TokenErrorResponseModel.MISSING_EMAIL)
    }

    @Test
    fun `test reset when user not found`() {
        restTestHelper.assertBasicErrorResultResponse(resetPasswordTokenResourceClient.reset(restTestHelper.buildResetPasswordRequest(uuid())), TokenErrorResponseModel.USER_NOT_FOUND)
    }

    @Test
    fun `test reset`() {
        Mockito.`when`(emailNotificationResourceClient.createEmailNotification(ArgumentMatchers.any())).thenReturn(ResultResponseModel<CreateEmailNotificationResponse>())
        val email = uuid()
        userResourceClient.createUser(userRestTestHelper.buildCreateUserRequest(email = email))
        val response = resetPasswordTokenResourceClient.reset(restTestHelper.buildResetPasswordRequest(email = email))
        Mockito.verify(emailNotificationResourceClient).createEmailNotification(ArgumentMatchers.any())
        restTestHelper.assertBasicSuccessResultResponse(response)
    }
}