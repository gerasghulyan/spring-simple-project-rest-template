package com.vntana.core.helper.unit.invitation.user

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.domain.invitation.user.InvitationUser
import com.vntana.core.domain.user.User
import com.vntana.core.domain.user.UserRole
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.invitation.user.dto.CreateInvitationUserDto
import com.vntana.core.service.invitation.user.dto.UpdateInvitationUserStatusDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:17 PM
 */
open class InvitationUserCommonTestHelper : AbstractCommonTestHelper() {
    private val userCommonTestHelper = UserCommonTestHelper()

    fun buildCreateInvitationUserDto(
            userRole: UserRole? = UserRole.ORGANIZATION_ADMIN,
            email: String? = uuid(),
            inviterUserUuid: String? = uuid()
    ) = CreateInvitationUserDto(userRole, email, inviterUserUuid)

    fun buildInvitationUserPage(entities: List<InvitationUser> = listOf(buildInvitationUser(), buildInvitationUser()),
                                pageAble: Pageable = buildPageRequest(0, 5),
                                total: Long = entities.size.toLong()
    ): Page<InvitationUser> {
        return PageImpl(entities, pageAble, total)
    }

    fun buildInvitationUser(
            userRole: UserRole? = UserRole.ORGANIZATION_ADMIN,
            email: String? = uuid(),
            status: InvitationStatus? = InvitationStatus.INVITED,
            invitedByUser: User? = userCommonTestHelper.buildUser()
    ) = InvitationUser(userRole, email, status, invitedByUser)

    fun buildUpdateInvitationUserStatusDto(
            uuid: String? = uuid(),
            status: InvitationStatus? = InvitationStatus.INVITED
    ) = UpdateInvitationUserStatusDto(uuid, status)
}