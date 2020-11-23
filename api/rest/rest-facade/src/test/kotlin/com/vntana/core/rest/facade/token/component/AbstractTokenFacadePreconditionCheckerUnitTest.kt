package com.vntana.core.rest.facade.token.component

import com.vntana.core.helper.token.TokenRestTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.token.component.impl.TokenFacadePreconditionCheckerImpl
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
import com.vntana.core.service.invitation.user.InvitationUserToClientService
import com.vntana.core.service.invitation.user.InvitationUserToOrganizationService
import com.vntana.core.service.token.TokenService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 12:17 PM
 */
abstract class AbstractTokenFacadePreconditionCheckerUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var preconditionChecker: TokenFacadePreconditionChecker

    @Mock
    protected lateinit var tokenService: TokenService

    @Mock
    protected lateinit var invitationOrganizationService: InvitationOrganizationService

    @Mock
    protected lateinit var invitationUserToClientService: InvitationUserToClientService

    @Mock
    protected lateinit var invitationUserToOrganizationService: InvitationUserToOrganizationService

    protected val restTestHelper = TokenRestTestHelper()

    protected val commonTestHelper = TokenCommonTestHelper()

    @Before
    fun prepare() {
        preconditionChecker = TokenFacadePreconditionCheckerImpl(tokenService,
                invitationOrganizationService,
                invitationUserToClientService,
                invitationUserToOrganizationService
        )
    }
}