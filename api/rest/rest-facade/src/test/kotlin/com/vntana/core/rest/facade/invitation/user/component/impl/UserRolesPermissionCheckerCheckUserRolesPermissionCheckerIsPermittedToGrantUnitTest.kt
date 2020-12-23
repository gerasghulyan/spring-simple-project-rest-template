package com.vntana.core.rest.facade.invitation.user.component.impl

import com.vntana.core.model.auth.response.UserRoleModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 23.12.2020
 * Time: 18:42
 */
class UserRolesPermissionCheckerCheckUserRolesPermissionCheckerIsPermittedToGrantUnitTest : AbstractUserRolesPermissionsCheckerComponentUnitTest() {

    @Test
    fun `test when granted ORGANIZATION_OWNER role`() {
        val granter = UserRoleModel.ORGANIZATION_OWNER
        val granted = UserRoleModel.ORGANIZATION_OWNER
        resetAll()
        replayAll()
        assertThat(checkerComponent.isPermittedToGrant(granter, granted)).isFalse()
        verifyAll()
    }

    @Test
    fun `test when granter - ORGANIZATION_OWNER granted - ORGANIZATION_ADMIN role`() {
        val granter = UserRoleModel.ORGANIZATION_OWNER
        val granted = UserRoleModel.ORGANIZATION_ADMIN
        resetAll()
        replayAll()
        assertThat(checkerComponent.isPermittedToGrant(granter, granted)).isTrue()
        verifyAll()
    }

    @Test
    fun `test when granter - ORGANIZATION_ADMIN granted - ORGANIZATION_ADMIN role`() {
        val granter = UserRoleModel.ORGANIZATION_ADMIN
        val granted = UserRoleModel.ORGANIZATION_ADMIN
        resetAll()
        replayAll()
        assertThat(checkerComponent.isPermittedToGrant(granter, granted)).isTrue()
        verifyAll()
    }

    @Test
    fun `test when granter - SUPER_ADMIN granted - ORGANIZATION_ADMIN role`() {
        val granter = UserRoleModel.ORGANIZATION_ADMIN
        val granted = UserRoleModel.ORGANIZATION_ADMIN
        resetAll()
        replayAll()
        assertThat(checkerComponent.isPermittedToGrant(granter, granted)).isTrue()
        verifyAll()
    }

    @Test
    fun `test when granter - ORGANIZATION_ADMIN granted - CLIENT_ORGANIZATION_ADMIN role`() {
        val granter = UserRoleModel.ORGANIZATION_ADMIN
        val granted = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
        resetAll()
        replayAll()
        assertThat(checkerComponent.isPermittedToGrant(granter, granted)).isTrue()
        verifyAll()
    }

    @Test
    fun `test when granter - CLIENT_ORGANIZATION_CONTENT_MANAGER granted - CLIENT_ORGANIZATION_ADMIN role`() {
        val granter = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER
        val granted = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
        resetAll()
        replayAll()
        assertThat(checkerComponent.isPermittedToGrant(granter, granted)).isFalse()
        verifyAll()
    }

    @Test
    fun `test when granter - CLIENT_ORGANIZATION_ADMIN granted - CLIENT_ORGANIZATION_ADMIN role`() {
        val granter = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
        val granted = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
        resetAll()
        replayAll()
        assertThat(checkerComponent.isPermittedToGrant(granter, granted)).isTrue()
        verifyAll()
    }

    @Test
    fun `test when granter - CLIENT_ORGANIZATION_VIEWER`() {
        val granter = UserRoleModel.CLIENT_ORGANIZATION_VIEWER
        val granted = UserRoleModel.CLIENT_ORGANIZATION_VIEWER
        resetAll()
        replayAll()
        assertThat(checkerComponent.isPermittedToGrant(granter, granted)).isFalse()
        verifyAll()
    }
}