package com.project.sample.service.organization

import com.project.sample.helper.integration.organization.OrganizationIntegrationTestHelper
import com.project.sample.helper.unit.organization.OrganizationCommonTestHelper
import com.project.sample.service.AbstractServiceIntegrationTest
import com.project.sample.service.organization.OrganizationService
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 10:46 AM
 */
abstract class AbstractOrganizationServiceIntegrationTest : AbstractServiceIntegrationTest() {
    @Autowired
    protected lateinit var organizationService: OrganizationService

    @Autowired
    protected lateinit var integrationTestHelper: OrganizationIntegrationTestHelper

    protected val commonTestHelper: OrganizationCommonTestHelper = OrganizationCommonTestHelper()
}