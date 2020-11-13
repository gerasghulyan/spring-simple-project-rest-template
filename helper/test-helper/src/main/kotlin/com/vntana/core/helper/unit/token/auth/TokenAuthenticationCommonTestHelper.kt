package com.vntana.core.helper.unit.token.auth

import com.vntana.core.domain.organization.Organization
import com.vntana.core.domain.token.TokenAuthentication
import com.vntana.core.domain.user.User
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.token.auth.dto.CreateTokenAuthenticationDto
import com.vntana.core.service.token.auth.dto.CreateTokenAuthenticationWithClientOrganizationDto
import com.vntana.core.service.token.auth.dto.CreateTokenAuthenticationWithOrganizationDto
import java.time.LocalDateTime

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 3:20 PM
 */
open class TokenAuthenticationCommonTestHelper : AbstractCommonTestHelper() {

    private val userCommonTestHelper = UserCommonTestHelper()
    private val organizationCommonTestHelper = OrganizationCommonTestHelper()

    fun buildTokenAuthenticationCreateDto(
            userUuid: String? = uuid(),
            token: String? = uuid(),
            expiration: LocalDateTime? = LocalDateTime.now().plusDays(10)
    ): CreateTokenAuthenticationDto = CreateTokenAuthenticationDto(userUuid, token, expiration)

    fun buildTokenAuthentication(
            token: String = uuid(),
            user: User = userCommonTestHelper.buildUserWithOrganizationOwnerRole(),
            expiration: LocalDateTime? = LocalDateTime.now().plusDays(10)
    ): TokenAuthentication = TokenAuthentication(token, expiration, user)

    fun buildTokenAuthenticationCreateWithOrganizationDto(
            userUuid: String? = uuid(),
            token: String? = uuid(),
            organizationUuid: String? = uuid(),
            expiration: LocalDateTime? = LocalDateTime.now().plusDays(10)
    ): CreateTokenAuthenticationWithOrganizationDto = CreateTokenAuthenticationWithOrganizationDto(userUuid, token, expiration, organizationUuid)

    fun buildTokenAuthenticationCreateWithClientOrganizationDto(
            userUuid: String? = uuid(),
            token: String? = uuid(),
            clientUuid: String? = uuid(),
            expiration: LocalDateTime? = LocalDateTime.now().plusDays(10)
    ): CreateTokenAuthenticationWithClientOrganizationDto = CreateTokenAuthenticationWithClientOrganizationDto(userUuid, token, expiration, clientUuid)

    fun buildTokenAuthenticationWithOrganization(
            token: String = uuid(),
            user: User = userCommonTestHelper.buildUserWithOrganizationOwnerRole(),
            organization: Organization = organizationCommonTestHelper.buildOrganization(),
            expiration: LocalDateTime? = LocalDateTime.now().plusDays(10)
    ): TokenAuthentication = TokenAuthentication(token, expiration, user, organization)
}