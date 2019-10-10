package com.vntana.core.helper.rest.client

import com.vntana.core.helper.rest.AbstractRestUnitTestHelper
import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest
import com.vntana.core.model.client.request.CreateClientOrganizationRequest

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 4:04 PM
 */
class ClientOrganizationRestTestHelper : AbstractRestUnitTestHelper() {
    fun buildCheckAvailableClientOrganizationSlugRequest(
            slug: String? = uuid()
    ): CheckAvailableClientOrganizationSlugRequest = CheckAvailableClientOrganizationSlugRequest(slug)

    fun buildCreateClientOrganizationRequest(
            name: String? = uuid(),
            slug: String? = uuid()
    ): CreateClientOrganizationRequest = CreateClientOrganizationRequest(name, slug)
}