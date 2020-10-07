package com.vntana.core.queue.consumer.user;

import com.vntana.asset.queue.message.user.UserMentionedQueueMessage;
import com.vntana.commons.queue.consumer.QueueConsumer;

/**
 * Created by Arman Gevorgyan.
 * Date: 10/7/20
 * Time: 2:34 PM
 */
public interface UserMentionedQueueMessageConsumer extends QueueConsumer<UserMentionedQueueMessage> {
}
