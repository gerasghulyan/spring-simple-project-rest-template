package com.vntana.core.rest.facade.user.component.precondition

import com.vntana.core.helper.user.UserRestTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.user.component.precondition.impl.UserFacadePreconditionCheckerComponentImpl
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 4/8/20
 * Time: 10:14 AM
 */
abstract class AbstractUserFacadePreconditionCheckerComponentUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var preconditionChecker: UserFacadePreconditionCheckerComponent

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var organizationService: OrganizationService

    protected val restTestHelper = UserRestTestHelper()

    @Before
    fun prepare() {
        preconditionChecker = UserFacadePreconditionCheckerComponentImpl(userService, organizationService)
    }
}