package com.project.sample.helper.unit.organization

import com.project.sample.commons.persistence.domain.organization.Organization
import com.project.sample.commons.persistence.domain.organization.status.OrganizationStatus
import com.project.sample.helper.unit.AbstractCommonTestHelper
import com.project.sample.service.organization.dto.CreateOrganizationDto
import com.project.sample.service.organization.dto.GetAllOrganizationDto
import com.project.sample.service.organization.dto.UpdateOrganizationDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

/**
 * Created by Geras Ghulyan.
 * Date: 10/8/19
 * Time: 6:05 PM
 */
open class OrganizationCommonTestHelper : AbstractCommonTestHelper() {

    fun buildCreateOrganizationDto(
        name: String? = uuid()
    ): CreateOrganizationDto = CreateOrganizationDto(name)

    fun buildUpdateOrganizationDto(
        uuid: String? = uuid(),
        imageBlobId: String? = uuid(),
        name: String? = uuid(),
        status: OrganizationStatus? = OrganizationStatus.DISABLED
    ): UpdateOrganizationDto = UpdateOrganizationDto(uuid, imageBlobId, name, status)

    fun buildOrganization(
        name: String? = uuid(),
        status: OrganizationStatus? = OrganizationStatus.ACTIVE
    ): Organization = Organization(name, status)

    fun buildOrganizationWithClients(
        name: String? = uuid(),
        status: OrganizationStatus? = OrganizationStatus.ACTIVE
    ): Organization = Organization(name, status)

    fun buildGetAllOrganizationDto(
        page: Int = 0,
        size: Int = 5
    ): GetAllOrganizationDto = GetAllOrganizationDto(page, size)

    fun buildOrganizationPage(
        organizations: List<Organization> = listOf(buildOrganization(), buildOrganization(), buildOrganization()),
        pageAble: Pageable = buildPageRequest(0, 5),
        total: Long = organizations.size.toLong()
    ): Page<Organization> {
        return PageImpl(organizations, pageAble, total)
    }
}