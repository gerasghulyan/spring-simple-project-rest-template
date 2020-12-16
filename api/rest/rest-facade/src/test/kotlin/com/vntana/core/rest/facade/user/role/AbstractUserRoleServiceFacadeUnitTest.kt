package com.vntana.core.rest.facade.user.role

import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.role.UserRoleCommonTestHelper
import com.vntana.core.helper.user.role.UserRoleRestTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.user.role.component.UserRoleFacadePreconditionCheckerComponent
import com.vntana.core.rest.facade.user.role.component.UserRoleHelperComponent
import com.vntana.core.rest.facade.user.role.impl.UserRoleServiceFacadeImpl
import com.vntana.core.service.client.OrganizationClientService
import com.vntana.core.service.token.auth.TokenAuthenticationService
import com.vntana.core.service.user.role.UserRoleService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 12:06 PM
 */
abstract class AbstractUserRoleServiceFacadeUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var userRoleServiceFacade: UserRoleServiceFacade

    protected val restTestHelper = UserRoleRestTestHelper()
    protected val commonTestHelper = UserRoleCommonTestHelper()
    protected val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    @Mock
    protected lateinit var preconditionChecker: UserRoleFacadePreconditionCheckerComponent

    @Mock
    protected lateinit var tokenAuthenticationService: TokenAuthenticationService

    @Mock
    protected lateinit var userRoleService: UserRoleService

    @Mock
    protected lateinit var organizationClientService: OrganizationClientService

    @Mock
    protected lateinit var userRoleHelperComponent: UserRoleHelperComponent

    @Before
    fun prepare() {
        tokenAuthenticationService
        userRoleServiceFacade = UserRoleServiceFacadeImpl(preconditionChecker,
                tokenAuthenticationService,
                userRoleService,
                organizationClientService,
                userRoleHelperComponent
        )
    }
}