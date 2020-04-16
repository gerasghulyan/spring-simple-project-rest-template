package com.vntana.core.rest.facade.indexation.invitation.organization.component.impl;

import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import com.vntana.core.rest.facade.indexation.invitation.organization.component.InvitationOrganizationIndexationComponent;
import com.vntana.core.service.invitation.organization.InvitationOrganizationService;
import com.vntana.core.service.invitation.organization.mediator.InvitationOrganizationUuidAwareLifecycleMediator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Created by Arman Gevorgyan.
 * Date: 4/2/20
 * Time: 11:23 AM
 */
@Component
public class InvitationOrganizationIndexationComponentImpl implements InvitationOrganizationIndexationComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationOrganizationIndexationComponentImpl.class);

    private final InvitationOrganizationService invitationOrganizationService;
    private final InvitationOrganizationUuidAwareLifecycleMediator organizationInvitationUuidAwareLifecycleMediator;

    public InvitationOrganizationIndexationComponentImpl(final InvitationOrganizationService invitationOrganizationService, final InvitationOrganizationUuidAwareLifecycleMediator organizationInvitationUuidAwareLifecycleMediator) {
        this.invitationOrganizationService = invitationOrganizationService;
        this.organizationInvitationUuidAwareLifecycleMediator = organizationInvitationUuidAwareLifecycleMediator;
    }

    @Override
    public void indexByOne(final String uuid) {
        Assert.hasText(uuid, "The organization invitation uuid should not be null or empty");
        LOGGER.debug("Processing the indexation of an organization invitation having uuid - {}", uuid);
        final InvitationOrganization invitation = invitationOrganizationService.getByUuid(uuid);
        if (Objects.nonNull(invitation.getRemoved())) {
            organizationInvitationUuidAwareLifecycleMediator.onDeleted(uuid);
        } else {
            organizationInvitationUuidAwareLifecycleMediator.onUpdated(uuid);
        }
        LOGGER.debug("Successfully processed the indexation of an organization invitation having uuid -{}", uuid);
    }
}
