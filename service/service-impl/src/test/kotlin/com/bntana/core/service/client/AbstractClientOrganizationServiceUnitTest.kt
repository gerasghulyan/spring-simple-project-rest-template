package com.bntana.core.service.client

import com.bntana.core.service.AbstractServiceUnitTest
import com.vntana.core.helper.common.client.ClientOrganizationCommonTestHelper
import com.vntana.core.persistence.client.ClientOrganizationRepository
import com.vntana.core.service.client.ClientOrganizationService
import com.vntana.core.service.client.impl.ClientOrganizationServiceImpl
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:59 PM
 */
abstract class AbstractClientOrganizationServiceUnitTest : AbstractServiceUnitTest() {

    @Mock
    protected lateinit var clientOrganizationRepository: ClientOrganizationRepository

    protected lateinit var clientOrganizationService: ClientOrganizationService

    protected val helper: ClientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    @Before
    fun before() {
        clientOrganizationService = ClientOrganizationServiceImpl(clientOrganizationRepository)
    }
}