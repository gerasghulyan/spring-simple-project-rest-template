package com.vntana.core.rest.facade.invitation.user.component

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.rest.facade.invitation.user.component.impl.UserRolesPermissionsCheckerComponentImpl
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/12/20
 * Time: 4:34 PM
 */
class UserRolesPermissionsCheckerComponentUnitTest : AbstractFacadeUnitTest() {

    private lateinit var checkerComponent: UserRolesPermissionsCheckerComponent

    @Before
    fun prepare() {
        checkerComponent = UserRolesPermissionsCheckerComponentImpl()
    }

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