package com.vntana.core.rest.facade.token

import com.vntana.core.helper.token.TokenRestTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.rest.facade.token.impl.TokeFacadePreconditionCheckerImpl
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
import com.vntana.core.service.token.TokenService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 12:17 PM
 */
abstract class AbstractTokenFacadePreconditionCheckerUnitTest : AbstractServiceFacadeUnitTest() {

    protected lateinit var preconditionChecker: TokeFacadePreconditionChecker

    @Mock
    protected lateinit var tokenService: TokenService

    @Mock
    protected lateinit var invitationOrganizationService: InvitationOrganizationService

    protected val restTestHelper = TokenRestTestHelper()

    protected val commonTestHelper = TokenCommonTestHelper()

    @Before
    fun prepare() {
        preconditionChecker = TokeFacadePreconditionCheckerImpl(tokenService, invitationOrganizationService)
    }
}