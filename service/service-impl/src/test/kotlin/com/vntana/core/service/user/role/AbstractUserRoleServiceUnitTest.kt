package com.vntana.core.service.user.role

import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.helper.unit.user.role.UserRoleCommonTestHelper
import com.vntana.core.persistence.user.role.UserRoleRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import com.vntana.core.service.user.role.impl.UserRoleServiceImpl
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 4:06 PM
 */
abstract class AbstractUserRoleServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var userRoleService: UserRoleService

    @Mock
    protected lateinit var userRoleRepository: UserRoleRepository

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var organizationService: OrganizationService

    protected val commonTestHelper = UserRoleCommonTestHelper()
    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()
    protected val userCommonTestHelper = UserCommonTestHelper()

    @Before
    fun prepare() {
        userRoleService = UserRoleServiceImpl(userRoleRepository, userService, organizationService)
    }
}