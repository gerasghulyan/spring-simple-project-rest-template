package com.vntana.core.indexation.producer.invitation.organization.impl;

import com.vntana.core.indexation.consumer.message.invitation.organization.InvitationOrganizationUuidAwareActionQueueMessage;
import com.vntana.core.indexation.producer.invitation.organization.InvitationOrganizationUuidAwareActionProducer;
import com.vntana.core.indexation.producer.organization.impl.OrganizationUuidAwareActionProducerImpl;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 5:42 PM
 */
@Component
public class InvitationOrganizationUuidAwareActionProducerImpl implements InvitationOrganizationUuidAwareActionProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationUuidAwareActionProducerImpl.class);

    private final String preIndexationInvitationOrganizationTopic;
    private final KafkaTemplate<String, InvitationOrganizationUuidAwareActionQueueMessage> kafkaTemplate;

    public InvitationOrganizationUuidAwareActionProducerImpl(
            @Value("${pre.indexation.organization.topic}") final String preIndexationInvitationOrganizationTopic,
            final KafkaTemplate<String, InvitationOrganizationUuidAwareActionQueueMessage> kafkaTemplate) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.preIndexationInvitationOrganizationTopic = preIndexationInvitationOrganizationTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void produce(final InvitationOrganizationUuidAwareActionQueueMessage message) {
        Assert.notNull(message, "The InvitationOrganizationUuidAwareActionQueueMessage should not be null");
        LOGGER.debug("Trying to produce InvitationOrganization uuid aware action message - {}", message);
        Try.of(() -> kafkaTemplate
                .send(
                        preIndexationInvitationOrganizationTopic,
                        message.getUuid(),
                        message
                ).get(5000L, TimeUnit.MILLISECONDS))
                .onFailure(ex -> LOGGER.error("Failed to send InvitationOrganization uuid aware action message - {} with exception - {}", message, ex))
                .getOrElseThrow(() -> {
                    throw new IllegalStateException(format("Failed to send organization uuid aware action message - %s", message));
                });
    }
}
