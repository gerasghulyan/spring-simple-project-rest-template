package com.vntana.core.rest.resource.indexation

import com.vntana.core.helper.invitation.organization.InvitationOrganizationResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.indexation.producer.invitation.organization.InvitationOrganizationUuidAwareActionProducer
import com.vntana.core.indexation.producer.organization.OrganizationUuidAwareActionProducer
import com.vntana.core.rest.client.indexation.IndexationResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Manuk Gharslyan.
 * Date: 3/26/2020
 * Time: 12:22 PM
 */
abstract class AbstractIndexationWebTest : AbstractWebIntegrationTest() {
    @Autowired
    protected lateinit var indexationResourceClient: IndexationResourceClient

    @Autowired
    protected lateinit var userResourceTestHelper: UserResourceTestHelper

    @Autowired
    protected lateinit var invitationOrganizationResourceTestHelper: InvitationOrganizationResourceTestHelper

    @Autowired
    protected lateinit var organizationUuidAwareActionProducer: OrganizationUuidAwareActionProducer

    @Autowired
    protected lateinit var invitationOrganizationUuidAwareActionProducer: InvitationOrganizationUuidAwareActionProducer

    @Before
    fun before() {
        Mockito.reset(organizationUuidAwareActionProducer)
        Mockito.reset(invitationOrganizationUuidAwareActionProducer)
        Mockito.doNothing().`when`(organizationUuidAwareActionProducer).produce(ArgumentMatchers.any())
        Mockito.doNothing().`when`(invitationOrganizationUuidAwareActionProducer).produce(ArgumentMatchers.any())
    }
}