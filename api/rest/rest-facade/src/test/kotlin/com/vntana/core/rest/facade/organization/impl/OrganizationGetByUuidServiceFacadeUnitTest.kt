package com.vntana.core.rest.facade.organization.impl

import com.vntana.core.rest.facade.organization.AbstractOrganizationServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 3:54 PM
 */
class OrganizationGetByUuidServiceFacadeUnitTest : AbstractOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test getByUuid with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { organizationServiceFacade.getByUuid(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test getByUuid`() {
        // test data
        resetAll()
        val organization = commonTestHelper.buildOrganization()
        // expectations
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        replayAll()
        // test scenario
        val resultResponse = organizationServiceFacade.getByUuid(organization.uuid)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().uuid).isEqualTo(organization.uuid)
        assertThat(resultResponse.response().name).isEqualTo(organization.name)
        assertThat(resultResponse.response().slug).isEqualTo(organization.slug)
        assertThat(resultResponse.response().imageBlobId).isEqualTo(organization.imageBlobId)
        assertThat(resultResponse.response().created).isEqualTo(organization.created)
        verifyAll()
    }
}