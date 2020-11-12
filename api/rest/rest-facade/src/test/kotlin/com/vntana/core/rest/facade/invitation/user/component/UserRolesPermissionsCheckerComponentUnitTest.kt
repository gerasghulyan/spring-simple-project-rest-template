package com.vntana.core.rest.facade.invitation.user.component

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.rest.facade.invitation.user.component.impl.UserRolesPermissionsCheckerComponentImpl
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import org.assertj.core.api.Assertions
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
    fun `test with roles inviter - ORGANIZATION_ADMIN, invited - ORGANIZATION_ADMIN`() {
        val inviter = UserRoleModel.ORGANIZATION_OWNER
        val invited = UserRoleModel.ORGANIZATION_OWNER
        resetAll()
        replayAll()
        Assertions.assertThat(checkerComponent.isPermittedToInvite(inviter, invited)).isFalse()
        verifyAll()
    }

    @Test
    fun `test with roles inviter - ORGANIZATION_ADMIN, invited - ClIENT_ADMIN`() {
        val inviter = UserRoleModel.ORGANIZATION_OWNER
        val invited = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
        resetAll()
        replayAll()
        Assertions.assertThat(checkerComponent.isPermittedToInvite(inviter, invited)).isTrue()
        verifyAll()
    }

    @Test
    fun `test with roles inviter - CLIENT_ORGANIZATION_VIEWER, invited - CLIENT_ORGANIZATION_VIEWER`() {
        val inviter = UserRoleModel.CLIENT_ORGANIZATION_VIEWER
        val invited = UserRoleModel.CLIENT_ORGANIZATION_VIEWER
        resetAll()
        replayAll()
        Assertions.assertThat(checkerComponent.isPermittedToInvite(inviter, invited)).isFalse()
        verifyAll()
    }

    @Test
    fun `test with roles inviter - CLIENT_ORGANIZATION_ADMIN, invited - CLIENT_ORGANIZATION_VIEWER`() {
        val inviter = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
        val invited = UserRoleModel.CLIENT_ORGANIZATION_VIEWER
        resetAll()
        replayAll()
        Assertions.assertThat(checkerComponent.isPermittedToInvite(inviter, invited)).isTrue()
        verifyAll()
    }
}