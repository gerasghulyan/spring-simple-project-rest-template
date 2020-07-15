package com.vntana.core.helper.unit.token.auth

import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.token.AuthToken
import com.vntana.core.domain.user.User
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.token.auth.dto.AuthTokenCreateDto
import com.vntana.core.service.token.auth.dto.AuthTokenCreateWithOrganizationDto
import java.time.LocalDateTime

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 3:20 PM
 */
open class AuthTokenCommonTestHelper : AbstractCommonTestHelper() {

    private val userCommonTestHelper = UserCommonTestHelper()
    private val organizationCommonTestHelper = OrganizationCommonTestHelper()

    fun buildAuthTokenCreateDto(
            userUuid: String? = uuid(),
            token: String? = uuid(),
            expiration: LocalDateTime? = LocalDateTime.now().plusDays(10)
    ): AuthTokenCreateDto = AuthTokenCreateDto(userUuid, token, expiration)

    fun buildAuthToken(
            token: String = uuid(),
            user: User = userCommonTestHelper.buildUserWithOrganizationOwnerRole(),
            expiration: LocalDateTime? = LocalDateTime.now().plusDays(10)
    ): AuthToken = AuthToken(token, user, expiration)

    fun buildAuthTokenCreateWithOrganizationDto(
            userUuid: String? = uuid(),
            token: String? = uuid(),
            organizationUuid: String? = uuid(),
            expiration: LocalDateTime? = LocalDateTime.now().plusDays(10)
    ): AuthTokenCreateWithOrganizationDto = AuthTokenCreateWithOrganizationDto(userUuid, token, organizationUuid, expiration)

    fun buildAuthTokenWithOrganization(
            token: String = uuid(),
            user: User = userCommonTestHelper.buildUserWithOrganizationOwnerRole(),
            organization: Organization = organizationCommonTestHelper.buildOrganization(),
            expiration: LocalDateTime? = LocalDateTime.now().plusDays(10)
    ): AuthToken = AuthToken(token, user, organization, expiration)
}