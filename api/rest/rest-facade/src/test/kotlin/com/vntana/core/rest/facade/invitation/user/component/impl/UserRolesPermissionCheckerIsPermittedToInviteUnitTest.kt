package com.vntana.core.rest.facade.invitation.user.component.impl

import com.vntana.core.model.auth.response.UserRoleModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 23.12.2020
 * Time: 18:39
 */
class UserRolesPermissionCheckerIsPermittedToInviteUnitTest : AbstractUserRolesPermissionsCheckerComponentUnitTest() {

    @Test
    fun `test with roles inviter - ORGANIZATION_OWNER, invited - ORGANIZATION_OWNER`() {
        val inviter = UserRoleModel.ORGANIZATION_OWNER
        val invited = UserRoleModel.ORGANIZATION_OWNER
        resetAll()
        replayAll()
        assertThat(checkerComponent.isPermittedToInvite(inviter, invited)).isFalse()
        verifyAll()
    }

    @Test
    fun `test with roles inviter - ORGANIZATION_OWNER, invited - ClIENT_ADMIN`() {
        val inviter = UserRoleModel.ORGANIZATION_OWNER
        val invited = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
        resetAll()
        replayAll()
        assertThat(checkerComponent.isPermittedToInvite(inviter, invited)).isTrue()
        verifyAll()
    }

    @Test
    fun `test with roles inviter - CLIENT_ORGANIZATION_VIEWER, invited - CLIENT_ORGANIZATION_VIEWER`() {
        val inviter = UserRoleModel.CLIENT_ORGANIZATION_VIEWER
        val invited = UserRoleModel.CLIENT_ORGANIZATION_VIEWER
        resetAll()
        replayAll()
        assertThat(checkerComponent.isPermittedToInvite(inviter, invited)).isFalse()
        verifyAll()
    }

    @Test
    fun `test when inviter - ORGANIZATION_CLIENTS_VIEWER`() {
        val inviter = UserRoleModel.ORGANIZATION_CLIENT_MEMBER
        val invited = UserRoleModel.CLIENT_ORGANIZATION_VIEWER
        resetAll()
        replayAll()
        assertThat(checkerComponent.isPermittedToInvite(inviter, invited)).isFalse()
        verifyAll()
    }

    @Test
    fun `test with roles inviter - CLIENT_ORGANIZATION_ADMIN, invited - CLIENT_ORGANIZATION_VIEWER`() {
        val inviter = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
        val invited = UserRoleModel.CLIENT_ORGANIZATION_VIEWER
        resetAll()
        replayAll()
        assertThat(checkerComponent.isPermittedToInvite(inviter, invited)).isTrue()
        verifyAll()
    }
}