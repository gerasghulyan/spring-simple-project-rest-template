package com.vntana.core.helper.unit.catalog

import com.vntana.core.domain.catalog.FacebookCatalogSetting
import com.vntana.core.domain.organization.Organization
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.service.catalog.dto.CreateFacebookCatalogSettingDto
import com.vntana.core.service.catalog.dto.GetByOrganizationFacebookCatalogSettingDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 10:32 AM
 */
@Component
open class FacebookCatalogSettingCommonTestHelper : AbstractCommonTestHelper() {

    private val organizationCommonTestHelper = OrganizationCommonTestHelper()

    fun buildCreateFacebookCatalogSettingDto(
        systemUserToken: String? = uuid(),
        name: String? = uuid(),
        catalogId: String? = uuid(),
        organization: Organization? = organizationCommonTestHelper.buildOrganization()
    ): CreateFacebookCatalogSettingDto = CreateFacebookCatalogSettingDto(systemUserToken, name, catalogId, organization)

    fun buildGetByOrganizationFacebookCatalogSettingDto(
        page: Int = 0,
        size: Int = 10,
        organization: Organization? = organizationCommonTestHelper.buildOrganization()
    ): GetByOrganizationFacebookCatalogSettingDto = GetByOrganizationFacebookCatalogSettingDto(page, size, organization)

    fun buildFacebookCatalogSetting(
        systemUserToken: String? = uuid(),
        name: String? = uuid(),
        catalogId: String? = uuid(),
        organization: Organization? = organizationCommonTestHelper.buildOrganization()
    ): FacebookCatalogSetting = FacebookCatalogSetting(systemUserToken, name, catalogId, organization)

    fun buildFacebookCatalogSettingsPage(
        catalogs: List<FacebookCatalogSetting> = listOf(
            buildFacebookCatalogSetting(),
            buildFacebookCatalogSetting(),
            buildFacebookCatalogSetting()
        ),
        pageAble: Pageable = buildPageRequest(0, 5),
        total: Long = catalogs.size.toLong()
    ): Page<FacebookCatalogSetting> {
        return PageImpl(catalogs, pageAble, total)
    }
}