package com.vntana.core.rest.resource.organization.test

import com.vntana.commons.queue.model.MessageActionType
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.rest.resource.organization.AbstractOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.ArgumentMatchers.argThat
import org.mockito.Mockito
import org.mockito.Mockito.times

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 5:53 PM
 */
class OrganizationCreateWebTest : AbstractOrganizationWebTest() {

    @Test
    fun `test create with invalid arguments`() {
        val response1 = organizationResourceClient.create(
                resourceTestHelper.buildCreateOrganizationRequest(slug = null)
        )
        assertBasicErrorResultResponse(response1, OrganizationErrorResponseModel.MISSING_SLUG)
        val response2 = organizationResourceClient.create(
                resourceTestHelper.buildCreateOrganizationRequest(name = null)
        )
        assertBasicErrorResultResponse(response2, OrganizationErrorResponseModel.MISSING_NAME)
    }

    @Test
    fun `test create`() {
        val email = userResourceTestHelper.email()
        val userUuid = userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest(email = email)).response().uuid
        val request = resourceTestHelper.buildCreateOrganizationRequest(userUuid = userUuid)
        val response = organizationResourceClient.create(request)
        assertThat(response.success()).isTrue()
        assertThat(response.response().uuid).isNotBlank()
        Mockito.verify(customerResourceClient, times(2)).create(argThat { inRequest -> inRequest.email == email })
        Mockito.verify(organizationUuidAwareActionProducer).produce(Mockito.argThat { argument ->
            argument.messageActionType == MessageActionType.CREATED && argument.uuid == response.response().uuid
        })
        Mockito.verify(organizationUuidAwareActionProducer).produce(Mockito.argThat { argument ->
            argument.messageActionType == MessageActionType.CREATED
        })
    }
}