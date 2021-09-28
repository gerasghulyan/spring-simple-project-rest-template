package com.vntana.core.helper.catalog

import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.model.catalog.response.CreateFacebookCatalogSettingResultResponse
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
        name: String? = uuid(),
        systemUserToken: String? = uuid(),
        catalogId: String? = uuid()
    ): ResponseEntity<CreateFacebookCatalogSettingResultResponse>? {
        val request = buildCreateFacebookCatalogSettingRequest(
            organizationUuid = organizationUuid,
            name = name,
            systemUserToken = systemUserToken,
            catalogId = catalogId
        )
        return facebookCatalogSettingResourceClient.create(request)
    }
}