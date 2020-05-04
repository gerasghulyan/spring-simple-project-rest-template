package com.vntana.core.service.organization.impl

import com.vntana.core.domain.organization.status.OrganizationStatus
import com.vntana.core.service.organization.AbstractOrganizationServiceIntegrationTest
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan
 * Date: 10/9/19
 * Time: 10:51 AM
 */
class OrganizationCreateWithInvitationServiceIntegrationTest : AbstractOrganizationServiceIntegrationTest() {
    @Test
    fun `test createWithInvitation`() {
        // given
        val invitation = invitationOrganizationIntegrationTestHelper.persistInvitationOrganization()
        commonTestHelper.buildCreateOrganizationFromInvitationDto(organizationInvitationUuid = invitation.uuid).let { dto ->
            // when
            organizationService.createWithInvitation(dto).let { organization ->
                // then
                flushAndClear()
                assertThat(organization)
                        .hasFieldOrPropertyWithValue("name", StringUtils.trim(dto.name))
                        .hasFieldOrPropertyWithValue("slug", StringUtils.trim(dto.slug))
                        .hasFieldOrPropertyWithValue("status", OrganizationStatus.ACTIVE)
                assertThat(organization.invitation).isEqualTo(invitation)
            }
        }
    }
}