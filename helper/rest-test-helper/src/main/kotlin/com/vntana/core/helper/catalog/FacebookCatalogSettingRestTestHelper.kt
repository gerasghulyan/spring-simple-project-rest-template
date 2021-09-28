package com.vntana.core.helper.catalog

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.catalog.request.CreateFacebookCatalogSettingRequest
import com.vntana.core.model.catalog.request.GetByOrganizationFacebookCatalogSettingRequest

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 12:31 PM
 */
open class FacebookCatalogSettingRestTestHelper : AbstractRestTestHelper() {

    fun buildCreateFacebookCatalogSettingRequest(
        organizationUuid: String? = uuid(),
        name: String? = uuid(),
        systemUserToken: String? = uuid(),
        catalogId: String? = uuid()
    ): CreateFacebookCatalogSettingRequest =
        CreateFacebookCatalogSettingRequest(systemUserToken, organizationUuid, name, catalogId)

    fun buildGetByOrganizationFacebookCatalogSettingRequest(
        organizationUuid: String? = uuid(),
        page: Int = 0,
        size: Int = 10
    ): GetByOrganizationFacebookCatalogSettingRequest =
        GetByOrganizationFacebookCatalogSettingRequest(page, size, organizationUuid)
}