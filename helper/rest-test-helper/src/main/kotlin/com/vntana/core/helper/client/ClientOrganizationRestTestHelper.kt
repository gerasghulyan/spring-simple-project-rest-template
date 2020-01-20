package com.vntana.core.helper.client

import com.vntana.core.helper.AbstractRestTestHelper
import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest
import com.vntana.core.model.client.request.CreateClientOrganizationRequest
import com.vntana.core.model.client.request.UpdateClientOrganizationRequest

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 4:04 PM
 */
open class ClientOrganizationRestTestHelper : AbstractRestTestHelper() {
    fun buildCheckAvailableClientOrganizationSlugRequest(
            slug: String? = uuid(),
            organizationUuid: String? = uuid()
    ): CheckAvailableClientOrganizationSlugRequest = CheckAvailableClientOrganizationSlugRequest(slug, organizationUuid)

    fun buildCreateClientOrganizationRequest(
            organizationUuid: String? = uuid(),
            name: String? = uuid(),
            slug: String? = uuid(),
            imageBlobId: String? = uuid()
    ): CreateClientOrganizationRequest = CreateClientOrganizationRequest(organizationUuid, name, slug, imageBlobId)

    fun buildUpdateClientOrganizationRequest(
            uuid: String? = uuid(),
            name: String? = uuid(),
            imageBlobId: String? = uuid()
    ): UpdateClientOrganizationRequest = UpdateClientOrganizationRequest(uuid, name, imageBlobId)
}