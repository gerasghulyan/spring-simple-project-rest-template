package com.vntana.core.listener.whitelist

import com.vntana.cache.service.whitelist.WhitelistIpCacheService
import com.vntana.cache.service.whitelist.dto.CacheWhitelistCreateDto
import com.vntana.core.helper.unit.whitelist.WhitelistIpCommonTestHelper
import com.vntana.core.listener.AbstractListenerUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CompletableFuture

/**
 * Created by Arman Gevorgyan.
 * Date: 2/11/20
 * Time: 11:33 AM
 */
class WhitelistIpCacheUpdateEventListenerUnitTest : AbstractListenerUnitTest() {

    private val helper = WhitelistIpCommonTestHelper()

    private lateinit var whitelistIpCacheUpdateEventListener: WhitelistIpCacheUpdateEventListener

    @Mock
    private lateinit var whitelistIpCacheService: WhitelistIpCacheService

    @Before
    fun prepare() {
        whitelistIpCacheUpdateEventListener = WhitelistIpCacheUpdateEventListenerImpl(whitelistIpCacheService)
    }

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { whitelistIpCacheUpdateEventListener.handleEvent(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val dto = helper.buildSaveWhitelistIpLifecycleDto()
        val cacheDto = CacheWhitelistCreateDto(dto.organizationUuid, dto.organizationSlug, dto.ips)
        val payload = WhitelistIpLifecyclePayload(dto, WhitelistIpLifecycle.SAVED)
        resetAll()
        expect(whitelistIpCacheService.cacheByOrganizationUuid(cacheDto)).andReturn(CompletableFuture())
        expect(whitelistIpCacheService.cacheByOrganizationSlug(cacheDto)).andReturn(CompletableFuture())
        replayAll()
        whitelistIpCacheUpdateEventListener.handleEvent(payload)
        verifyAll()
    }
}