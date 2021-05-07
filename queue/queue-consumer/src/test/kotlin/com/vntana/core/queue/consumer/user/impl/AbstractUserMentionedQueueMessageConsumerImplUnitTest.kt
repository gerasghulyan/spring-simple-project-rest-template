package com.vntana.core.queue.consumer.user.impl

import com.vntana.asset.queue.message.user.UserMentionedInType
import com.vntana.asset.queue.message.user.UserMentionedQueueMessage
import com.vntana.commons.queue.model.MessageActionType
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.model.user.enums.UserMentionedEntityTypeModel
import com.vntana.core.model.user.request.SendUserMentionRequest
import com.vntana.core.queue.consumer.AbstractQueueConsumerUnitTest
import com.vntana.core.queue.consumer.user.UserMentionedQueueMessageConsumer
import com.vntana.core.rest.facade.user.component.UserMentionEmailSenderComponent
import com.vntana.core.service.client.ClientOrganizationService
import com.vntana.core.service.organization.OrganizationService
import com.vntana.core.service.user.UserService
import org.easymock.Mock
import org.junit.Before

/**
 * Created by Vardan Aivazian
 * Date: 07.05.2021
 * Time: 14:40
 */
abstract class AbstractUserMentionedQueueMessageConsumerImplUnitTest : AbstractQueueConsumerUnitTest() {

    protected lateinit var userMentionedQueueMessageConsumer: UserMentionedQueueMessageConsumer

    protected val userCommonTestHelper = UserCommonTestHelper()
    protected val organizationCommonTestHelper = OrganizationCommonTestHelper()
    protected val clientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()
    
    @Mock
    protected lateinit var userService: UserService

    @Mock
    protected lateinit var organizationService: OrganizationService

    @Mock
    protected lateinit var clientOrganizationService: ClientOrganizationService

    @Mock
    protected lateinit var userMentionEmailSenderComponent: UserMentionEmailSenderComponent

    @Before
    fun prepare() {
        userMentionedQueueMessageConsumer = UserMentionedQueueMessageConsumerImpl(
                userService,
                organizationService,
                clientOrganizationService,
                userMentionEmailSenderComponent,
                true
        )
    }

    fun buildUserMentionedQueueMessage(
            messageActionType: MessageActionType? = MessageActionType.DELETED,
            organizationUuid: String? = uuid(),
            clientUuid: String? = uuid(),
            mentionedInEntityUuid: String? = uuid(),
            mentionedByUserUuid: String? = uuid(),
            mentionedUserUuids: Set<String>? = setOf(uuid(), uuid()),
            productUuid: String? = uuid(),
            productName: String? = uuid(),
            userMentionedInType: UserMentionedInType? = UserMentionedInType.ANNOTATION
    ): UserMentionedQueueMessage {
        return UserMentionedQueueMessage(
                organizationUuid,
                clientUuid,
                messageActionType,
                mentionedInEntityUuid,
                mentionedByUserUuid,
                mentionedUserUuids,
                productName,
                productUuid,
                userMentionedInType)
    }
    
    fun buildSendUserMentionRequest(
            mentionedUserUuid: String? = uuid(),
            mentionedUserEmail: String? = uuid(),
            mentionedUserName: String? = uuid(),
            promptingUserName: String? = uuid(),
            userMentionedEntityType: UserMentionedEntityTypeModel? = UserMentionedEntityTypeModel.ANNOTATION,
            message: UserMentionedQueueMessage? = buildUserMentionedQueueMessage(),
            clientSlug: String? = uuid(),
            organizationSlug: String? = uuid()
    ): SendUserMentionRequest? {
        return SendUserMentionRequest(
                mentionedUserEmail,
                promptingUserName,
                mentionedUserName,
                userMentionedEntityType,
                message?.mentionedInEntityUuid,
                message?.productUuid,
                message?.productName,
                clientSlug,
                organizationSlug
        )
    }
}