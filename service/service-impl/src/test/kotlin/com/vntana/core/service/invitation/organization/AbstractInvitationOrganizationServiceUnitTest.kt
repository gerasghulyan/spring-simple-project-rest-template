package com.vntana.core.service.invitation.organization

import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.persistence.invitation.organization.InvitationOrganizationRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.invitation.organization.impl.InvitationOrganizationServiceImpl
import com.vntana.core.service.token.invitation.organization.TokenInvitationOrganizationService
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

    protected val commonTestHelper = InvitationOrganizationCommonTestHelper()

    @Before
    fun prepare() {
        invitationOrganizationService = InvitationOrganizationServiceImpl(invitationOrganizationRepository)
    }
}