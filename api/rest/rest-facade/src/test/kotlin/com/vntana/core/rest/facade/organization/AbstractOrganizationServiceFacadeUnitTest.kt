package com.vntana.core.rest.facade.organization

import com.vntana.core.helper.organization.OrganizationRestTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.utils.PersistenceUtilityService
import com.vntana.core.rest.facade.organization.impl.OrganizationServiceFacadeImpl
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.organization.mediator.OrganizationLifecycleMediator
import com.vntana.core.service.user.UserService
import ma.glasnost.orika.MapperFacade
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:58 PM
 */
abstract class AbstractOrganizationServiceFacadeUnitTest : AbstractServiceFacadeUnitTest() {
    protected lateinit var organizationServiceFacade: OrganizationServiceFacade

    @Mock
    protected lateinit var mapperFacade: MapperFacade

    @Mock
    protected lateinit var organizationService: OrganizationService

    protected val restHelper = OrganizationRestTestHelper()

    protected val commonTestHelper = OrganizationCommonTestHelper()

    protected val userHelper = UserCommonTestHelper()

    protected val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    @Mock
    protected lateinit var persistenceUtilityService: PersistenceUtilityService

    @Mock
    protected lateinit var organizationLifecycleMediator: OrganizationLifecycleMediator

    @Mock
    protected lateinit var userService: UserService

    @Before
    fun before() {
        organizationServiceFacade = OrganizationServiceFacadeImpl(mapperFacade, organizationService, userService, persistenceUtilityService, organizationLifecycleMediator)
    }
}