package com.vntana.core.queue.consumer.user.impl;

import com.vntana.asset.queue.message.user.UserMentionedQueueMessage;
import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.model.user.enums.UserMentionedEntityTypeModel;
import com.vntana.core.model.user.request.SendUserMentionRequest;
import com.vntana.core.queue.consumer.user.UserMentionedQueueMessageConsumer;
import com.vntana.core.rest.facade.user.component.UserMentionEmailSenderComponent;
import com.vntana.core.service.client.OrganizationClientService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/7/20
 * Time: 2:39 PM
 */
@Component
class UserMentionedQueueMessageConsumerImpl implements UserMentionedQueueMessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMentionedQueueMessageConsumerImpl.class);
    private final boolean isEmailNotificationsEnabled;
    private final UserService userService;
    private final OrganizationService organizationService;
    private final OrganizationClientService clientOrganizationService;
    private final UserMentionEmailSenderComponent mentionEmailSenderComponent;

    public UserMentionedQueueMessageConsumerImpl(
            final UserService userService,
            final OrganizationService organizationService,
            final OrganizationClientService clientOrganizationService,
            final UserMentionEmailSenderComponent mentionEmailSenderComponent,
            @Value("${user.mentioning.email.notifier.enabled}") final boolean isEmailNotificationsEnabled) {
        this.isEmailNotificationsEnabled = isEmailNotificationsEnabled;
        this.userService = userService;
        this.organizationService = organizationService;
        this.clientOrganizationService = clientOrganizationService;
        this.mentionEmailSenderComponent = mentionEmailSenderComponent;
    }

    @KafkaListener(
            topics = {"${user.mentioning.topic}"},
            groupId = "${user.mentioning.group}",
            concurrency = "${user.mentioning.concurrency}",
            properties = {"auto.offset.reset=earliest", "enable.auto.commit=false"}
    )
    @Override
    public void consume(final UserMentionedQueueMessage message) {
        LOGGER.debug("Trying to consume user mentioned queue message - {}", message);
        notNull(message, "The user mentioned queue message should not be null");
        if (!isEmailNotificationsEnabled) {
            LOGGER.debug("Email notification sending for message - {} has been disabled. Exiting method...", message);
            return;
        }
        final User user = userService.getByUuid(message.getMentionedByUserUuid());
        final Organization organization = organizationService.getByUuid(message.getOrganizationUuid());
        final ClientOrganization client = clientOrganizationService.getByUuid(message.getClientUuid());
        final UserMentionedEntityTypeModel userMentionedEntityType = UserMentionedEntityTypeModel.valueOf(message.getUserMentionedInType().name());
        message.getMentionedUserUuids().forEach(mentionedUserUuid -> {
            final SendUserMentionRequest sendUserMentionRequest = createSendUserMentionRequest(mentionedUserUuid, user.getFullName(), userMentionedEntityType, message, client.getSlug(), organization.getSlug());
            LOGGER.debug("Created sendUserMentionRequest - {}", sendUserMentionRequest);
            mentionEmailSenderComponent.sendMentionedUsersEmails(sendUserMentionRequest);
            LOGGER.debug("Successfully sent email to mentioned users for request - {}", sendUserMentionRequest);
        });
        LOGGER.debug("Successfully processed consumption of user mentioned queue message - {}", message);
    }

    private SendUserMentionRequest createSendUserMentionRequest(
            final String mentionedUserUuid,
            final String promptingUserName,
            final UserMentionedEntityTypeModel userMentionedEntityType,
            final UserMentionedQueueMessage message,
            final String clientSlug,
            final String organizationSlug
    ) {
        final User mentionedUser = userService.getByUuid(mentionedUserUuid);
        hasText(mentionedUser.getEmail(), "The email should not be null or empty");
        hasText(promptingUserName, "The prompting user full name should not be null or empty");
        hasText(mentionedUser.getFullName(), "The mentioned user full name should not be null or empty");
        notNull(userMentionedEntityType, "The UserMentionedEntityType should not be null");
        hasText(message.getMentionedInEntityUuid(), "The entityUuid should not be null or empty");
        hasText(message.getProductUuid(), "The productUuid should not be null or empty");
        hasText(message.getProductName(), "The productName should not be null or empty");
        hasText(clientSlug, "The clientSlug should not be null or empty");
        hasText(organizationSlug, "The organizationSlug should not be null or empty");
        return new SendUserMentionRequest(
                mentionedUser.getEmail(),
                promptingUserName,
                mentionedUser.getFullName(),
                userMentionedEntityType,
                message.getMentionedInEntityUuid(),
                message.getProductUuid(),
                message.getProductName(),
                clientSlug,
                organizationSlug
        );
    }
}
