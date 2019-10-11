package com.vntana.core.rest.facade.user

import com.vntana.core.helper.rest.user.UserRestTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.utils.PersistenceUtilityService
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.rest.facade.user.impl.UserServiceFacadeImpl
import com.vntana.core.service.client.ClientOrganizationService
import com.vntana.core.service.user.UserService
import ma.glasnost.orika.MapperFacade
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

    protected val clientOrganizationHelper = ClientOrganizationCommonTestHelper()

    protected lateinit var userServiceFacade: UserServiceFacade

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var mapperFacade: MapperFacade

    @Mock
    protected lateinit var clientOrganizationService: ClientOrganizationService

    @Mock
    protected lateinit var persistenceUtilityService: PersistenceUtilityService

    @Before
    fun before() {
        userServiceFacade = UserServiceFacadeImpl(userService, mapperFacade, clientOrganizationService, persistenceUtilityService)
    }
}