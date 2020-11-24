package com.vntana.core.rest.facade.user.role.component

import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.role.UserRoleCommonTestHelper
import com.vntana.core.helper.user.role.UserRoleRestTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.user.role.component.impl.UserRoleFacadePreconditionCheckerComponentImpl
import com.vntana.core.service.client.OrganizationClientService
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import com.vntana.core.service.user.role.UserRoleService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 11:49 AM
 */
abstract class AbstractUserRoleFacadePreconditionCheckerComponentUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var preconditionChecker: UserRoleFacadePreconditionCheckerComponent

    protected val restTestHelper = UserRoleRestTestHelper()

    protected val commonTestHelper = UserRoleCommonTestHelper()

    protected val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var userRoleService: UserRoleService

    @Mock
    protected lateinit var organizationClientService: OrganizationClientService

    @Before
    fun prepare() {
        preconditionChecker = UserRoleFacadePreconditionCheckerComponentImpl(organizationService, userService, userRoleService, organizationClientService)
    }
}