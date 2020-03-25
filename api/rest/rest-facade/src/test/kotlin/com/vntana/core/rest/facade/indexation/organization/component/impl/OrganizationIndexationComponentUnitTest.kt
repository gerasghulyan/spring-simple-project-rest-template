package com.vntana.core.rest.facade.indexation.organization.component.impl

import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.rest.facade.indexation.organization.component.OrganizationIndexationComponent
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.organization.mediator.OrganizationUuidAwareLifecycleMediator
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

/**
 * Created by Manuk Gharslyan.
 * Date: 3/25/2020
 * Time: 2:26 PM
 */
class OrganizationIndexationComponentUnitTest : AbstractServiceFacadeUnitTest() {
    private lateinit var organizationIndexationComponent: OrganizationIndexationComponent

    private val organizationCommonTestHelper = OrganizationCommonTestHelper()

    @Mock
    private lateinit var organizationUuidAwareLifecycleMediator: OrganizationUuidAwareLifecycleMediator

    @Mock
    private lateinit var organizationService: OrganizationService

    @Before
    fun prepare() {
        organizationIndexationComponent = OrganizationIndexationComponentImpl(organizationUuidAwareLifecycleMediator, organizationService)
    }

    @Test
    fun `test indexByOne with invalid argument`() {
        resetAll()
        replayAll()
        assertThatThrownBy { organizationIndexationComponent.indexByOne(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { organizationIndexationComponent.indexByOne(emptyString()) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test indexByOne with removed organization`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        organization.removed = LocalDateTime.now()
        resetAll()
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        expect(organizationUuidAwareLifecycleMediator.onDeleted(organization.uuid)).andVoid()
        replayAll()
        organizationIndexationComponent.indexByOne(organization.uuid)
        verifyAll()
    }

    @Test
    fun `test indexByOne an organization`() {
        val organization = organizationCommonTestHelper.buildOrganization()
        resetAll()
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        expect(organizationUuidAwareLifecycleMediator.onUpdated(organization.uuid)).andVoid()
        replayAll()
        organizationIndexationComponent.indexByOne(organization.uuid)
        verifyAll()
    }
}