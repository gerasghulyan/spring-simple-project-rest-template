package com.vntana.core.rest.facade.indexation.organization.impl

import com.vntana.core.model.indexation.error.OrganizationIndexationByUuidErrorResponseModel
import com.vntana.core.rest.facade.indexation.organization.AbstractOrganizationIndexationServiceFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan
 * Date: 6/3/20
 * Time: 6:48 PM
 */
class OrganizationIndexationReindexByUuidServiceFacadeUnitTest : AbstractOrganizationIndexationServiceFacadeUnitTest() {

    @Test
    fun `test with Illegal argument exception indexation fails`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertBasicErrorResultResponse(organizationIndexationServiceFacade.reindexByUuid(null), OrganizationIndexationByUuidErrorResponseModel.MISSING_ORGANIZATION_UUID)
        verifyAll()
    }

    @Test
    fun `test when organization not found`() {
        // test data
        val uuid = uuid()
        resetAll()
        // expectations
        expect(organizationService.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertBasicErrorResultResponse(organizationIndexationServiceFacade.reindexByUuid(uuid), OrganizationIndexationByUuidErrorResponseModel.ORGANIZATION_NOT_FOUND).let {

        }
        verifyAll()
    }

    @Test
    fun `test when indexation fails`() {
        // test data
        val organization = organizationCommonTestHelper.buildOrganization()
        resetAll()
        // expectations
        expect(organizationService.findByUuid(organization.uuid)).andReturn(Optional.of(organization))
        expect(organizationIndexationComponent.indexByOne(organization.uuid)).andThrow(RuntimeException())
        replayAll()
        // test scenario
        assertBasicErrorResultResponse(organizationIndexationServiceFacade.reindexByUuid(organization.uuid), OrganizationIndexationByUuidErrorResponseModel.INDEXATION_ERROR)
        verifyAll()
    }

    @Test
    fun `test when indexation success`() {
        // test data
        val organization = organizationCommonTestHelper.buildOrganization()
        resetAll()
        // expectations
        expect(organizationService.findByUuid(organization.uuid)).andReturn(Optional.of(organization))
        expect(organizationIndexationComponent.indexByOne(organization.uuid))
        replayAll()
        // test scenario
        assertBasicSuccessResultResponse(organizationIndexationServiceFacade.reindexByUuid(organization.uuid)).let {

        }
        verifyAll()
    }

}