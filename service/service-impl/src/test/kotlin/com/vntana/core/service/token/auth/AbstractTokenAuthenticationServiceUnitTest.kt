package com.vntana.core.service.token.auth

import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.token.auth.TokenAuthenticationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.token.auth.TokenAuthenticationRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.client.ClientOrganizationService
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.token.auth.impl.TokenAuthenticationServiceImpl
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 3:27 PM
 */
abstract class AbstractTokenAuthenticationServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var tokenAuthenticationService: TokenAuthenticationService

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var tokenAuthenticationRepository: TokenAuthenticationRepository

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var clientOrganizationService: ClientOrganizationService

    protected val commonTestHelper = TokenAuthenticationCommonTestHelper()
    protected val userCommonTestHelper = UserCommonTestHelper()
    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()
    protected val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    @Before
    fun prepare() {
        tokenAuthenticationService = TokenAuthenticationServiceImpl(userService, tokenAuthenticationRepository, organizationService, clientOrganizationService)
    }
}