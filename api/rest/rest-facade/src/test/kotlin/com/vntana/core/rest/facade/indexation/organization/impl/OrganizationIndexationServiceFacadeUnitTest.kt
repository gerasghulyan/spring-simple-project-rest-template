package com.vntana.core.rest.facade.indexation.organization.impl

import com.vntana.commons.api.model.response.indexation.error.IndexationErrorResponseModel
import com.vntana.core.rest.facade.indexation.organization.AbstractOrganizationIndexationServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.anyObject
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 3/25/2020
 * Time: 2:35 PM
 */
class OrganizationIndexationServiceFacadeUnitTest : AbstractOrganizationIndexationServiceFacadeUnitTest() {

    @Test
    fun `test reindexAll`() {
        val organizations = listOf(
                organizationCommonTestHelper.buildOrganization(),
                organizationCommonTestHelper.buildOrganization(),
                organizationCommonTestHelper.buildOrganization()
        )
        resetAll()
        expect(organizationService.count()).andReturn(organizations.size.toLong())
        expect(organizationService.getAll(organizationCommonTestHelper.buildGetAllOrganizationDto(size = organizations.size))).andReturn(organizationCommonTestHelper.buildOrganizationPage(
                organizations = organizations,
                pageAble = organizationCommonTestHelper.buildPageRequest(size = organizations.size)
        ))
        expect(organizationIndexationComponent.indexByOne(anyObject())).times(organizations.size - 1).andVoid()
        replayAll()
        val resultResponse = organizationIndexationServiceFacade.reindexAll()
        assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().tookMillis).isGreaterThanOrEqualTo(0L)
        verifyAll()
    }

    @Test
    fun `test reindexAll when failed on client indexation`() {
        val organizations = listOf(
                organizationCommonTestHelper.buildOrganization()
        )
        resetAll()
        expect(organizationService.count()).andReturn(organizations.size.toLong())
        expect(organizationService.getAll(organizationCommonTestHelper.buildGetAllOrganizationDto(size = organizations.size))).andReturn(organizationCommonTestHelper.buildOrganizationPage(
                organizations = organizations,
                pageAble = organizationCommonTestHelper.buildPageRequest(size = organizations.size)
        ))
        expect(organizationIndexationComponent.indexByOne(anyObject())).andThrow(IllegalStateException())
        replayAll()
        assertBasicErrorResultResponse(organizationIndexationServiceFacade.reindexAll(), IndexationErrorResponseModel.INDEXATION_ERROR)
        verifyAll()
    }
}