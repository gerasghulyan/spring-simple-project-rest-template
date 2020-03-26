package com.vntana.core.rest.facade.indexation.organization

import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.rest.facade.indexation.organization.component.OrganizationIndexationComponent
import com.vntana.core.rest.facade.indexation.organization.impl.OrganizationIndexationServiceFacadeImpl
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.service.organization.OrganizationService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Manuk Gharslyan.
 * Date: 3/25/2020
 * Time: 2:27 PM
 */
abstract class AbstractOrganizationIndexationServiceFacadeUnitTest : AbstractServiceFacadeUnitTest() {

    protected lateinit var organizationIndexationServiceFacade: OrganizationIndexationServiceFacade

    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var organizationIndexationComponent: OrganizationIndexationComponent

    @Before
    fun prepare() {
        organizationIndexationServiceFacade = OrganizationIndexationServiceFacadeImpl(organizationService, organizationIndexationComponent)
    }
}