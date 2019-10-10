package com.vntana.core.helper.unit.client

import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.service.client.dto.CreateClientOrganizationDto

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 6:05 PM
 */
open class ClientOrganizationCommonTestHelper : AbstractCommonTestHelper() {

    fun buildCreateClientOrganizationDto(
            name: String? = uuid(),
            slug: String? = uuid()
    ): CreateClientOrganizationDto = CreateClientOrganizationDto(name, slug)

    fun buildClientOrganization(
            name: String? = uuid(),
            slug: String? = uuid()
    ): ClientOrganization = ClientOrganization(name, slug)

}