package com.vntana.core.rest.resource.storage.client

import com.vntana.core.helper.client.ClientOrganizationResourceTestHelper
import com.vntana.core.helper.invitation.organization.InvitationOrganizationResourceTestHelper
import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.helper.storage.client.StorageClientOrganizationKeyResourceTestHelper
import com.vntana.core.helper.storage.client.StorageClientOrganizationKeyRestTestHelper
import com.vntana.core.helper.token.TokenResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.helper.user.role.UserRoleResourceTestHelper
import com.vntana.core.indexation.producer.organization.OrganizationUuidAwareActionProducer
import com.vntana.core.rest.client.invitation.organization.InvitationOrganizationResourceClient
import com.vntana.core.rest.client.organization.OrganizationResourceClient
import com.vntana.core.rest.client.storage.client.StorageClientOrganizationKeyResourceClient
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
 * Created by Geras Ghulyan
 * Date: 05-May-21
 * Time: 16:58
 */
abstract class AbstractStorageClientOrganizationKeyWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var storageClientOrganizationKeyResourceClient: StorageClientOrganizationKeyResourceClient

    @Autowired
    protected val storageClientOrganizationKeyResourceTestHelper = StorageClientOrganizationKeyResourceTestHelper()

    @Autowired
    protected lateinit var clientOrganizationResourceTestHelper: ClientOrganizationResourceTestHelper
}