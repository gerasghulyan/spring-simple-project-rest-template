package com.vntana.core.helper.integration.token

import com.vntana.core.domain.token.ResetPasswordToken
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.token.ResetPasswordTokenService
import com.vntana.core.service.token.TokenService
import com.vntana.core.service.token.dto.CreateResetPasswordTokenDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:00 PM
 */
@Component
class TokenIntegrationTestHelper : TokenCommonTestHelper() {

    private val userCommonTestHelper = UserCommonTestHelper()

    @Autowired
    private lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var resetPasswordTokenService: ResetPasswordTokenService

    fun persistResetPasswordToken(
            dto: CreateResetPasswordTokenDto = buildCreateResetPasswordTokenDto(userUuid = userIntegrationTestHelper.persistUser().uuid)
    ): ResetPasswordToken = resetPasswordTokenService.create(dto)
}