package com.vntana.core.rest.facade.user.component

import com.vntana.core.notification.EmailSenderService
import com.vntana.core.rest.facade.test.AbstractFacadeUnitTest
import com.vntana.core.model.user.request.SendUserMentionRequest
import com.vntana.core.model.user.enums.UserMentionedEntityTypeModel
import com.vntana.core.rest.facade.user.component.impl.UserMentionEmailSenderComponentImpl
import com.vntana.core.service.template.email.TemplateEmailService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Vardan Aivazian
 * Date: 07.10.2020
 * Time: 14:54
 */
abstract class AbstractUserMentionEmailSenderComponentImplUnitTest : AbstractFacadeUnitTest() {

    protected lateinit var mentionEmailSenderComponent: UserMentionEmailSenderComponent

    @Mock
    protected lateinit var emailSenderService: EmailSenderService

    @Mock
    protected lateinit var templateEmailService: TemplateEmailService

    protected val userMentionedUrlPrefix: String = uuid()

    protected val senderEmail: String = uuid()

    @Before
    fun prepare() {
        mentionEmailSenderComponent = UserMentionEmailSenderComponentImpl(emailSenderService,
                templateEmailService,
                userMentionedUrlPrefix,
                senderEmail
        )
    }

    fun buildSendUserMentionRequest(
            email: String? = uuid(),
            promptingUserName: String? = uuid(),
            mentionedUserName: String? = uuid(),
            entityType: UserMentionedEntityTypeModel? = UserMentionedEntityTypeModel.COMMENT,
            entityUuid: String? = uuid(),
            productUuid: String? = uuid(),
            productName: String? = uuid(),
            clientSlug: String? = uuid(),
            organizationSlug: String? = uuid()
    ): SendUserMentionRequest = SendUserMentionRequest(email, promptingUserName, mentionedUserName, entityType, entityUuid, productUuid, productName, clientSlug, organizationSlug)
}