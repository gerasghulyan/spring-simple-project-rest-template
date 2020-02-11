package com.vntana.core.rest.facade.client

import com.vntana.core.helper.client.ClientOrganizationRestTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.persistence.utils.PersistenceUtilityService
import com.vntana.core.rest.facade.client.impl.ClientOrganizationServiceFacadeImpl
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.service.client.ClientOrganizationService
import com.vntana.core.service.client.mediator.ClientOrganizationLifecycleMediator
import com.vntana.core.service.common.component.SlugValidationComponent
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import ma.glasnost.orika.MapperFacade
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:58 PM
 */
abstract class AbstractClientOrganizationServiceFacadeUnitTest : AbstractServiceFacadeUnitTest() {
    protected lateinit var clientOrganizationServiceFacade: ClientOrganizationServiceFacade

    @Mock
    protected lateinit var mapperFacade: MapperFacade

    @Mock
    protected lateinit var persistenceUtilityService: PersistenceUtilityService

    @Mock
    protected lateinit var clientOrganizationService: ClientOrganizationService

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var organizationService: OrganizationService

    protected val restHelper = ClientOrganizationRestTestHelper()

    protected val userHelper = UserCommonTestHelper()

    protected val commonTestHelper = ClientOrganizationCommonTestHelper()

    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()

    @Mock
    protected lateinit var clientOrganizationLifecycleMediator: ClientOrganizationLifecycleMediator

    @Mock
    protected lateinit var slugValidationComponent: SlugValidationComponent

    @Before
    fun before() {
        clientOrganizationServiceFacade = ClientOrganizationServiceFacadeImpl(
                mapperFacade,
                persistenceUtilityService,
                clientOrganizationService,
                organizationService,
                userService,
                slugValidationComponent,
                clientOrganizationLifecycleMediator
        )
    }
}