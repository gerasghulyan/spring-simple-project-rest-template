package com.vntana.core.listener

import org.easymock.EasyMockRunner
import org.easymock.EasyMockSupport
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by Geras Ghulyan.
 * Date: 12/9/19
 * Time: 5:48 PM
 */
@RunWith(EasyMockRunner::class)
abstract class AbstractListenerUnitTest : EasyMockSupport() {

    fun uuid(): String = UUID.randomUUID().toString()
}