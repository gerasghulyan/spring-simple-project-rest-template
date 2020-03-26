package com.vntana.core.rest.resource.invitation.organization.impl

import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.resource.invitation.organization.AbstractInvitationOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:42 PM
 */
class InvitationOrganizationCreateWebTest : AbstractInvitationOrganizationWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.create(resourceTestHelper.buildCreateInvitationOrganizationRequest(ownerFullName = null)),
                InvitationOrganizationErrorResponseModel.MISSING_OWNER_FULL_NAME
        )
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.create(resourceTestHelper.buildCreateInvitationOrganizationRequest(ownerFullName = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_OWNER_FULL_NAME
        )
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.create(resourceTestHelper.buildCreateInvitationOrganizationRequest(email = null)),
                InvitationOrganizationErrorResponseModel.MISSING_EMAIL
        )
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.create(resourceTestHelper.buildCreateInvitationOrganizationRequest(email = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_EMAIL
        )
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.create(resourceTestHelper.buildCreateInvitationOrganizationRequest(organizationName = null)),
                InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_NAME
        )
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.create(resourceTestHelper.buildCreateInvitationOrganizationRequest(organizationName = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_ORGANIZATION_NAME
        )
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.create(resourceTestHelper.buildCreateInvitationOrganizationRequest(slug = null)),
                InvitationOrganizationErrorResponseModel.MISSING_SLUG
        )
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.create(resourceTestHelper.buildCreateInvitationOrganizationRequest(slug = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_SLUG
        )
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.create(resourceTestHelper.buildCreateInvitationOrganizationRequest(customerSubscriptionDefinitionUuid = null)),
                InvitationOrganizationErrorResponseModel.MISSING_CUSTOMER_SUBSCRIPTION_DEFINITION_UUID
        )
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.create(resourceTestHelper.buildCreateInvitationOrganizationRequest(customerSubscriptionDefinitionUuid = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_CUSTOMER_SUBSCRIPTION_DEFINITION_UUID
        )
    }

    @Test
    fun `test when slug is not correct`() {
        val request = resourceTestHelper.buildCreateInvitationOrganizationRequest(slug = "^87ds")
        assertBasicErrorResultResponse(invitationOrganizationResourceClient.create(request), InvitationOrganizationErrorResponseModel.INVALID_SLUG)
    }

    @Test
    fun `test when slug is not available`() {
        val slug = uuid()
        organizationResourceTestHelper.persistOrganization(slug = slug)
        val request = resourceTestHelper.buildCreateInvitationOrganizationRequest(slug = slug)
        assertBasicErrorResultResponse(invitationOrganizationResourceClient.create(request), InvitationOrganizationErrorResponseModel.SLUG_IS_NOT_AVAILABLE)
    }

    @Test
    fun `test when correct slug is available`() {
        val request = resourceTestHelper.buildCreateInvitationOrganizationRequest()
        invitationOrganizationResourceClient.create(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it?.body?.response()?.uuid).isNotBlank()
        }
    }
}
