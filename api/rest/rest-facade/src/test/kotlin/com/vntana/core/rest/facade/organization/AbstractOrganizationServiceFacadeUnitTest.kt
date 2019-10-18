package com.vntana.core.rest.facade.organization

import com.vntana.core.helper.rest.organization.OrganizationRestTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.rest.facade.organization.impl.OrganizationServiceFacadeImpl
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.service.organization.OrganizationService
import ma.glasnost.orika.MapperFacade
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:58 PM
 */
abstract class AbstractOrganizationServiceFacadeUnitTest : AbstractServiceFacadeUnitTest() {
    protected lateinit var organizationServiceFacade: OrganizationServiceFacade

    @Mock
    protected lateinit var mapperFacade: MapperFacade

    @Mock
    protected lateinit var organizationService: OrganizationService

    protected val restHelper = OrganizationRestTestHelper()

    protected val commonTestHelper = OrganizationCommonTestHelper()

    @Before
    fun before() {
        organizationServiceFacade = OrganizationServiceFacadeImpl(mapperFacade, organizationService)
    }
}