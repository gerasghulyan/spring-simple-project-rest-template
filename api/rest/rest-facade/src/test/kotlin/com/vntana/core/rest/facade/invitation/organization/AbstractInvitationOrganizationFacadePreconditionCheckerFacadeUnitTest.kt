package com.vntana.core.rest.facade.invitation.organization

import com.vntana.core.helper.invitation.organization.InvitationOrganizationRestTestHelper
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.rest.facade.invitation.organization.impl.InvitationOrganizationFacadePreconditionCheckerImpl
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.service.common.component.SlugValidationComponent
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.token.TokenService
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
    
    @Mock
    protected lateinit var tokenService: TokenService

    protected val restTestHelper = InvitationOrganizationRestTestHelper()
    
    protected val invitationOrganizationCommonTestHelper = InvitationOrganizationCommonTestHelper()
    
    protected val tokenCommonHelper = TokenCommonTestHelper()

    @Before
    fun prepare() {
        preconditionChecker = InvitationOrganizationFacadePreconditionCheckerImpl(invitationOrganizationService,
                tokenService,
                slugValidationComponent,
                organizationService
        )
    }
}