package com.vntana.core.listener.client

import com.vntana.cache.service.client.ClientOrganizationCacheService
import com.vntana.cache.service.client.dto.CacheClientOrganizationCreateDto
import com.vntana.cache.service.client.model.CacheClientOrganizationModel
import com.vntana.commons.indexation.payload.EntityLifecycle
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.listener.AbstractListenerUnitTest
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
class ClientOrganizationCacheUpdateEventListenerUnitTest : AbstractListenerUnitTest() {

    private lateinit var clientOrganizationCacheUpdateEventListener: ClientOrganizationCacheUpdateEventListener

    private val clientOrganizationCommonTestHelper: ClientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

    @Mock
    private lateinit var clientOrganizationCacheService: ClientOrganizationCacheService

    @Before
    fun prepare() {
        clientOrganizationCacheUpdateEventListener = ClientOrganizationCacheUpdateEventListener(clientOrganizationCacheService)
    }

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { clientOrganizationCacheUpdateEventListener.handleEvent(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test handleEvent`() {
        // test data
        val client = clientOrganizationCommonTestHelper.buildClientOrganization()
        val dto = CacheClientOrganizationCreateDto(
                client.uuid,
                client.name,
                client.slug,
                client.organization.uuid,
                client.organization.slug,
                client.imageBlobId
        )
        val model = CacheClientOrganizationModel(dto.uuid, dto.name, dto.slug, dto.organizationUuid, dto.organizationSlug, dto.imageBlobId)
        val payload = ClientOrganizationLifecyclePayload(client, EntityLifecycle.CREATED)
        resetAll()
        // expectations
        expect(clientOrganizationCacheService.cacheByUuid(dto)).andReturn(CompletableFuture.completedFuture(model))
        expect(clientOrganizationCacheService.cacheBySlug(dto)).andReturn(CompletableFuture.completedFuture(model))
        replayAll()
        // test scenario
        clientOrganizationCacheUpdateEventListener.handleEvent(payload)
        verifyAll()
    }
}