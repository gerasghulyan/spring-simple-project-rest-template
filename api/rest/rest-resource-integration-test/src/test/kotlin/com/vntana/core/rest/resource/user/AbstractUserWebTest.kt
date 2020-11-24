package com.vntana.core.rest.resource.user

import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient
import com.sflpro.notifier.api.model.common.result.ResultResponseModel
import com.sflpro.notifier.api.model.email.response.CreateEmailNotificationResponse
import com.vntana.core.helper.client.ClientOrganizationResourceTestHelper
import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.helper.user.role.UserRoleResourceTestHelper
import com.vntana.core.rest.client.user.UserResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import com.vntana.payment.client.customer.PaymentCustomerResourceClient
import com.vntana.payment.reset.model.customer.create.response.CustomerCreateResultResponse
import com.vntana.payment.reset.model.customer.create.response.model.CustomerCreateResponseModel
import com.vntana.payment.reset.model.customer.update.response.CustomerUpdateResultResponse
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.reset
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:24 AM
 */
abstract class AbstractUserWebTest : AbstractWebIntegrationTest() {
    @Autowired
    protected lateinit var resourceHelper: UserResourceTestHelper

    @Autowired
    lateinit var userResourceClient: UserResourceClient

    @Autowired
    protected lateinit var emailNotificationResourceClient: EmailNotificationResourceClient

    @Autowired
    protected lateinit var customerResourceClient: PaymentCustomerResourceClient

    @Autowired
    protected lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper

    @Autowired
    protected lateinit var clientOrganizationResourceTestHelper: ClientOrganizationResourceTestHelper

    @Autowired
    protected lateinit var userRoleResourceTestHelper: UserRoleResourceTestHelper

    @Before
    fun prepare() {
        reset(emailNotificationResourceClient)
        reset(customerResourceClient)
        `when`(emailNotificationResourceClient.createEmailNotification(ArgumentMatchers.any())).thenReturn(ResultResponseModel<CreateEmailNotificationResponse>())
        `when`(customerResourceClient.create(ArgumentMatchers.any()))
                .thenReturn(CustomerCreateResultResponse(CustomerCreateResponseModel(uuid())))
        `when`(customerResourceClient.update(ArgumentMatchers.any()))
                .thenReturn(CustomerUpdateResultResponse())
    }
}