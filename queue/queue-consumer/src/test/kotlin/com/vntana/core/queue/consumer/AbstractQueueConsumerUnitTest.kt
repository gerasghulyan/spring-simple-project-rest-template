package com.vntana.core.queue.consumer

import org.easymock.EasyMockRunner
import org.easymock.EasyMockSupport
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 10/7/20
 * Time: 2:31 PM
 */
@RunWith(EasyMockRunner::class)
abstract class AbstractQueueConsumerUnitTest : EasyMockSupport() {
    protected fun uuid(): String = UUID.randomUUID().toString()
}
