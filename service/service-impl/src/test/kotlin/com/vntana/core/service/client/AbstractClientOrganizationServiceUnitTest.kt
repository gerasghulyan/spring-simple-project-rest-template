package com.vntana.core.service.client

import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.persistence.client.ClientOrganizationRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.client.impl.ClientOrganizationServiceImpl
import com.vntana.core.service.organization.OrganizationService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:59 PM
 */
abstract class AbstractClientOrganizationServiceUnitTest : AbstractServiceUnitTest() {

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var clientOrganizationRepository: ClientOrganizationRepository

    protected lateinit var clientOrganizationService: ClientOrganizationService

    protected val helper: ClientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    protected val organizationTestHelper = OrganizationCommonTestHelper()

    @Before
    fun before() {
        clientOrganizationService = ClientOrganizationServiceImpl(organizationService, clientOrganizationRepository)
    }
}