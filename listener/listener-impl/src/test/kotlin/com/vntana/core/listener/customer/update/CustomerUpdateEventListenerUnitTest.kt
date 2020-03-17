package com.vntana.core.listener.customer.update

import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.listener.AbstractListenerUnitTest
import com.vntana.core.listener.commons.EntityLifecycle
import com.vntana.core.listener.organization.OrganizationLifecyclePayload
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import com.vntana.payment.client.customer.PaymentCustomerResourceClient
import com.vntana.payment.reset.model.customer.update.request.CustomerUpdateRequest
import com.vntana.payment.reset.model.customer.update.response.CustomerUpdateResultResponse
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 12/28/19
 * Time: 1:24 PM
 */
class CustomerUpdateEventListenerUnitTest : AbstractListenerUnitTest() {

    private lateinit var customerUpdateEventListener: CustomerUpdateEventListener

    private val organizationCommonTestHelper = OrganizationCommonTestHelper()

    private val userCommonTestHelper = UserCommonTestHelper()

    @Mock
    private lateinit var organizationService: OrganizationService

    @Mock
    private lateinit var customerResourceClient: PaymentCustomerResourceClient

    @Mock
    private lateinit var userService: UserService

    @Before
    fun prepare() {
        customerUpdateEventListener = CustomerUpdateEventListener(organizationService, userService, customerResourceClient)
    }

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { customerUpdateEventListener.handleEvent(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test handleEvent`() {
        val email = uuid()
        val organization = organizationCommonTestHelper.buildOrganization()
        val user = userCommonTestHelper.buildUser(email = email)
        val payload = OrganizationLifecyclePayload(organization, EntityLifecycle.UPDATED)
        resetAll()
        expect(organizationService.getOrganizationOwnerEmail(organization.uuid)).andReturn((email))
        expect(userService.findByEmail(email)).andReturn(Optional.of(user))
        expect(customerResourceClient.update(CustomerUpdateRequest(organization.uuid, user.fullName, organization.name))).andReturn(CustomerUpdateResultResponse())
        replayAll()
        customerUpdateEventListener.handleEvent(payload)
        verifyAll()
    }

    @Test
    fun `test handleEvent user not found`() {
        val email = uuid()
        val organization = organizationCommonTestHelper.buildOrganization()
        val payload = OrganizationLifecyclePayload(organization, EntityLifecycle.UPDATED)
        resetAll()
        expect(organizationService.getOrganizationOwnerEmail(organization.uuid)).andReturn((email))
        expect(userService.findByEmail(email)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { customerUpdateEventListener.handleEvent(payload) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }
}