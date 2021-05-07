package com.vntana.core.rest.facade.client

import com.vntana.core.helper.client.ClientOrganizationRestTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.helper.unit.user.role.UserRoleCommonTestHelper
import com.vntana.core.rest.facade.client.component.precondition.ClientOrganizationServiceFacadePreconditionCheckerComponent
import com.vntana.core.rest.facade.client.impl.ClientOrganizationServiceFacadeImpl
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.client.ClientOrganizationService
import com.vntana.core.service.client.mediator.ClientOrganizationLifecycleMediator
import com.vntana.core.service.common.component.SlugValidationComponent
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import com.vntana.core.service.user.role.UserRoleService
import ma.glasnost.orika.MapperFacade
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:58 PM
 */
abstract class AbstractClientOrganizationServiceFacadeUnitTest : AbstractFacadeUnitTest() {
    protected lateinit var clientOrganizationServiceFacade: ClientOrganizationServiceFacade

    @Mock
    protected lateinit var mapperFacade: MapperFacade

    @Mock
    protected lateinit var clientOrganizationService: ClientOrganizationService

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var userRoleService: UserRoleService

    protected val restHelper = ClientOrganizationRestTestHelper()

    protected val userHelper = UserCommonTestHelper()

    protected val userRoleHelper = UserRoleCommonTestHelper()

    protected val clientOrganizationHelper = ClientOrganizationCommonTestHelper()

    protected val commonTestHelper = ClientOrganizationCommonTestHelper()

    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()

    @Mock
    protected lateinit var clientOrganizationLifecycleMediator: ClientOrganizationLifecycleMediator

    @Mock
    protected lateinit var slugValidationComponent: SlugValidationComponent

    @Mock
    protected lateinit var preconditionCheckerComponent: ClientOrganizationServiceFacadePreconditionCheckerComponent

    @Before
    fun before() {
        clientOrganizationServiceFacade = ClientOrganizationServiceFacadeImpl(
                mapperFacade,
                clientOrganizationService,
                organizationService,
                userService,
                userRoleService,
                slugValidationComponent,
                preconditionCheckerComponent,
                clientOrganizationLifecycleMediator
        )
    }
}