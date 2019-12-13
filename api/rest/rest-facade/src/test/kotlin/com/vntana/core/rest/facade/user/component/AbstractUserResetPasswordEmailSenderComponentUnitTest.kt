package com.vntana.core.rest.facade.user.component

import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.helper.user.UserRestTestHelper
import com.vntana.core.notification.EmailSenderService
import com.vntana.core.rest.facade.test.AbstractServiceFacadeUnitTest
import com.vntana.core.rest.facade.user.component.impl.UserResetPasswordEmailSenderComponentImpl
import com.vntana.core.service.template.email.TemplateEmailService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 3:07 PM
 */
abstract class AbstractUserResetPasswordEmailSenderComponentUnitTest : AbstractServiceFacadeUnitTest() {

    protected lateinit var resetPasswordEmailSenderComponent: UserResetPasswordEmailSenderComponent

    protected val restHelper = UserRestTestHelper()

    protected val userHelper = UserCommonTestHelper()

    @Mock
    protected lateinit var emailSenderService: EmailSenderService

    @Mock
    protected lateinit var templateEmailService: TemplateEmailService

    protected val resetPasswordUrlPrefix: String = uuid()

    protected val senderEmail: String = uuid()

    @Before
    fun prepare() {
        resetPasswordEmailSenderComponent = UserResetPasswordEmailSenderComponentImpl(emailSenderService,
                templateEmailService,
                resetPasswordUrlPrefix,
                senderEmail
        )
    }
}