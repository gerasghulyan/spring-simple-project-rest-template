package com.vntana.core.rest.facade.invitation.organization

import com.vntana.core.helper.invitation.organization.InvitationOrganizationRestTestHelper
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.rest.facade.invitation.organization.impl.InvitationOrganizationServiceFacadeImpl
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
import ma.glasnost.orika.MapperFacade
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 3:35 PM
 */
abstract class AbstractInvitationOrganizationFacadeUnitTest : AbstractServiceFacadeUnitTest() {

    protected lateinit var invitationOrganizationServiceFacade: InvitationOrganizationServiceFacade

    @Mock
    protected lateinit var invitationOrganizationService: InvitationOrganizationService

    @Mock
    protected lateinit var preconditionChecker: InvitationOrganizationFacadePreconditionChecker

    @Mock
    protected lateinit var mapperFacade: MapperFacade

    protected val restTestHelper = InvitationOrganizationRestTestHelper()
    protected val commonTestHelper = InvitationOrganizationCommonTestHelper()

    @Before
    fun prepare() {
        invitationOrganizationServiceFacade = InvitationOrganizationServiceFacadeImpl(invitationOrganizationService, preconditionChecker, mapperFacade)
    }
}