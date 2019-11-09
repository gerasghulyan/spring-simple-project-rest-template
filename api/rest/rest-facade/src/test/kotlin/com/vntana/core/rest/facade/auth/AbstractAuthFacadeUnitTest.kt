package com.vntana.core.rest.facade.auth

import com.vntana.core.helper.organization.OrganizationRestTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.utils.PersistenceUtilityService
import com.vntana.core.rest.facade.auth.impl.AuthFacadeImpl
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:58 PM
 */
abstract class AbstractAuthFacadeUnitTest : AbstractServiceFacadeUnitTest() {

    protected lateinit var authFacade: AuthFacade

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var persistenceUtilityService: PersistenceUtilityService

    @Mock
    protected lateinit var userService: UserService

    protected val restHelper = OrganizationRestTestHelper()

    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()

    protected val userHelper = UserCommonTestHelper()


    @Before
    fun before() {
        authFacade = AuthFacadeImpl(userService, organizationService, persistenceUtilityService)
    }
}