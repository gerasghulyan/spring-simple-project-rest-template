package com.vntana.core.service.invitation.organization

import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.persistence.invitation.organization.InvitationOrganizationRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.invitation.organization.impl.InvitationOrganizationServiceImpl
import com.vntana.core.service.organization.OrganizationService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 4:07 PM
 */
abstract class AbstractInvitationOrganizationServiceUnitTest : AbstractServiceUnitTest() {

    protected lateinit var invitationOrganizationService: InvitationOrganizationService

    @Mock
    protected lateinit var invitationOrganizationRepository: InvitationOrganizationRepository

    @Mock
    protected lateinit var organizationService: OrganizationService

    protected val commonTestHelper = InvitationOrganizationCommonTestHelper()
    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()

    @Before
    fun prepare() {
        invitationOrganizationService = InvitationOrganizationServiceImpl(invitationOrganizationRepository, organizationService)
    }
}