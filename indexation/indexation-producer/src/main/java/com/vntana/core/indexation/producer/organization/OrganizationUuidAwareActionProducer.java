package com.vntana.core.indexation.producer.organization;

import com.vntana.commons.queue.producer.QueueUuidAwareProducer;
import com.vntana.core.indexation.consumer.message.organization.OrganizationUuidAwareActionQueueMessage;

/**
 * Created by Manuk Gharslyan.
 * Date: 3/25/2020
 * Time: 12:26 PM
 */
public interface OrganizationUuidAwareActionProducer extends QueueUuidAwareProducer<OrganizationUuidAwareActionQueueMessage> {
}