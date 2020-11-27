package com.vntana.core.rest.resource.invitation.user.impl

import com.vntana.core.model.invitation.InvitationStatusModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.resource.invitation.user.AbstractInvitationUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 9:11 PM
 */
class InvitationUserToOrganizationGetByTokenWebTest : AbstractInvitationUserWebTest() {

    @Test
    fun `test token not found`() {
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                invitationUserResourceClient.getByTokenInvitationToOrganization(uuid()),
                InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN
        )
    }

    @Test
    fun `test token has been expired`() {
        val token = uuid()
        tokenResourceTestHelper.persistTokenInvitationUserToOrganization(token = token)
        tokenResourceTestHelper.expire(token)
        assertBasicErrorResultResponse(
                HttpStatus.BAD_REQUEST,
                invitationUserResourceClient.getByTokenInvitationToOrganization(token),
                InvitationUserErrorResponseModel.INVALID_INVITATION_TOKEN
        )
    }

    @Test
    fun `test when invited user does not exist`() {
        val token = uuid()
        val email = email()
        val organizationName = uuid()
        val organizationUuid = organizationResourceTestHelper.persistOrganization(name = organizationName).response().uuid
        val inviterUserFullName = uuid()
        val inviterUserUuid = userResourceTestHelper.persistUser(createUserRequest = userResourceTestHelper.buildCreateUserRequest(fullName = inviterUserFullName)).response().uuid
        val invitationUserUuid = resourceTestHelper.persistInvitationUserToOrganization(inviterUserUuid = inviterUserUuid, organizationUuid = organizationUuid, email = email)
        tokenResourceTestHelper.persistTokenInvitationUserToOrganization(invitationUserUuid = invitationUserUuid, token = token)
        val responseEntity = invitationUserResourceClient.getByTokenInvitationToOrganization(token)
        assertBasicSuccessResultResponse(responseEntity)
        val response = responseEntity?.body?.response()
        assertThat(response?.uuid).isNotBlank()
        assertThat(response?.invitedUserExists).isFalse()
        assertThat(response?.inviterUserFullName).isEqualTo(inviterUserFullName)
        assertThat(response?.organizationName).isEqualTo(organizationName)
        assertThat(response?.invitedUserEmail).isEqualTo(email)
        assertThat(response?.status).isEqualTo(InvitationStatusModel.INVITED)
    }

    @Test
    fun `test when invited user exists`() {
        val token = uuid()
        val email = email()
        userResourceTestHelper.persistUser(createUserRequest = userResourceTestHelper.buildCreateUserRequest(email = email)).response().uuid
        val organizationName = uuid()
        val organizationUuid = organizationResourceTestHelper.persistOrganization(name = organizationName).response().uuid
        val inviterUserFullName = uuid()
        val inviterUserUuid = userResourceTestHelper.persistUser(createUserRequest = userResourceTestHelper.buildCreateUserRequest(fullName = inviterUserFullName)).response().uuid
        val invitationUserUuid = resourceTestHelper.persistInvitationUserToOrganization(inviterUserUuid = inviterUserUuid, organizationUuid = organizationUuid, email = email)
        tokenResourceTestHelper.persistTokenInvitationUserToOrganization(invitationUserUuid = invitationUserUuid, token = token)
        val responseEntity = invitationUserResourceClient.getByTokenInvitationToOrganization(token)
        assertBasicSuccessResultResponse(responseEntity)
        val response = responseEntity?.body?.response()
        assertThat(response?.uuid).isNotBlank()
        assertThat(response?.invitedUserExists).isTrue()
        assertThat(response?.inviterUserFullName).isEqualTo(inviterUserFullName)
        assertThat(response?.organizationName).isEqualTo(organizationName)
        assertThat(response?.invitedUserEmail).isEqualTo(email)
        assertThat(response?.status).isEqualTo(InvitationStatusModel.INVITED)
    }
}