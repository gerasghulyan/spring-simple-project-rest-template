package com.vntana.core.rest.facade.client.component.precondition.impl

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.rest.facade.client.component.precondition.AbstractClientOrganizationServiceFacadePreconditionCheckerUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.anyString
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 11/18/2020
 * Time: 1:58 PM
 */
class ClientOrganizationFacadeCheckGetUserClientOrganizationsPreconditionCheckerUnitTest : AbstractClientOrganizationServiceFacadePreconditionCheckerUnitTest() {

    @Test
    fun `test when organizationUuid is blank`() {
        resetAll()
        replayAll()
        preconditionChecker.checkGetUserClientOrganizations(uuid(), null).let {
            assertThat(it.error).isEqualTo(ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        preconditionChecker.checkGetUserClientOrganizations(uuid(), emptyString()).let {
            assertThat(it.error).isEqualTo(ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        verifyAll()
    }

    @Test
    fun `test when userUuid is blank`() {
        resetAll()
        replayAll()
        preconditionChecker.checkGetUserClientOrganizations(null, uuid()).let {
            assertThat(it.error).isEqualTo(ClientOrganizationErrorResponseModel.MISSING_USER_UUID)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        preconditionChecker.checkGetUserClientOrganizations(emptyString(), uuid()).let {
            assertThat(it.error).isEqualTo(ClientOrganizationErrorResponseModel.MISSING_USER_UUID)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        verifyAll()
    }

    @Test
    fun `test when user not found`() {
        resetAll()
        expect(userService.existsByUuid(anyString())).andReturn(false)
        replayAll()
        preconditionChecker.checkGetUserClientOrganizations(uuid(), uuid()).let {
            assertThat(it.error).isEqualTo(ClientOrganizationErrorResponseModel.USER_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when organization not found`() {
        resetAll()
        expect(userService.existsByUuid(anyString())).andReturn(true)
        expect(organizationService.existsByUuid(anyString())).andReturn(false)
        replayAll()
        preconditionChecker.checkGetUserClientOrganizations(uuid(), uuid()).let {
            assertThat(it.error).isEqualTo(ClientOrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        expect(userService.existsByUuid(anyString())).andReturn(true)
        expect(organizationService.existsByUuid(anyString())).andReturn(true)
        replayAll()
        preconditionChecker.checkGetUserClientOrganizations(uuid(), uuid()).let {
            assertThat(it.isPresent).isFalse()
        }
        verifyAll()
    }
}