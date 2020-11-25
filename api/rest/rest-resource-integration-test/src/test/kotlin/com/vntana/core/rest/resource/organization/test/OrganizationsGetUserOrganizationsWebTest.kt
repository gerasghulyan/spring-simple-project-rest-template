package com.vntana.core.rest.resource.organization.test

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.rest.resource.organization.AbstractOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2020
 * Time: 10:24 AM
 */
class OrganizationsGetUserOrganizationsWebTest : AbstractOrganizationWebTest() {

    @Test
    fun test() {
        val user = userResourceTestHelper.persistUser().response()
        val userUuid = user.uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val clientOrganization = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = user.organizationUuid)
        val clientOrganization2 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid)
        val clientOrganization3 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid)
        val newOrganizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        userRoleResourceTestHelper.grantUserOrganizationAdminRole(userUuid, newOrganizationUuid)
        userRoleResourceTestHelper.grantUserClientRole(userUuid, clientOrganization.response().uuid)
        userRoleResourceTestHelper.grantUserClientRole(userUuid, clientOrganization2.response().uuid, UserRoleModel.CLIENT_ORGANIZATION_VIEWER)
        userRoleResourceTestHelper.grantUserClientRole(userUuid, clientOrganization3.response().uuid, UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
        organizationResourceClient.getUserOrganizations(userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().items().size).isEqualTo(3)
        }
    }
}