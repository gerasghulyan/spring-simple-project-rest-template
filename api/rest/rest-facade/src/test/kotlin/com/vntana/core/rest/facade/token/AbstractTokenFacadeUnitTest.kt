package com.vntana.core.rest.facade.token

import com.vntana.core.helper.token.TokenRestTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.token.component.TokenFacadePreconditionChecker
import com.vntana.core.rest.facade.token.impl.TokenServiceFacadeImpl
import com.vntana.core.service.token.TokenService
import com.vntana.core.service.token.invitation.organization.TokenInvitationOrganizationService
import com.vntana.core.service.token.invitation.user.TokenInvitationUserService
import ma.glasnost.orika.MapperFacade
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 12:17 PM
 */
abstract class AbstractTokenFacadeUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var tokenServiceFacade: TokenServiceFacade

    @Mock
    protected lateinit var tokenService: TokenService

    @Mock
    protected lateinit var tokenInvitationOrganizationService: TokenInvitationOrganizationService

    @Mock
    protected lateinit var tokenInvitationUserService: TokenInvitationUserService

    @Mock
    protected lateinit var preconditionChecker: TokenFacadePreconditionChecker

    @Mock
    protected lateinit var mapperFacade: MapperFacade

    protected val restTestHelper = TokenRestTestHelper()
    protected val commonTestHelper = TokenCommonTestHelper()

    @Before
    fun prepare() {
        tokenServiceFacade = TokenServiceFacadeImpl(
            tokenService,
            tokenInvitationOrganizationService,
            tokenInvitationUserService,
            preconditionChecker,
            mapperFacade
        )
    }
}