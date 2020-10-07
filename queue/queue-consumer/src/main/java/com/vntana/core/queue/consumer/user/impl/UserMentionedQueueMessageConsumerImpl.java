package com.vntana.core.queue.consumer.user.impl;

import com.vntana.asset.queue.message.user.UserMentionedQueueMessage;
import com.vntana.core.queue.consumer.user.UserMentionedQueueMessageConsumer;
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

        LOGGER.debug("Successfully processed consumption of user mentioned queue message - {}", message);
    }
}
