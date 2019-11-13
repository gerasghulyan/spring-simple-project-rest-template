package com.vntana.core.helper.organization

import com.vntana.core.model.organization.response.CreateOrganizationResultResponse
import com.vntana.core.rest.client.organization.OrganizationResourceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 10/23/19
 * Time: 5:04 PM
 */
@Component
class OrganizationResourceTestHelper : OrganizationRestTestHelper() {

    @Autowired
    private lateinit var organizationResourceClient: OrganizationResourceClient

    fun persistOrganization(name: String? = uuid(), slug: String? = uuid()): CreateOrganizationResultResponse {
        return organizationResourceClient.create(buildCreateOrganizationRequest(name, slug))
    }
}