package com.vntana.core.rest.facade.user.external

import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.user.external.impl.ExternalUserServiceFacadeImpl
import com.vntana.core.service.client.ClientOrganizationService
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.external.ExternalUserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Diana Gevorgyan
 * Date: 7/16/2021
 * Time: 2:16 PM
 */
abstract class AbstractExternalUserServiceFacadeUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var externalUserServiceFacade: ExternalUserServiceFacade

    protected val userHelper = UserCommonTestHelper()
    protected val organizationHelper = OrganizationCommonTestHelper()
    protected val clientOrganizationTestHelper = ClientOrganizationCommonTestHelper()

    @Mock
    protected lateinit var externalUserService: ExternalUserService

    @Mock
    protected lateinit var organizationService: OrganizationService
    
    @Mock
    protected lateinit var clientOrganizationService: ClientOrganizationService

    @Before
    fun before() {
        externalUserServiceFacade = ExternalUserServiceFacadeImpl(
            externalUserService,
            organizationService,
            clientOrganizationService
        )
    }
}