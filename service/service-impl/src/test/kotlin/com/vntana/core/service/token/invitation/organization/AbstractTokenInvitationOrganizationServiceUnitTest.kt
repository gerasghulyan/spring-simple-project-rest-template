package com.vntana.core.service.token.invitation.organization

import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.persistence.token.TokenInvitationOrganizationRepository
import com.vntana.core.persistence.token.TokenRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
import com.vntana.core.service.token.invitation.organization.impl.TokenInvitationOrganizationServiceImpl
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Geras Ghulyan
 * Date: 12.04.20
 * Time: 02:00
 */
abstract class AbstractTokenInvitationOrganizationServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var tokenInvitationOrganizationService: TokenInvitationOrganizationService

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var invitationOrganizationService: InvitationOrganizationService

    @Mock
    protected lateinit var tokenRepository: TokenRepository

    @Mock
    protected lateinit var tokenInvitationOrganizationRepository: TokenInvitationOrganizationRepository
    
    protected val helper = TokenCommonTestHelper()

    protected val invitationOrganizationCommonTestHelper = InvitationOrganizationCommonTestHelper()
    
    @Before
    fun before() {
        tokenInvitationOrganizationService = TokenInvitationOrganizationServiceImpl(
                invitationOrganizationService,
                tokenRepository,
                tokenInvitationOrganizationRepository
        )
    }

}