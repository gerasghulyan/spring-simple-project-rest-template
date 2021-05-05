package com.vntana.core.rest.facade.token.auth.component.precondition.impl

import com.vntana.core.model.token.auth.error.TokenAuthenticationErrorResponseModel
import com.vntana.core.rest.facade.token.auth.component.precondition.AbstractTokenAuthenticationServiceFacadePreconditionCheckerUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.anyString
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 11/13/2020
 * Time: 4:35 PM
 */
class TokenAuthenticationServiceFacadePreconditionCheckerPersistWithClientOrganizationUnitTest : AbstractTokenAuthenticationServiceFacadePreconditionCheckerUnitTest() {

    @Test
    fun `test when user not found`() {
        resetAll()
        val request = tokenAuthenticationRestTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest()
        expect(userService.existsByUuid(anyString())).andReturn(false)
        replayAll()
        preconditionChecker.checkPersistWithClientOrganization(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(TokenAuthenticationErrorResponseModel.USER_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when client organization not found`() {
        resetAll()
        val request = tokenAuthenticationRestTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest()
        expect(userService.existsByUuid(anyString())).andReturn(true)
        expect(clientOrganizationService.existsByUuid(anyString())).andReturn(false)
        replayAll()
        preconditionChecker.checkPersistWithClientOrganization(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(TokenAuthenticationErrorResponseModel.CLIENT_ORGANIZATION_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = tokenAuthenticationRestTestHelper.buildTokenAuthenticationPersistWithClientOrganizationRequest()
        expect(userService.existsByUuid(anyString())).andReturn(true)
        expect(clientOrganizationService.existsByUuid(anyString())).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkPersistWithClientOrganization(request).isPresent).isFalse()
        verifyAll()
    }
}