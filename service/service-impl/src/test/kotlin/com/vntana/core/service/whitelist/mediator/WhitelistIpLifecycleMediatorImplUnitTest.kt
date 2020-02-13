package com.vntana.core.service.whitelist.mediator

import com.vntana.core.service.AbstractServiceUnitTest
import com.vntana.core.service.whitelist.mediator.impl.WhitelistIpLifecycleMediatorImpl
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.Mock
import org.junit.Before
import org.junit.Test
import org.springframework.context.ApplicationEventPublisher

/**
 * Created by Arman Gevorgyan.
 * Date: 2/11/20
 * Time: 10:14 AM
 */
class WhitelistIpLifecycleMediatorImplUnitTest : AbstractServiceUnitTest() {

    private lateinit var whitelistIpLifecycleMediator: WhitelistIpLifecycleMediator

    @Mock
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @Before
    fun prepare() {
        whitelistIpLifecycleMediator = WhitelistIpLifecycleMediatorImpl(applicationEventPublisher)
    }

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { whitelistIpLifecycleMediator.onSaved(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }
}