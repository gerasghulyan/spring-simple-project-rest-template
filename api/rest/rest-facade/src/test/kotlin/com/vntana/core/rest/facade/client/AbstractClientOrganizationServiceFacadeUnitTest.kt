package com.vntana.core.rest.facade.client

import com.vntana.core.helper.common.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.rest.ClientOrganizationRestTestHelper
import com.vntana.core.rest.facade.client.impl.ClientOrganizationServiceFacadeImpl
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.service.client.ClientOrganizationService
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
    protected lateinit var clientOrganizationService: ClientOrganizationService

    protected val restHelper = ClientOrganizationRestTestHelper()

    protected val commonTestHelper = ClientOrganizationCommonTestHelper()

    @Before
    fun before() {
        clientOrganizationServiceFacade = ClientOrganizationServiceFacadeImpl(clientOrganizationService)
    }
}