package com.vntana.core.service.organization.test

import com.vntana.core.domain.organization.status.OrganizationStatus
import com.vntana.core.service.organization.AbstractOrganizationServiceIntegrationTest
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 10:51 AM
 */
class OrganizationCreateServiceIntegrationTest : AbstractOrganizationServiceIntegrationTest() {
    @Test
    fun `test create`() {
        // given
        commonTestHelper.buildCreateOrganizationDto().let { dto ->
            // when
            organizationService.create(dto).let { organization ->
                // then
                flushAndClear()
                assertThat(organization)
                        .hasFieldOrPropertyWithValue("name", StringUtils.trim(dto.name))
                        .hasFieldOrPropertyWithValue("slug", StringUtils.trim(dto.slug))
                        .hasFieldOrPropertyWithValue("imageBlobId", dto.imageBlobId)
                        .hasFieldOrPropertyWithValue("status", OrganizationStatus.ACTIVE)
            }
        }
    }
}