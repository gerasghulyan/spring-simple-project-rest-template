package com.vntana.core.listener.whitelist

import com.vntana.cache.service.whitelist.WhitelistIpCacheService
import com.vntana.core.listener.AbstractListenerUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.Mock
import org.junit.Before
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 2/11/20
 * Time: 11:33 AM
 */
class WhitelistIpCacheUpdateEventListenerUnitTest : AbstractListenerUnitTest() {

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
}