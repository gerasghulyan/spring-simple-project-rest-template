package com.vntana.core.service.organization

import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.persistence.organization.OrganizationRepository
import com.vntana.core.service.AbstractServiceUnitTest
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

    protected val helper: OrganizationCommonTestHelper = OrganizationCommonTestHelper()

    @Before
    fun before() {
        organizationService = OrganizationServiceImpl(organizationRepository)
    }
}