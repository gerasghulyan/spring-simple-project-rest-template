package com.vntana.core.rest.resource.user.role.impl

import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.resource.user.role.AbstractUserRoleWebTest
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Arman Gevorgyan.
 * Date: 5/27/20
 * Time: 2:32 PM
 */
class UserRoleGrantSuperAdminRoleWebTest : AbstractUserRoleWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantSuperAdmin(userRoleResourceTestHelper.buildUserRoleGrantSuperAdminRequest(userUuid = null)),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                userRoleResourceClient.grantSuperAdmin(userRoleResourceTestHelper.buildUserRoleGrantSuperAdminRequest(emptyString())),
                UserRoleErrorResponseModel.MISSING_USER_UUID
        )
    }

    @Test
    fun `test when user does not exist`() {
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                userRoleResourceClient.grantSuperAdmin(userRoleResourceTestHelper.buildUserRoleGrantSuperAdminRequest()),
                UserRoleErrorResponseModel.USER_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val userUuid = userResourceTestHelper.persistUser().response().uuid
        assertBasicSuccessResultResponse(userRoleResourceClient.grantSuperAdmin(userRoleResourceTestHelper.buildUserRoleGrantSuperAdminRequest(userUuid = userUuid)))
    }
}