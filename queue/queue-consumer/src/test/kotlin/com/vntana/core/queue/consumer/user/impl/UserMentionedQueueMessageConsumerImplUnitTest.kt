package com.vntana.core.queue.consumer.user.impl

import com.vntana.core.model.user.enums.UserMentionedEntityTypeModel
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 10/7/20
 * Time: 2:44 PM
 */
class UserMentionedQueueMessageConsumerImplUnitTest : AbstractUserMentionedQueueMessageConsumerImplUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userMentionedQueueMessageConsumer.consume(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val mentionedByUser = userCommonTestHelper.buildUser()
        val mentioned = userCommonTestHelper.buildUser()
        val organization = organizationCommonTestHelper.buildOrganization()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        val mentionedUserUuid1 = uuid()
        val message = buildUserMentionedQueueMessage(
                mentionedByUserUuid = mentionedByUser.uuid,
                mentionedUserUuids = setOf(mentionedUserUuid1)
        )
        val sendUser1MentionRequest = buildSendUserMentionRequest(
                mentionedUserEmail = mentioned.email,
                mentionedUserName = mentioned.fullName,
                promptingUserName = mentionedByUser.fullName,
                userMentionedEntityType = UserMentionedEntityTypeModel.ANNOTATION,
                message = message,
                clientSlug = clientOrganization.slug,
                organizationSlug = organization.slug
        )
        resetAll()
        expect(userService.getByUuid(message.mentionedByUserUuid)).andReturn(mentionedByUser)
        expect(organizationService.getByUuid(message.organizationUuid)).andReturn(organization)
        expect(clientOrganizationService.getByUuid(message.clientUuid)).andReturn(clientOrganization)
        expect(userService.getByUuid(mentionedUserUuid1)).andReturn(mentioned)
        expect(userMentionEmailSenderComponent.sendMentionedUsersEmails(sendUser1MentionRequest)).andVoid()
        replayAll()
        userMentionedQueueMessageConsumer.consume(message)
        verifyAll()
    }
}