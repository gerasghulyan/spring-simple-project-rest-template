package com.vntana.core.helper.integration.organization

import com.vntana.core.domain.organization.Organization
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.service.organization.OrganizationService
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

    fun persistOrganization(
            name: String? = uuid(),
            slug: String? = uuid(),
            imageId: String? = uuid()): Organization {
        val dto = buildCreateOrganizationDto(
                name = name,
                slug = slug,
                imageId = imageId
        )
        return organizationService.create(dto)
    }
}