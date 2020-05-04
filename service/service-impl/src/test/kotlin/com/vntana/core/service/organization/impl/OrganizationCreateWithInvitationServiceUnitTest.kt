package com.vntana.core.service.organization.impl

import com.vntana.core.domain.organization.Organization
import com.vntana.core.service.organization.AbstractOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan
 * Date: 10/8/19
 * Time: 6:04 PM
 */
class OrganizationCreateWithInvitationServiceUnitTest : AbstractOrganizationServiceUnitTest() {
    @Test
    fun `test create with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { organizationService.createWithInvitation(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.createWithInvitation(helper.buildCreateOrganizationFromInvitationDto(name = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.createWithInvitation(helper.buildCreateOrganizationFromInvitationDto(name = emptyString())) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.createWithInvitation(helper.buildCreateOrganizationFromInvitationDto(slug = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.createWithInvitation(helper.buildCreateOrganizationFromInvitationDto(slug = emptyString())) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.createWithInvitation(helper.buildCreateOrganizationFromInvitationDto(organizationInvitationUuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationService.createWithInvitation(helper.buildCreateOrganizationFromInvitationDto(organizationInvitationUuid = emptyString())) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test createWithInvitation`() {
        // test data
        resetAll()
        val invitationOrganization = invitationOrganizationCommonTestHelper.buildInvitationOrganization()
        val dto = helper.buildCreateOrganizationFromInvitationDto(organizationInvitationUuid = invitationOrganization.uuid)
        // expectations
        expect(invitationOrganizationService.getByUuid(dto.organizationInvitationUuid)).andReturn(invitationOrganization)
        expect(organizationRepository.findBySlug(dto.slug)).andReturn(Optional.empty())
        expect(organizationRepository.save(isA(Organization::class.java)))
                .andAnswer { getCurrentArguments()[0] as Organization }
        replayAll()
        // test scenario
        val result = organizationService.createWithInvitation(dto)
        assertThat(result)
                .hasFieldOrPropertyWithValue("name", dto.name)
                .hasFieldOrPropertyWithValue("slug", dto.slug)
        assertThat(result.invitation).isEqualTo(invitationOrganization)
        verifyAll()
    }
}