package com.project.sample.service.organization

import com.project.sample.helper.unit.organization.OrganizationCommonTestHelper
import com.project.sample.persistence.organization.OrganizationRepository
import com.project.sample.service.AbstractServiceUnitTest
import com.project.sample.service.organization.impl.OrganizationServiceImpl
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Geras Ghulyan.
 * Date: 10/8/19
 * Time: 5:59 PM
 */
abstract class AbstractOrganizationServiceUnitTest : AbstractServiceUnitTest() {

    @Mock
    protected lateinit var organizationRepository: OrganizationRepository

    protected lateinit var organizationService: OrganizationService

    protected val helper = OrganizationCommonTestHelper()

    @Before
    fun before() {
        organizationService = OrganizationServiceImpl(organizationRepository)
    }
}