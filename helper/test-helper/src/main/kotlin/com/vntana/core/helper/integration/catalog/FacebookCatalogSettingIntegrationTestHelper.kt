package com.vntana.core.helper.integration.catalog

import com.vntana.core.domain.catalog.FacebookCatalogSetting
import com.vntana.core.domain.organization.Organization
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.unit.catalog.FacebookCatalogSettingCommonTestHelper
import com.vntana.core.service.catalog.FacebookCatalogSettingService
import com.vntana.core.service.catalog.dto.CreateFacebookCatalogSettingDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 10:31 AM
 */
@Component
class FacebookCatalogSettingIntegrationTestHelper : FacebookCatalogSettingCommonTestHelper() {

    @Autowired
    private lateinit var facebookCatalogSettingService: FacebookCatalogSettingService

    @Autowired
    private lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper

    fun persistFacebookCatalogSetting(
        organization: Organization = organizationIntegrationTestHelper.persistOrganization(),
        dto: CreateFacebookCatalogSettingDto = buildCreateFacebookCatalogSettingDto(organization = organization)
    ): FacebookCatalogSetting = facebookCatalogSettingService.create(dto)

    fun deleteFacebookCatalogSetting(
        facebookCatalogSetting: FacebookCatalogSetting = persistFacebookCatalogSetting()
    ): Unit = facebookCatalogSettingService.delete(facebookCatalogSetting)
}