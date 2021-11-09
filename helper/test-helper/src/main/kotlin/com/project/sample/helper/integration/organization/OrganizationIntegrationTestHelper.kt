package com.project.sample.helper.integration.organization

import com.project.sample.commons.persistence.domain.organization.Organization
import com.project.sample.helper.unit.organization.OrganizationCommonTestHelper
import com.project.sample.service.organization.OrganizationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 10:48 AM
 */
@Component
class OrganizationIntegrationTestHelper : OrganizationCommonTestHelper() {

    @Autowired
    private lateinit var organizationService: OrganizationService

    fun persistOrganization(
        name: String? = uuid()
    ): Organization {
        val dto = buildCreateOrganizationDto(
            name = name
        )
        return organizationService.create(dto)
    }
}