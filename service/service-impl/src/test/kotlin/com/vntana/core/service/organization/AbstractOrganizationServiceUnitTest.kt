package com.vntana.core.service.organization

import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.persistence.organization.OrganizationRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.common.component.SlugValidationComponent
import com.vntana.core.service.common.component.impl.SlugValidationComponentImpl
import com.vntana.core.service.invitation.organization.InvitationOrganizationService
import com.vntana.core.service.organization.impl.OrganizationServiceImpl
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:59 PM
 */
abstract class AbstractOrganizationServiceUnitTest : AbstractServiceUnitTest() {

    @Mock
    protected lateinit var organizationRepository: OrganizationRepository

    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var invitationOrganizationService: InvitationOrganizationService

    protected val slugValidationComponent: SlugValidationComponent = SlugValidationComponentImpl()

    protected val helper = OrganizationCommonTestHelper()

    protected val invitationOrganizationCommonTestHelper = InvitationOrganizationCommonTestHelper()

    @Before
    fun before() {
        organizationService = OrganizationServiceImpl(organizationRepository, slugValidationComponent, invitationOrganizationService)
    }
}