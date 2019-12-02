package com.vntana.core.service.whitelist

import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.whitelist.WhitelistIpCommonTestHelper
import com.vntana.core.persistence.whitelist.WhitelistIpRepository
import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.organization.OrganizationService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 3:46 PM
 */
abstract class AbstractWhitelistIpServiceUnitTest : AbstractServiceUnitTest() {

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var whitelistIpRepository: WhitelistIpRepository

    protected lateinit var whitelistIpService: WhitelistIpService

    protected val testHelper: WhitelistIpCommonTestHelper = WhitelistIpCommonTestHelper()

    protected val organizationTestHelper: OrganizationCommonTestHelper = OrganizationCommonTestHelper()

    @Before
    fun prepare() {
        whitelistIpService = WhitelistIpServiceImpl(organizationService, whitelistIpRepository)
    }
}