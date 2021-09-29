package com.vntana.core.helper.catalog

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.catalog.request.CreateFacebookCatalogSettingRequest
import com.vntana.core.model.catalog.request.FacebookCatalogSetting
import com.vntana.core.model.catalog.request.GetByOrganizationFacebookCatalogSettingRequest

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 12:31 PM
 */
open class FacebookCatalogSettingRestTestHelper : AbstractRestTestHelper() {

    fun buildCreateFacebookCatalogSettingRequest(
        organizationUuid: String? = uuid(),
        catalogs: List<FacebookCatalogSetting>? = emptyList(),
        systemUserToken: String? = uuid()
    ): CreateFacebookCatalogSettingRequest =
        CreateFacebookCatalogSettingRequest(catalogs, systemUserToken, organizationUuid)

    fun buildSingleFacebookCatalogSetting(
        name: String? = uuid(),
        catalogId: String? = uuid()
    ): FacebookCatalogSetting = FacebookCatalogSetting(name, catalogId)

    fun buildGetByOrganizationFacebookCatalogSettingRequest(
        organizationUuid: String? = uuid(),
        page: Int = 0,
        size: Int = 10
    ): GetByOrganizationFacebookCatalogSettingRequest =
        GetByOrganizationFacebookCatalogSettingRequest(page, size, organizationUuid)
}