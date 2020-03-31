package com.vntana.core.service.organization.impl

import com.vntana.core.service.organization.AbstractOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

/**
 * Created by Arman Gevorgyan.
 * Date: 2/13/20
 * Time: 2:32 PM
 */
class OrganizationGetAllServiceUnitTest : AbstractOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid argument`() {
        resetAll()
        replayAll()
        assertThatThrownBy { organizationService.getAll(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test getAll not found`() {
        val dto = helper.buildGetAllOrganizationDto()
        resetAll()
        expect(organizationRepository.findAll(PageRequest.of(dto.page, dto.size))).andReturn(Page.empty())
        replayAll()
        organizationService.getAll(dto).let {
            assertThat(it).isEmpty()
        }
        verifyAll()
    }

    @Test
    fun `test getAll`() {
        val organizations = listOf(helper.buildOrganization(), helper.buildOrganization())
        val dto = helper.buildGetAllOrganizationDto(size = organizations.size)
        resetAll()
        expect(organizationRepository.findAll(PageRequest.of(dto.page, dto.size))).andReturn(helper.buildOrganizationPage(
                organizations = organizations,
                pageAble = helper.buildPageRequest(dto.page, dto.size)
        ))
        replayAll()
        organizationService.getAll(dto).let {
            assertThat(it.content).isNotEmpty
            assertThat(it.totalPages).isEqualTo(dto.page + 1)
            assertThat(it.totalElements).isEqualTo(dto.size.toLong())
            assertThat(it).containsExactlyElementsOf(organizations)
        }
        verifyAll()
    }
}