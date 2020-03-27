package com.vntana.core.indexation.producer.invitation.organization;

import com.vntana.commons.queue.producer.QueueUuidAwareProducer;
import com.vntana.core.indexation.consumer.message.invitation.organization.InvitationOrganizationUuidAwareActionQueueMessage;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 5:42 PM
 */
public interface InvitationOrganizationUuidAwareActionProducer extends QueueUuidAwareProducer<InvitationOrganizationUuidAwareActionQueueMessage> {
}
