package com.vntana.core.helper.unit.client

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.domain.organization.Organization
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.service.client.dto.CreateClientOrganizationDto
import com.vntana.core.service.client.dto.UpdateClientOrganizationDto
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 6:05 PM
 */
@Component
open class ClientOrganizationCommonTestHelper : AbstractCommonTestHelper() {

    private val organizationCommonTestHelper = OrganizationCommonTestHelper()

    fun buildCreateClientOrganizationDto(
            name: String? = uuid(),
            slug: String? = uuid(),
            imageBlobId: String? = uuid(),
            organizationUuid: String? = uuid()
    ): CreateClientOrganizationDto = CreateClientOrganizationDto(name, slug, imageBlobId, organizationUuid)

    fun buildClientOrganization(
            name: String? = uuid(),
            slug: String? = uuid(),
            imageBlobId: String? = uuid(),
            organization: Organization? = organizationCommonTestHelper.buildOrganization()
    ): ClientOrganization = ClientOrganization(name, slug, imageBlobId, organization)

    fun buildUpdateClientOrganizationDto(
            uuid: String? = uuid(),
            name: String? = uuid(),
            imageBlobId: String? = uuid()
    ): UpdateClientOrganizationDto = UpdateClientOrganizationDto(uuid, name, imageBlobId)

}