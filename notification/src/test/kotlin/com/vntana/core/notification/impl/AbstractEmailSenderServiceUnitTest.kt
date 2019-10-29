package com.vntana.core.notification.impl

import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient
import com.vntana.core.notification.AbstractNotificationUnitTest
import com.vntana.core.notification.EmailSenderService
import org.easymock.Mock
import org.junit.Before
import java.util.concurrent.Executor

/**
 * Created by Arman Gevorgyan.
 * Date: 10/29/19
 * Time: 10:58 AM
 */
abstract class AbstractEmailSenderServiceUnitTest : AbstractNotificationUnitTest() {

    lateinit var emailSenderService: EmailSenderService

    @Mock
    lateinit var emailNotificationResourceClient: EmailNotificationResourceClient

    @Mock
    lateinit var executor: Executor

    @Before
    fun prepare() {
        emailSenderService = EmailSenderServiceImpl(executor, emailNotificationResourceClient)
    }
    
}