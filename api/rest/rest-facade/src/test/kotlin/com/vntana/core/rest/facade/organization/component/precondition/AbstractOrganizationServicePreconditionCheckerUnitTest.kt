package com.vntana.core.rest.facade.organization.component.precondition

import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.rest.facade.organization.component.precondition.impl.OrganizationServiceFacadePreconditionCheckerComponentImpl
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.service.organization.OrganizationService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Geras Ghulyan
 * Date: 26.04.20
 * Time: 23:42
 */
abstract class AbstractOrganizationServicePreconditionCheckerUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var preconditionChecker: OrganizationServiceFacadePreconditionCheckerComponent

    @Mock
    protected lateinit var organizationService: OrganizationService

    protected val commonTestHelper = OrganizationCommonTestHelper()

    @Before
    fun prepare() {
        preconditionChecker = OrganizationServiceFacadePreconditionCheckerComponentImpl(organizationService)
    }

}