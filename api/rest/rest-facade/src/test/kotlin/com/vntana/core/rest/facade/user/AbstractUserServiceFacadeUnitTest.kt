package com.vntana.core.rest.facade.user

import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.helper.user.UserRestTestHelper
import com.vntana.core.persistence.utils.PersistenceUtilityService
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.rest.facade.user.impl.UserServiceFacadeImpl
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/11/19
 * Time: 4:38 PM
 */
abstract class AbstractUserServiceFacadeUnitTest : AbstractServiceFacadeUnitTest() {

    protected val restHelper = UserRestTestHelper()

    protected val userHelper = UserCommonTestHelper()

    protected val organizationHelper = OrganizationCommonTestHelper()

    protected lateinit var userServiceFacade: UserServiceFacade

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var persistenceUtilityService: PersistenceUtilityService

    @Before
    fun before() {
        userServiceFacade = UserServiceFacadeImpl(userService, organizationService, persistenceUtilityService)
    }
}