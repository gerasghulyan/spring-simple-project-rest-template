package com.vntana.core.listener.customer

import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.listener.AbstractListenerUnitTest
import com.vntana.core.listener.commons.EntityLifecycle
import com.vntana.core.listener.organization.OrganizationLifecyclePayload
import com.vntana.core.service.organization.OrganizationService
import com.vntana.payment.client.customer.CustomerResourceClient
import com.vntana.payment.reset.model.customer.create.request.CustomerCreateRequest
import com.vntana.payment.reset.model.customer.create.response.CustomerCreateResultResponse
import org.assertj.core.api.Assertions
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.junit.Before
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 12/28/19
 * Time: 1:24 PM
 */
class CustomerCreationEventListenerUnitTest : AbstractListenerUnitTest() {

    private lateinit var customerCreationEventListener: CustomerCreationEventListener

    private val organizationCommonTestHelper: OrganizationCommonTestHelper = OrganizationCommonTestHelper()

    @Mock
    private lateinit var organizationService: OrganizationService

    @Mock
    private lateinit var customerResourceClient: CustomerResourceClient

    @Before
    fun prepare() {
        customerCreationEventListener = CustomerCreationEventListener(organizationService, customerResourceClient)
    }

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        Assertions.assertThatThrownBy { customerCreationEventListener.handleEvent(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test handleEvent`() {
        val email = uuid()
        val organization = organizationCommonTestHelper.buildOrganization()
        val payload = OrganizationLifecyclePayload(organization, EntityLifecycle.CREATED)
        resetAll()
        expect(organizationService.getOrganizationOwnerEmail(organization.uuid)).andReturn((email))
        expect(customerResourceClient.create(CustomerCreateRequest(organization.uuid, email))).andReturn(CustomerCreateResultResponse())
        replayAll()
        customerCreationEventListener.handleEvent(payload)
        verifyAll()
    }
}