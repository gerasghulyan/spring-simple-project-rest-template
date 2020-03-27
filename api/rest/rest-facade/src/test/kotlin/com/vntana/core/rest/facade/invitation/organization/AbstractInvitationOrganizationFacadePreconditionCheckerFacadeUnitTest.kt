package com.vntana.core.rest.facade.invitation.organization

import com.vntana.core.helper.invitation.organization.InvitationOrganizationRestTestHelper
import com.vntana.core.rest.facade.invitation.organization.impl.InvitationOrganizationFacadePreconditionCheckerImpl
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.service.common.component.SlugValidationComponent
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
import com.vntana.core.service.organization.OrganizationService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 3:03 PM
 */
abstract class AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest : AbstractServiceFacadeUnitTest() {

    protected lateinit var preconditionChecker: InvitationOrganizationFacadePreconditionChecker

    @Mock
    protected lateinit var invitationOrganizationService: InvitationOrganizationService

    @Mock
    protected lateinit var slugValidationComponent: SlugValidationComponent

    @Mock
    protected lateinit var organizationService: OrganizationService

    protected val restTestHelper = InvitationOrganizationRestTestHelper()

    @Before
    fun prepare() {
        preconditionChecker = InvitationOrganizationFacadePreconditionCheckerImpl(invitationOrganizationService,
                slugValidationComponent,
                organizationService
        )
    }
}