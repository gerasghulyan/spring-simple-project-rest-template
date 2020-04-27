package com.vntana.core.rest.facade.organization.component.precondition.impl

import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.rest.facade.organization.component.precondition.AbstractOrganizationServicePreconditionCheckerUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 27.04.20
 * Time: 10:11
 */
class OrganizationFacadeCheckGetOrganizationInvitationPreconditionCheckerUnitTest : AbstractOrganizationServicePreconditionCheckerUnitTest() {

    @Test
    fun `test when organizationUuid is blank`() {
        resetAll()
        replayAll()
        preconditionChecker.checkGetOrganizationInvitation(null).let {
            Assertions.assertThat(it.error).isEqualTo(OrganizationErrorResponseModel.MISSING_UUID)
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        preconditionChecker.checkGetOrganizationInvitation("").let {
            Assertions.assertThat(it.error).isEqualTo(OrganizationErrorResponseModel.MISSING_UUID)
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        }
        verifyAll()
    }

    @Test
    fun `test when organization not found`() {
        resetAll()
        val organizationUuid = uuid()
        expect(organizationService.existsByUuid(organizationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkGetOrganizationInvitation(organizationUuid).let {
            Assertions.assertThat(it.error).isEqualTo(OrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND)
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when organization invitation not found`() {
        resetAll()
        val organization = commonTestHelper.buildOrganization()
        expect(organizationService.existsByUuid(organization.uuid)).andReturn(true)
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        replayAll()
        preconditionChecker.checkGetOrganizationInvitation(organization.uuid).let {
            Assertions.assertThat(it.error).isEqualTo(OrganizationErrorResponseModel.ORGANIZATION_INVITATION_NOT_FOUND)
            Assertions.assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

}