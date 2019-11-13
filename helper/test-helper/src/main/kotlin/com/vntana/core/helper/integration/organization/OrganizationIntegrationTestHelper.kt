package com.vntana.core.helper.integration.organization

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.domain.organization.Organization
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.organization.dto.CreateOrganizationDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 10:48 AM
 */
@Component
class OrganizationIntegrationTestHelper : OrganizationCommonTestHelper() {
    @Autowired
    private lateinit var organizationService: OrganizationService

    fun persistOrganization(dto: CreateOrganizationDto = buildCreateOrganizationDto()): Organization = organizationService.create(dto)
}