package com.vntana.core.queue.consumer.user.impl

import com.vntana.core.queue.consumer.AbstractQueueConsumerUnitTest
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 10/7/20
 * Time: 2:44 PM
 */
class UserMentionedQueueMessageConsumerImplUnitTest : AbstractQueueConsumerUnitTest() {

    @Test
    fun `test`() {
        resetAll()
        replayAll()
        verifyAll()
    }
}