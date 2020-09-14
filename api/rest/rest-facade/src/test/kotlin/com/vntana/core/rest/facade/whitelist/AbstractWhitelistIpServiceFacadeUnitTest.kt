package com.vntana.core.rest.facade.whitelist

import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.whitelist.WhitelistIpCommonTestHelper
import com.vntana.core.helper.whitelist.WhitelistIpRestTestHelper
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.whitelist.impl.WhitelistIpServiceFacadeImpl
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.whitelist.WhitelistIpService
import com.vntana.core.service.whitelist.mediator.WhitelistIpLifecycleMediator
import ma.glasnost.orika.MapperFacade
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 11/29/19
 * Time: 4:50 PM
 */
abstract class AbstractWhitelistIpServiceFacadeUnitTest : AbstractFacadeUnitTest() {

    @Mock
    protected lateinit var mapperFacade: MapperFacade

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var whitelistIpService: WhitelistIpService

    @Mock
    protected lateinit var whitelistIpLifecycleMediator: WhitelistIpLifecycleMediator

    protected lateinit var whitelistIpServiceFacade: WhitelistIpServiceFacade

    protected val testHelper: WhitelistIpRestTestHelper = WhitelistIpRestTestHelper()

    protected val commonTestHelper: WhitelistIpCommonTestHelper = WhitelistIpCommonTestHelper()

    protected val organizationCommonTestHelper: OrganizationCommonTestHelper = OrganizationCommonTestHelper()

    @Before
    fun prepare() {
        whitelistIpServiceFacade = WhitelistIpServiceFacadeImpl(mapperFacade,
                organizationService,
                whitelistIpService,
                whitelistIpLifecycleMediator
        )
    }
}