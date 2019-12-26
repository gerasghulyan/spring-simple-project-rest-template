package com.vntana.core.helper.organization

import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.model.organization.response.create.CreateOrganizationResultResponse
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

    @Autowired
    private lateinit var userResourceTestHelper: UserResourceTestHelper

    fun persistOrganization(name: String? = uuid(),
                            slug: String? = uuid(),
                            imageId: String? = uuid()
    ): CreateOrganizationResultResponse {
        val request = buildCreateOrganizationRequest(
                name = name,
                slug = slug,
                imageId = imageId
        )
        val userResponse = userResourceTestHelper.persistUser()
        request.userUuid = userResponse.response().uuid
        return organizationResourceClient.create(request)
    }
}