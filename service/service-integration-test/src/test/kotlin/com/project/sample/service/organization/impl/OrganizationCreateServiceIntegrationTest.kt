package com.project.sample.service.organization.impl

import com.project.sample.commons.persistence.domain.organization.status.OrganizationStatus
import com.project.sample.service.organization.AbstractOrganizationServiceIntegrationTest
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Geras Ghulyan.
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
                        .hasFieldOrPropertyWithValue("status", OrganizationStatus.ACTIVE)
            }
        }
    }
}