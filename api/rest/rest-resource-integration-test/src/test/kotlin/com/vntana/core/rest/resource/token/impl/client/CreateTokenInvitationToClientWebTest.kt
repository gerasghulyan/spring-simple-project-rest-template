package com.vntana.core.rest.resource.token.impl.client

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.user.request.SingleUserInvitationToClientRequestModel
import com.vntana.core.model.token.request.CreateTokenUserInvitationToClientRequest
import com.vntana.core.model.token.request.InvitationUuidAndTokenRequestModel
import com.vntana.core.rest.resource.token.AbstractTokenWebTest
import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/24/20
 * Time: 2:57 PM
 */
class CreateTokenInvitationToClientWebTest : AbstractTokenWebTest() {
    
    @Test
    fun test() {
        val organization = organizationResourceTestHelper.persistOrganization().response()
        val client = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organization.uuid).response()
        val inviter = userResourceTestHelper.persistUser().response()
        userRoleResourceTestHelper.grantUserClientRole(inviter.uuid, client.uuid, UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        val invitationUuid = invitationUserResourceTestHelper
                .persistInvitationUserToClient(
                        listOf(SingleUserInvitationToClientRequestModel(client.uuid, UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)),
                        uuid(),
                        inviter.uuid,
                        organization.uuid
                )?.get(0)
        val request = CreateTokenUserInvitationToClientRequest(
                listOf(InvitationUuidAndTokenRequestModel(invitationUuid, uuid()))
        )
        tokenResourceClient.createTokenInvitationUserToClient(request).let {
            assertBasicSuccessResultResponse(it)
            Assertions.assertThat(it?.body?.response()?.uuids).isNotEmpty
        }
    }
}