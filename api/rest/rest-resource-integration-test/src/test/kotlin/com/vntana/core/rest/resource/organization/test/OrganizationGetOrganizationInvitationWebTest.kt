package com.vntana.core.rest.resource.organization.test

import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.model.organization.response.get.model.OrganizationStatusModel
import com.vntana.core.rest.resource.organization.AbstractOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 5:53 PM
 */
class OrganizationGetOrganizationInvitationWebTest : AbstractOrganizationWebTest() {

    @Test
    fun `test getOrganizationInvitation`() {
        val email = email()
        userResourceClient.createUser(userResourceTestHelper.buildCreateUserRequest(email = email))
        val invitationOrganizationUuid = invitationOrganizationResourceTestHelper.persistInvitationOrganization(email = email)
        val token = uuid()
        val request = invitationOrganizationResourceTestHelper.buildAcceptInvitationOrganizationRequest(token = token)
        tokenResourceTestHelper.persistTokenInvitationOrganization(
                invitationOrganizationUuid = invitationOrganizationUuid,
                token = token
        )
        val organizationUuid = invitationOrganizationResourceClient.accept(request).body.response().uuid
        organizationResourceClient.getOrganizationInvitation(organizationUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body.response().uuid).isEqualTo(organizationUuid)
            assertThat(it.body.response().customerSubscriptionDefinitionUuid).isNotEmpty()
        }
    }

    @Test
    fun `test getOrganizationInvitation when invitation not found`() {
        val organizationUuid = resourceTestHelper.persistOrganization().response().uuid
        organizationResourceClient.getOrganizationInvitation(organizationUuid).let {
            assertBasicErrorResultResponse(it, OrganizationErrorResponseModel.ORGANIZATION_INVITATION_NOT_FOUND)
        }
    }
}