package com.vntana.core.helper.integration.client

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.service.client.OrganizationClientService
import com.vntana.core.service.client.dto.CreateClientOrganizationDto
import com.vntana.core.service.organization.OrganizationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 10:48 AM
 */
@Component
class ClientOrganizationIntegrationTestHelper : ClientOrganizationCommonTestHelper() {
    @Autowired
    private lateinit var organizationClientService: OrganizationClientService

    @Autowired
    private lateinit var organizationService: OrganizationService

    @Autowired
    private val organizationCommonTestHelper = OrganizationCommonTestHelper()

    fun persistClientOrganization(
            organizationUuid: String = organizationService.create(organizationCommonTestHelper.buildCreateOrganizationDto()).uuid,
            dto: CreateClientOrganizationDto = buildCreateClientOrganizationDto(organizationUuid = organizationUuid)
    ): ClientOrganization = organizationClientService.create(dto)
}