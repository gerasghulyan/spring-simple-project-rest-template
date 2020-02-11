package com.vntana.core.service.client.mediator

import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.client.mediator.impl.ClientOrganizationLifecycleMediatorImpl
import org.easymock.Mock
import org.junit.Before
import org.springframework.context.ApplicationEventPublisher

/**
 * Created by Geras Ghulyan.
 * Date: 11/1/19
 * Time: 4:33 PM
 */
abstract class AbstractClientOrganizationLifecycleMediatorUnitTest : AbstractServiceUnitTest() {

    protected lateinit var clientOrganizationLifecycleMediator: ClientOrganizationLifecycleMediator

    protected val clientOrganizationCommonTestHelper: ClientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    @Mock
    protected lateinit var applicationEventPublisher: ApplicationEventPublisher

    @Before
    fun prepare() {
        clientOrganizationLifecycleMediator = ClientOrganizationLifecycleMediatorImpl(
                applicationEventPublisher
        )
    }
}