package com.vntana.core.rest.resource.organization

import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.rest.client.organization.OrganizationResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import com.vntana.payment.client.customer.PaymentCustomerResourceClient
import com.vntana.payment.reset.model.customer.create.response.CustomerCreateResultResponse
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 5:51 PM
 */
abstract class AbstractOrganizationWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var organizationResourceClient: OrganizationResourceClient

    @Autowired
    protected lateinit var resourceTestHelper: OrganizationResourceTestHelper

    @Autowired
    protected lateinit var userResourceTestHelper: UserResourceTestHelper

    @Autowired
    protected lateinit var customerResourceClient: PaymentCustomerResourceClient

    @Before
    fun prepare() {
        Mockito.reset(customerResourceClient)
        Mockito.`when`(customerResourceClient.create(ArgumentMatchers.any())).thenReturn(CustomerCreateResultResponse())
    }
}