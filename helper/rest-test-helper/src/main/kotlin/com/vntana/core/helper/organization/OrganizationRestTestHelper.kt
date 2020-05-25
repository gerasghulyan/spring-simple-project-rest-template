package com.vntana.core.helper.organization

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.organization.request.CheckAvailableOrganizationSlugRequest
import com.vntana.core.model.organization.request.CreateOrganizationRequest
import com.vntana.core.model.organization.response.get.model.OrganizationStatusModel
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
            imageBlobId: String? = uuid()
    ): CreateOrganizationRequest = CreateOrganizationRequest(name, slug, userUuid, imageBlobId)

    fun buildUpdateOrganizationRequest(
            uuid: String? = uuid(),
            name: String? = uuid(),
            imageBlobId: String? = uuid(),
            status: OrganizationStatusModel? = OrganizationStatusModel.DISABLED
    ): UpdateOrganizationRequest = UpdateOrganizationRequest(uuid, name, imageBlobId, status)
}