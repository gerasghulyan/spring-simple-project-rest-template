package com.vntana.core.rest.facade.indexation.invitation.organization.impl

import com.vntana.commons.api.model.response.indexation.error.IndexationErrorResponseModel
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.rest.facade.indexation.invitation.organization.InvitationOrganizationIndexationServiceFacade
import com.vntana.core.rest.facade.indexation.invitation.organization.component.InvitationOrganizationIndexationComponent
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
import com.vntana.core.service.invitation.organization.dto.GetAllInvitationOrganizationsDto
import org.easymock.EasyMock.anyObject
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.junit.Before
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 4/2/20
 * Time: 2:10 PM
 */
class InvitationOrganizationIndexationReIndexAllFacadeUnitTest : AbstractFacadeUnitTest() {

    private lateinit var indexationFacade: InvitationOrganizationIndexationServiceFacade

    @Mock
    private lateinit var invitationOrganizationService: InvitationOrganizationService

    @Mock
    private lateinit var invitationOrganizationIndexationComponent: InvitationOrganizationIndexationComponent

    private val commonTestHelper = InvitationOrganizationCommonTestHelper()

    @Before
    fun prepare() {
        indexationFacade = InvitationOrganizationIndexationServiceFacadeImpl(invitationOrganizationService, invitationOrganizationIndexationComponent)
    }

    @Test
    fun `test reindexAll`() {
        val invitations = listOf(commonTestHelper.buildInvitationOrganization(), commonTestHelper.buildInvitationOrganization())
        resetAll()
        expect(invitationOrganizationService.getAll(GetAllInvitationOrganizationsDto(Integer.MAX_VALUE))).andReturn(commonTestHelper.buildInvitationOrganizationPage(
                entities = invitations,
                pageAble = commonTestHelper.buildPageRequest(size = invitations.size)
        ))
        expect(invitationOrganizationIndexationComponent.indexByOne(anyObject())).times(invitations.size - 1).andVoid()
        replayAll()
        assertBasicSuccessResultResponse(indexationFacade.reindexAll())
        verifyAll()
    }

    @Test
    fun `test reindexAll when failed on client indexation`() {
        val invitations = listOf(commonTestHelper.buildInvitationOrganization())
        resetAll()
        expect(invitationOrganizationService.getAll(GetAllInvitationOrganizationsDto(Integer.MAX_VALUE))).andReturn(commonTestHelper.buildInvitationOrganizationPage(
                entities = invitations,
                pageAble = commonTestHelper.buildPageRequest(size = invitations.size)
        ))
        expect(invitationOrganizationIndexationComponent.indexByOne(anyObject())).andThrow(IllegalStateException())
        replayAll()
        assertBasicErrorResultResponse(indexationFacade.reindexAll(), IndexationErrorResponseModel.INDEXATION_ERROR)
        verifyAll()
    }
}