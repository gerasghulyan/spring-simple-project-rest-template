package com.vntana.core.queue.consumer.user.impl;

import com.vntana.asset.queue.message.user.UserMentionedQueueMessage;
import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.queue.consumer.user.UserMentionedQueueMessageConsumer;
import com.vntana.core.rest.facade.user.component.UserMentionEmailSenderComponent;
import com.vntana.core.rest.facade.user.component.dto.SendUserMentionDto;
import com.vntana.core.rest.facade.user.component.dto.UserMentionedEntityType;
import com.vntana.core.service.client.ClientOrganizationService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/7/20
 * Time: 2:39 PM
 */
@Component
class UserMentionedQueueMessageConsumerImpl implements UserMentionedQueueMessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMentionedQueueMessageConsumerImpl.class);
    private final UserService userService;
    private final OrganizationService organizationService;
    private final ClientOrganizationService clientOrganizationService;
    private final UserMentionEmailSenderComponent mentionEmailSenderComponent;

    public UserMentionedQueueMessageConsumerImpl(final UserService userService, final OrganizationService organizationService, final ClientOrganizationService clientOrganizationService, final UserMentionEmailSenderComponent mentionEmailSenderComponent) {
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
        Assert.notNull(message, "The user mentioned queue message should not be null");
        final User user = userService.getByUuid(message.getMentionedByUserUuid());
        final Organization organization = organizationService.getByUuid(message.getOrganizationUuid());
        final ClientOrganization client = clientOrganizationService.getByUuid(message.getClientUuid());
        final UserMentionedEntityType userMentionedEntityType = UserMentionedEntityType.valueOf(message.getUserMentionedInType().name());
        for (final String mentionedUserUuid : message.getMentionedUserUuids()) {
            final User mentionedUser = userService.getByUuid(mentionedUserUuid);
            final SendUserMentionDto sendUserMentionDto = new SendUserMentionDto(
                    mentionedUser.getEmail(),
                    user.getFullName(),
                    mentionedUser.getFullName(),
                    userMentionedEntityType,
                    message.getMentionedInEntityUuid(),
                    message.getProductUuid(),
                    message.getProductName(),
                    client.getSlug(),
                    organization.getSlug()
            );
            mentionEmailSenderComponent.sendMentionedUsersEmails(sendUserMentionDto);
        }
        LOGGER.debug("Successfully processed consumption of user mentioned queue message - {}", message);
    }
}
