package com.vntana.core.rest.facade.indexation.invitation.organization.component.impl

import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.rest.facade.indexation.invitation.organization.component.InvitationOrganizationIndexationComponent
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
import com.vntana.core.service.invitation.organization.mediator.InvitationOrganizationUuidAwareLifecycleMediator
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

/**
 * Created by Arman Gevorgyan.
 * Date: 4/2/20
 * Time: 2:41 PM
 */
class InvitationOrganizationIndexationIndexByOneComponentUnitTest : AbstractServiceFacadeUnitTest() {

    private lateinit var indexationComponent: InvitationOrganizationIndexationComponent

    private val commonTestHelper = InvitationOrganizationCommonTestHelper()

    @Mock
    private lateinit var invitationOrganizationUuidAwareLifecycleMediator: InvitationOrganizationUuidAwareLifecycleMediator

    @Mock
    private lateinit var invitationOrganizationService: InvitationOrganizationService

    @Before
    fun prepare() {
        indexationComponent = InvitationOrganizationIndexationComponentImpl(invitationOrganizationService, invitationOrganizationUuidAwareLifecycleMediator)
    }

    @Test
    fun `test indexByOne with invalid argument`() {
        resetAll()
        replayAll()
        assertThatThrownBy { indexationComponent.indexByOne(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { indexationComponent.indexByOne(emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test indexByOne with removed organization`() {
        val invitation = commonTestHelper.buildInvitationOrganization()
        invitation.removed = LocalDateTime.now()
        resetAll()
        expect(invitationOrganizationService.getByUuid(invitation.uuid)).andReturn(invitation)
        expect(invitationOrganizationUuidAwareLifecycleMediator.onDeleted(invitation.uuid)).andVoid()
        replayAll()
        indexationComponent.indexByOne(invitation.uuid)
        verifyAll()
    }

    @Test
    fun `test indexByOne an organization`() {
        val organization = commonTestHelper.buildInvitationOrganization()
        resetAll()
        expect(invitationOrganizationService.getByUuid(organization.uuid)).andReturn(organization)
        expect(invitationOrganizationUuidAwareLifecycleMediator.onUpdated(organization.uuid)).andVoid()
        replayAll()
        indexationComponent.indexByOne(organization.uuid)
        verifyAll()
    }
}