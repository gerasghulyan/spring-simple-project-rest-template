package com.vntana.core.rest.facade.auth

import com.vntana.core.helper.organization.OrganizationRestTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.helper.unit.user.role.UserRoleCommonTestHelper
import com.vntana.core.persistence.utils.PersistenceUtilityService
import com.vntana.core.rest.facade.auth.impl.AuthFacadeImpl
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import com.vntana.core.service.user.role.UserRoleService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:58 PM
 */
abstract class AbstractAuthFacadeUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var authFacade: AuthFacade

    @Mock
    protected lateinit var persistenceUtilityService: PersistenceUtilityService

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var userRoleService: UserRoleService

    protected val restHelper = OrganizationRestTestHelper()
    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()
    protected val userHelper = UserCommonTestHelper()
    protected val userRoleCommonTestHelper = UserRoleCommonTestHelper()


    @Before
    fun before() {
        authFacade = AuthFacadeImpl(userService, userRoleService, persistenceUtilityService)
    }
}