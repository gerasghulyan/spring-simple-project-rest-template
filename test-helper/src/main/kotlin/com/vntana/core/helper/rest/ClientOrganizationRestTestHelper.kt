package com.vntana.core.helper.rest

import com.vntana.core.helper.common.AbstractCommonTestHelper
import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 4:04 PM
 */
class ClientOrganizationRestTestHelper : AbstractCommonTestHelper() {
    fun buildCheckAvailableClientOrganizationSlugRequest(
            slug: String? = uuid()
    ): CheckAvailableClientOrganizationSlugRequest = CheckAvailableClientOrganizationSlugRequest(slug)
}