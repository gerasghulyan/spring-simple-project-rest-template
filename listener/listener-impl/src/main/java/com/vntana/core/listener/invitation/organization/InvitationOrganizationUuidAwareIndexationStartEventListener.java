package com.vntana.core.listener.invitation.organization;

import com.vntana.commons.indexation.listener.EntityUuidAwareLifecycleListener;
import com.vntana.commons.indexation.payload.EntityLifecycle;
import com.vntana.commons.queue.model.MessageActionType;
import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import com.vntana.core.indexation.consumer.message.invitation.organization.InvitationOrganizationUuidAwareActionQueueMessage;
import com.vntana.core.indexation.producer.invitation.organization.InvitationOrganizationUuidAwareActionProducer;
import com.vntana.core.service.invitation.organization.InvitationOrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 5:30 PM
 */
@Component
@Lazy(false)
public class InvitationOrganizationUuidAwareIndexationStartEventListener implements EntityUuidAwareLifecycleListener<InvitationOrganizationUuidAwareLifecyclePayload> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationOrganizationUuidAwareIndexationStartEventListener.class);

    private final InvitationOrganizationService invitationOrganizationService;
    private final InvitationOrganizationUuidAwareActionProducer invitationOrganizationUuidAwareActionProducer;

    public InvitationOrganizationUuidAwareIndexationStartEventListener(final InvitationOrganizationService invitationOrganizationService,
                                                                       final InvitationOrganizationUuidAwareActionProducer invitationOrganizationUuidAwareActionProducer) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.invitationOrganizationService = invitationOrganizationService;
        this.invitationOrganizationUuidAwareActionProducer = invitationOrganizationUuidAwareActionProducer;
    }

    @EventListener
    @Override
    public void handleEvent(final InvitationOrganizationUuidAwareLifecyclePayload payload) {
        Assert.notNull(payload, "The InvitationOrganizationUuidAwareLifecyclePayload should not be null");
        LOGGER.debug("Trying to produce InvitationOrganizationUuidAwareActionQueueMessage to organization pre-indexation topic for uuid - {}", payload.entityUuid());
        if (isUpdateEventWhenEntityRemoved(payload)) {
            LOGGER.debug("Exiting from indexation. Organization with uuid - {} is deleted. Update is not supported", payload.entityUuid());
            return;
        }
        invitationOrganizationUuidAwareActionProducer.produce(
                new InvitationOrganizationUuidAwareActionQueueMessage(MessageActionType.valueOf(payload.lifecycle().name()), payload.entityUuid())
        );
        LOGGER.debug("Successfully produced OrganizationUuidAwareActionQueueMessage to organization pre-indexation topic for uuid - {}", payload.entityUuid());

    }

    @Override
    public boolean isUpdateEventWhenEntityRemoved(final InvitationOrganizationUuidAwareLifecyclePayload payload) {
        final InvitationOrganization invitationOrganization = invitationOrganizationService.getByUuid(payload.entityUuid());
        return Objects.nonNull(invitationOrganization.getRemoved()) && payload.lifecycle() == EntityLifecycle.UPDATED;
    }

}
