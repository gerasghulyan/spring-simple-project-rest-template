package com.vntana.core.rest.facade.user.role.component

import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.helper.unit.user.role.UserRoleCommonTestHelper
import com.vntana.core.helper.user.role.UserRoleRestTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.user.role.component.impl.UserRoleHelperComponentImpl
import com.vntana.core.service.client.OrganizationClientService
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import com.vntana.core.service.user.role.UserRoleService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Vardan Aivazian
 * Date: 16.12.2020
 * Time: 16:44
 */
abstract class AbstractUserRoleFacadeHelperComponentUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var userRoleHelperComponent: UserRoleHelperComponent

    protected val restTestHelper = UserRoleRestTestHelper()

    protected val commonTestHelper = UserRoleCommonTestHelper()

    protected val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    protected val userCommonTestHelper = UserCommonTestHelper()

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
        userRoleHelperComponent = UserRoleHelperComponentImpl(userRoleService)
    }
}