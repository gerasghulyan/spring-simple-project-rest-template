package com.vntana.core.rest.facade.invitation.user.component.impl

import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.helper.unit.user.role.UserRoleCommonTestHelper
import com.vntana.core.helper.user.role.UserRoleRestTestHelper
import com.vntana.core.rest.facade.invitation.user.component.UserRolesPermissionsCheckerComponent
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.user.UserService
import com.vntana.core.service.user.role.UserRoleService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Diana Gevorgyan
 * Date: 11/12/20
 * Time: 4:34 PM
 */
abstract class AbstractUserRolesPermissionsCheckerComponentUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var checkerComponent: UserRolesPermissionsCheckerComponent
    
    protected val userRoleRestTestHelper = UserRoleRestTestHelper()

    protected val userCommonTestHelper = UserCommonTestHelper()

    protected val userRoleCommonTestHelper = UserRoleCommonTestHelper()
    
    @Mock
    protected lateinit var userService: UserService
    
    @Mock
    protected lateinit var userRoleService: UserRoleService

    @Before
    fun prepare() {
        checkerComponent = UserRolesPermissionsCheckerComponentImpl(userService, userRoleService)
    }
}