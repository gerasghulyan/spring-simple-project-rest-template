package com.vntana.core.rest.resource.invitation.organization.impl

import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.resource.invitation.organization.AbstractInvitationOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 2:45 PM
 */
class InvitationOrganizationGetByUuidWebTest : AbstractInvitationOrganizationWebTest() {

    @Test
    fun `test when not found`() {
        assertBasicErrorResultResponse(invitationOrganizationResourceClient.getByUuid(uuid()), InvitationOrganizationErrorResponseModel.NOT_FOUND)
    }

    @Test
    fun test() {
        val ownerFullName = uuid()
        val email = uuid()
        val organizationName = uuid()
        val slug = uuid()
        val customerSubscriptionDefinitionUuid = uuid()
        val uuid = resourceTestHelper.persistInvitationOrganization(
                ownerFullName = ownerFullName,
                email = email,
                organizationName = organizationName,
                slug = slug,
                customerSubscriptionDefinitionUuid = customerSubscriptionDefinitionUuid
        )
        invitationOrganizationResourceClient.getByUuid(uuid).let {
            assertBasicSuccessResultResponse(it)
            it?.body?.response()?.let { model ->
                assertThat(model.ownerFullName).isEqualTo(ownerFullName)
                assertThat(model.email).isEqualTo(email)
                assertThat(model.organizationName).isEqualTo(organizationName)
                assertThat(model.slug).isEqualTo(slug)
                assertThat(model.customerSubscriptionDefinitionUuid).isEqualTo(customerSubscriptionDefinitionUuid)
                assertThat(model.uuid).isEqualTo(uuid)
            }
        }
    }

}