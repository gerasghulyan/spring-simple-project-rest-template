package com.vntana.core.helper.client

import com.vntana.core.helper.AbstractRestTestHelper
import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest
import com.vntana.core.model.client.request.CreateClientOrganizationRequest

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
            imageId: String? = uuid()
    ): CreateClientOrganizationRequest = CreateClientOrganizationRequest(organizationUuid, name, slug, imageId)
}