package com.vntana.core.rest.resource.organization

import com.vntana.core.helper.client.ClientOrganizationResourceTestHelper
import com.vntana.core.helper.invitation.organization.InvitationOrganizationResourceTestHelper
import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.helper.token.TokenResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.helper.user.role.UserRoleResourceTestHelper
import com.vntana.core.indexation.producer.organization.OrganizationUuidAwareActionProducer
import com.vntana.core.rest.client.invitation.organization.InvitationOrganizationResourceClient
import com.vntana.core.rest.client.organization.OrganizationResourceClient
import com.vntana.core.rest.client.user.UserResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import com.vntana.payment.client.customer.PaymentCustomerResourceClient
import com.vntana.payment.reset.model.customer.create.response.CustomerCreateResultResponse
import com.vntana.payment.reset.model.customer.update.response.CustomerUpdateResultResponse
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
    protected lateinit var customerResourceClient: PaymentCustomerResourceClient

    @Autowired
    protected lateinit var userResourceClient: UserResourceClient

    @Autowired
    protected lateinit var invitationOrganizationResourceClient: InvitationOrganizationResourceClient

    @Autowired
    protected lateinit var resourceTestHelper: OrganizationResourceTestHelper

    @Autowired
    protected lateinit var userResourceTestHelper: UserResourceTestHelper

    @Autowired
    protected lateinit var organizationUuidAwareActionProducer: OrganizationUuidAwareActionProducer

    @Autowired
    protected lateinit var invitationOrganizationResourceTestHelper: InvitationOrganizationResourceTestHelper

    @Autowired
    protected lateinit var tokenResourceTestHelper: TokenResourceTestHelper

    @Autowired
    protected lateinit var clientOrganizationResourceTestHelper : ClientOrganizationResourceTestHelper

    @Autowired
    protected lateinit var organizationResourceTestHelper : OrganizationResourceTestHelper

    @Autowired
    protected lateinit var userRoleResourceTestHelper: UserRoleResourceTestHelper
    
    @Before
    fun prepare() {
        Mockito.reset(customerResourceClient)
        Mockito.reset(organizationUuidAwareActionProducer)
        Mockito.`when`(customerResourceClient.create(ArgumentMatchers.any())).thenReturn(CustomerCreateResultResponse())
        Mockito.`when`(customerResourceClient.update(ArgumentMatchers.any())).thenReturn(CustomerUpdateResultResponse())
        Mockito.doNothing().`when`(organizationUuidAwareActionProducer).produce(ArgumentMatchers.any())
    }
}