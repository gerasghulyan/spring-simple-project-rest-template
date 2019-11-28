package com.vntana.core.helper.unit.client

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.domain.organization.Organization
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.service.client.dto.CreateClientOrganizationDto

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 6:05 PM
 */
open class ClientOrganizationCommonTestHelper : AbstractCommonTestHelper() {

    private val organizationCommonTestHelper = OrganizationCommonTestHelper()

    fun buildCreateClientOrganizationDto(
            name: String? = uuid(),
            slug: String? = uuid(),
            imageId: String? = uuid(),
            organizationUuid: String? = uuid()
    ): CreateClientOrganizationDto = CreateClientOrganizationDto(name, slug, imageId, organizationUuid)

    fun buildClientOrganization(
            name: String? = uuid(),
            slug: String? = uuid(),
            imageId: String? = uuid(),
            organization: Organization? = organizationCommonTestHelper.buildOrganization()
    ): ClientOrganization = ClientOrganization(name, slug, imageId, organization)

}