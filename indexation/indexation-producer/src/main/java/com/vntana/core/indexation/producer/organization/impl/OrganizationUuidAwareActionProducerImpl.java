package com.vntana.core.indexation.producer.organization.impl;

import com.vntana.core.indexation.consumer.message.organization.OrganizationUuidAwareActionQueueMessage;
import com.vntana.core.indexation.producer.organization.OrganizationUuidAwareActionProducer;
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
 * Created by Manuk Gharslyan.
 * Date: 3/25/2020
 * Time: 12:27 PM
 */
@Component
public class OrganizationUuidAwareActionProducerImpl implements OrganizationUuidAwareActionProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationUuidAwareActionProducerImpl.class);

    private final String preIndexationOrganizationTopic;
    private final KafkaTemplate<String, OrganizationUuidAwareActionQueueMessage> kafkaTemplate;

    public OrganizationUuidAwareActionProducerImpl(
            @Value("${pre-indexation.organization.topic}") final String preIndexationOrganizationTopic,
            final KafkaTemplate<String, OrganizationUuidAwareActionQueueMessage> kafkaTemplate) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.preIndexationOrganizationTopic = preIndexationOrganizationTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void produce(final OrganizationUuidAwareActionQueueMessage message) {
        Assert.notNull(message, "The OrganizationUuidAwareActionQueueMessage should not be null");
        LOGGER.debug("Trying to produce organization uuid aware action message - {}", message);
        Try.of(() -> kafkaTemplate
                .send(
                        preIndexationOrganizationTopic,
                        message.getUuid(),
                        message
                ).get(5000L, TimeUnit.MILLISECONDS))
                .onFailure(ex -> LOGGER.error("Failed to send organization uuid aware action message - {} with exception - {}", message, ex))
                .getOrElseThrow(() -> {
                    throw new IllegalStateException(format("Failed to send organization uuid aware action message - %s", message));
                });
    }
}