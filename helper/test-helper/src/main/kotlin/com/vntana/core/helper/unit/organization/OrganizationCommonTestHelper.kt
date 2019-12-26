package com.vntana.core.helper.unit.organization

import com.vntana.core.domain.organization.Organization
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.service.organization.dto.CreateOrganizationDto
import com.vntana.core.service.organization.dto.UpdateOrganizationDto

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 6:05 PM
 */
open class OrganizationCommonTestHelper : AbstractCommonTestHelper() {

    fun buildCreateOrganizationDto(
            name: String? = uuid(),
            slug: String? = uuid(),
            imageId: String? = uuid()
    ): CreateOrganizationDto = CreateOrganizationDto(name, slug, imageId)

    fun buildUpdateOrganizationDto(
            uuid: String? = uuid(),
            imageId: String? = uuid(),
            name: String? = uuid()
    ): UpdateOrganizationDto = UpdateOrganizationDto(uuid, imageId, name)


    fun buildOrganization(
            name: String? = uuid(),
            slug: String? = uuid(),
            imageId: String? = uuid()
    ): Organization = Organization(name, slug, imageId)

}