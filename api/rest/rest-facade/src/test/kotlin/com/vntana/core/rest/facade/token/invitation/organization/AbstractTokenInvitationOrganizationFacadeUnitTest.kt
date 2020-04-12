package com.vntana.core.rest.facade.token.invitation.organization

import com.vntana.core.helper.token.TokenRestTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.rest.facade.token.TokenFacadePreconditionChecker
import com.vntana.core.rest.facade.token.impl.TokenServiceFacadeImpl
import com.vntana.core.rest.facade.token.invitation.organization.impl.TokenInvitationOrganizationServiceFacadeImpl
import com.vntana.core.service.token.TokenService
import com.vntana.core.service.token.invitation.organization.TokenInvitationOrganizationService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Geras Ghulyan
 * Date: 3/27/20
 * Time: 12:17 PM
 */
abstract class AbstractTokenInvitationOrganizationFacadeUnitTest : AbstractServiceFacadeUnitTest() {

    protected lateinit var tokenInvitationOrganizationServiceFacade: TokenInvitationOrganizationServiceFacade

    @Mock
    protected lateinit var tokenService: TokenService

    @Mock
    protected lateinit var tokenInvitationOrganizationService: TokenInvitationOrganizationService

    @Mock
    protected lateinit var preconditionChecker: TokenFacadePreconditionChecker

    protected val restTestHelper = TokenRestTestHelper()
    protected val commonTestHelper = TokenCommonTestHelper()

    @Before
    fun prepare() {
        tokenInvitationOrganizationServiceFacade = TokenInvitationOrganizationServiceFacadeImpl(
                tokenInvitationOrganizationService, 
                preconditionChecker
        )
    }
}