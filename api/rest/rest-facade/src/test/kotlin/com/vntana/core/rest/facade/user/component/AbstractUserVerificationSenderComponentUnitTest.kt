package com.vntana.core.rest.facade.user.component

import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.helper.user.UserRestTestHelper
import com.vntana.core.notification.EmailSenderService
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.rest.facade.user.component.impl.UserVerificationSenderComponentImpl
import com.vntana.core.service.template.email.TemplateEmailService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Arman Gevorgyan.
 * Date: 11/12/19
 * Time: 3:20 PM
 */
abstract class AbstractUserVerificationSenderComponentUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var userVerificationSenderComponent: UserVerificationSenderComponent

    protected val restHelper = UserRestTestHelper()

    protected val userHelper = UserCommonTestHelper()

    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var emailSenderService: EmailSenderService

    @Mock
    protected lateinit var templateEmailService: TemplateEmailService

    private val verificationUrlPrefix: String = uuid()

    private val senderEmail: String = uuid()

    @Before
    fun prepare() {
        userVerificationSenderComponent = UserVerificationSenderComponentImpl(userService,
                emailSenderService,
                templateEmailService,
                verificationUrlPrefix,
                senderEmail
        )
    }
}