package com.vntana.core.rest.facade.user.role

import com.vntana.core.helper.unit.user.role.UserRoleCommonTestHelper
import com.vntana.core.helper.user.role.UserRoleRestTestHelper
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.rest.facade.user.role.component.UserRoleFacadePreconditionCheckerComponent
import com.vntana.core.rest.facade.user.role.impl.UserRoleServiceFacadeImpl
import com.vntana.core.service.user.role.UserRoleService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 12:06 PM
 */
abstract class AbstractUserRoleServiceFacadeUnitTest : AbstractServiceFacadeUnitTest() {

    protected lateinit var userRoleServiceFacade: UserRoleServiceFacade

    protected val restTestHelper = UserRoleRestTestHelper()
    protected val commonTestHelper = UserRoleCommonTestHelper()

    @Mock
    protected lateinit var preconditionChecker: UserRoleFacadePreconditionCheckerComponent

    @Mock
    protected lateinit var userRoleService: UserRoleService

    @Before
    fun prepare() {
        userRoleServiceFacade = UserRoleServiceFacadeImpl(preconditionChecker, userRoleService)
    }
}