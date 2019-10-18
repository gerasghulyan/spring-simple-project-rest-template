package com.vntana.core.helper.rest.organization

import com.vntana.core.helper.rest.AbstractRestUnitTestHelper
import com.vntana.core.model.organization.request.CheckAvailableOrganizationSlugRequest
import com.vntana.core.model.organization.request.CreateOrganizationRequest

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 4:04 PM
 */
class OrganizationRestTestHelper : AbstractRestUnitTestHelper() {
    fun buildCheckAvailableOrganizationSlugRequest(
            slug: String? = uuid()
    ): CheckAvailableOrganizationSlugRequest = CheckAvailableOrganizationSlugRequest(slug)

    fun buildCreateOrganizationRequest(
            name: String? = uuid(),
            slug: String? = uuid()
    ): CreateOrganizationRequest = CreateOrganizationRequest(name, slug)
}