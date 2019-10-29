package com.vntana.core.notification

import org.easymock.EasyMockRunner
import org.easymock.EasyMockSupport
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 10/29/19
 * Time: 10:52 AM
 */
@RunWith(EasyMockRunner::class)
abstract class AbstractNotificationUnitTest : EasyMockSupport() {
    fun uuid(): String = UUID.randomUUID().toString()
}