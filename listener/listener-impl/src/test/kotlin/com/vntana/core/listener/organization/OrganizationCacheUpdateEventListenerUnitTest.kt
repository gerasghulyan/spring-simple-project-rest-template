package com.vntana.core.listener.organization

import com.vntana.cache.service.organization.OrganizationCacheService
import com.vntana.cache.service.organization.dto.CacheOrganizationCreateDto
import com.vntana.cache.service.organization.model.CacheOrganizationModel
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.listener.AbstractListenerUnitTest
import com.vntana.core.listener.commons.EntityLifecycle
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CompletableFuture

/**
 * Created by Geras Ghulyan.
 * Date: 12/6/19
 * Time: 5:10 PM
 */
class OrganizationCacheUpdateEventListenerUnitTest : AbstractListenerUnitTest() {

    private lateinit var organizationCacheUpdateEventListener: OrganizationCacheUpdateEventListener

    private val organizationCommonTestHelper: OrganizationCommonTestHelper = OrganizationCommonTestHelper()

    @Mock
    private lateinit var organizationCacheService: OrganizationCacheService

    @Before
    fun prepare() {
        organizationCacheUpdateEventListener = OrganizationCacheUpdateEventListener(organizationCacheService)
    }

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { organizationCacheUpdateEventListener.handleEvent(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test handleEvent`() {
        // test data
        val dto = CacheOrganizationCreateDto(uuid(), uuid(), uuid())
        val model = CacheOrganizationModel(dto.uuid, dto.slug, dto.name)
        val organization = organizationCommonTestHelper.buildOrganization(name = dto.name, slug = dto.slug)
        organization.uuid = dto.uuid
        val payload = OrganizationLifecyclePayload(organization, EntityLifecycle.CREATED)
        resetAll()
        // expectations
        expect(organizationCacheService.cacheByUuid(dto)).andReturn(CompletableFuture.completedFuture(model))
        expect(organizationCacheService.cacheBySlug(dto)).andReturn(CompletableFuture.completedFuture(model))
        replayAll()
        // test scenario
        organizationCacheUpdateEventListener.handleEvent(payload)
        verifyAll()
    }
}