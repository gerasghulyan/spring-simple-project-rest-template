package com.vntana.core.helper.catalog

import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.model.catalog.request.FacebookCatalogSettingRequestModel
import com.vntana.core.model.catalog.response.FacebookCatalogSettingCreateResultResponse
import com.vntana.core.rest.client.catalog.FacebookCatalogSettingResourceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 12:30 PM
 */
@Component
class FacebookCatalogSettingResourceTestHelper : FacebookCatalogSettingRestTestHelper() {

    @Autowired
    private lateinit var facebookCatalogSettingResourceClient: FacebookCatalogSettingResourceClient

    @Autowired
    private lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper

    fun persistFacebookCatalogSetting(
        organizationUuid: String? = organizationResourceTestHelper.persistOrganization().response().uuid,
        systemUserToken: String? = uuid(),
        catalogs: List<FacebookCatalogSettingRequestModel>? = listOf(buildSingleFacebookCatalogSetting())
    ): ResponseEntity<FacebookCatalogSettingCreateResultResponse>? {
        val request = buildCreateFacebookCatalogSettingRequest(
            organizationUuid = organizationUuid,
            systemUserToken = systemUserToken,
            catalogs = catalogs
        )
        return facebookCatalogSettingResourceClient.create(request)
    }
}