package com.vntana.core.rest.facade.token.auth.component.precondition

import com.vntana.core.helper.token.auth.TokenAuthenticationRestTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.token.auth.component.precondition.impl.TokenAuthenticationServiceFacadePreconditionCheckerComponentImpl
import com.vntana.core.service.client.ClientOrganizationService
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Manuk Gharslyan.
 * Date: 11/13/2020
 * Time: 4:35 PM
 */
abstract class AbstractTokenAuthenticationServiceFacadePreconditionCheckerUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var preconditionChecker: TokenAuthenticationServiceFacadePreconditionCheckerComponent

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var clientOrganizationService: ClientOrganizationService

    protected val tokenAuthenticationRestTestHelper = TokenAuthenticationRestTestHelper()

    @Before
    fun prepare() {
        preconditionChecker = TokenAuthenticationServiceFacadePreconditionCheckerComponentImpl(
                userService,
                organizationService,
                clientOrganizationService
        )
    }
}