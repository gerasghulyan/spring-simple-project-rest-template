package com.vntana.core.helper.organization

import com.vntana.core.helper.AbstractRestTestHelper
import com.vntana.core.model.organization.request.CheckAvailableOrganizationSlugRequest
import com.vntana.core.model.organization.request.CreateOrganizationRequest
import com.vntana.core.model.organization.response.update.request.UpdateOrganizationRequest

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 4:04 PM
 */
open class OrganizationRestTestHelper : AbstractRestTestHelper() {
    fun buildCheckAvailableOrganizationSlugRequest(
            slug: String? = uuid()
    ): CheckAvailableOrganizationSlugRequest = CheckAvailableOrganizationSlugRequest(slug)

    fun buildCreateOrganizationRequest(
            name: String? = uuid(),
            slug: String? = uuid(),
            userUuid: String? = uuid(),
            imageId: String? = uuid()
    ): CreateOrganizationRequest = CreateOrganizationRequest(name, slug, userUuid, imageId)

    fun buildUpdateOrganizationRequest(
            uuid: String? = uuid(),
            name: String? = uuid(),
            imageId: String? = uuid()
    ): UpdateOrganizationRequest = UpdateOrganizationRequest(uuid, name, imageId)
}