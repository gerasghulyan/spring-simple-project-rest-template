package com.vntana.core.helper.unit.invitation.organization

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.service.invitation.organization.dto.CreateInvitationOrganizationDto
import com.vntana.core.service.invitation.organization.dto.UpdateInvitationOrganizationStatusDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 3:35 PM
 */
open class InvitationOrganizationCommonTestHelper : AbstractCommonTestHelper() {

    fun buildCreateInvitationOrganizationDto(
            ownerFullName: String? = uuid(),
            email: String? = uuid(),
            organizationName: String? = uuid(),
            slug: String? = uuid(),
            customerSubscriptionDefinitionUuid: String? = uuid()
    ) = CreateInvitationOrganizationDto(ownerFullName, email, organizationName, slug, customerSubscriptionDefinitionUuid)

    fun buildInvitationOrganizationPage(entities: List<InvitationOrganization> = listOf(buildInvitationOrganization(), buildInvitationOrganization()),
                                        pageAble: Pageable = buildPageRequest(0, 5),
                                        total: Long = entities.size.toLong()
    ): Page<InvitationOrganization> {
        return PageImpl(entities, pageAble, total)
    }

    fun buildInvitationOrganization(
            ownerFullName: String? = uuid(),
            email: String? = uuid(),
            organizationName: String? = uuid(),
            slug: String? = uuid(),
            customerSubscriptionDefinitionUuid: String? = uuid(),
            status: InvitationStatus? = InvitationStatus.INVITED
    ) = InvitationOrganization(ownerFullName, email, organizationName, slug, customerSubscriptionDefinitionUuid, status)

    fun buildUpdateInvitationOrganizationStatusDto(
            uuid: String? = uuid(),
            status: InvitationStatus? = InvitationStatus.INVITED
    ) = UpdateInvitationOrganizationStatusDto(uuid, status)
}