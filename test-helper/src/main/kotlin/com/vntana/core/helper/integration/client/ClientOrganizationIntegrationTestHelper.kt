package com.vntana.core.helper.integration.client

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.service.client.ClientOrganizationService
import com.vntana.core.service.client.dto.CreateClientOrganizationDto
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
    private lateinit var clientOrganizationService: ClientOrganizationService

    fun persistClientOrganization(dto: CreateClientOrganizationDto = buildCreateClientOrganizationDto()): ClientOrganization = clientOrganizationService.create(dto)
}