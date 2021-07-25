package com.vntana.core.rest.facade.user.external.impl

import com.vntana.core.domain.user.external.ExternalUser
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.model.user.external.request.GetOrCreateExternalUserRequest
import com.vntana.core.rest.facade.user.external.AbstractExternalUserServiceFacadeUnitTest
import com.vntana.core.service.user.external.dto.GetOrCreateExternalUserDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.eq
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 7/16/2021
 * Time: 2:20 PM
 */
class ExternalUserSignUpOrSignInServiceFacadeUnitTest : AbstractExternalUserServiceFacadeUnitTest() {

    @Test
    fun `test when organization not exists`() {
        // test data
        resetAll()
        val externalUuid = uuid()
        val organizationUuid = uuid()
        val clientOrganization = uuid()
        val request = GetOrCreateExternalUserRequest(
            externalUuid,
            organizationUuid,
            clientOrganization
        )
        // expectations
        expect(organizationService.findByUuid(eq(organizationUuid))).andReturn(Optional.empty())
        replayAll()
        //test scenario
        assertBasicErrorResultResponse(
            externalUserServiceFacade.getOrCreateExternalUser(request),
            UserErrorResponseModel.ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun test() {
        // test data
        resetAll()
        val externalUuid = uuid()
        val organization = organizationHelper.buildOrganization()
        val clientOrganization = clientOrganizationTestHelper.buildClientOrganization(organization = organization)
        val request = GetOrCreateExternalUserRequest(
            externalUuid,
            organization.uuid,
            clientOrganization.uuid
        )
        val dto = GetOrCreateExternalUserDto(externalUuid, organization, clientOrganization)
        val externalUser = ExternalUser(externalUuid, userHelper.buildUser())
        // expectations
        expect(organizationService.findByUuid(eq(organization.uuid))).andReturn(Optional.of(organization))
        expect(clientOrganizationService.findByUuid(eq(clientOrganization.uuid))).andReturn(
            Optional.of(
                clientOrganization
            )
        )
        expect(externalUserService.getOrCreate(eq(dto))).andReturn(externalUser)
        replayAll()
        //test scenario
        externalUserServiceFacade.getOrCreateExternalUser(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().externalUuid).isEqualTo(externalUuid)
            assertThat(it.response().userUuid).isEqualTo(externalUser.targetUser.uuid)
        }
    }
}