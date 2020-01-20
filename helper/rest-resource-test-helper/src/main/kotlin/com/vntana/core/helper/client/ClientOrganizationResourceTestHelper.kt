package com.vntana.core.helper.client

import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.model.client.response.CreateClientOrganizationResultResponse
import com.vntana.core.rest.client.client.ClientOrganizationResourceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 10/23/19
 * Time: 4:58 PM
 */
@Component
class ClientOrganizationResourceTestHelper : ClientOrganizationRestTestHelper() {

    @Autowired
    private lateinit var clientOrganizationResourceClient: ClientOrganizationResourceClient

    @Autowired
    private lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper

    fun persistClientOrganization(organizationUuid: String? = organizationResourceTestHelper.persistOrganization().response().uuid,
                                  name: String? = uuid(),
                                  slug: String? = uuid(),
                                  imageBlobId: String? = uuid()): CreateClientOrganizationResultResponse {
        val request = buildCreateClientOrganizationRequest(organizationUuid = organizationUuid,
                name = name,
                slug = slug,
                imageBlobId = imageBlobId)
        return clientOrganizationResourceClient.create(request)
    }
}