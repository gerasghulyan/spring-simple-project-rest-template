package com.vntana.core.service.invitation.organization.impl;

import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import com.vntana.core.persistence.invitation.organization.InvitationOrganizationRepository;
import com.vntana.core.service.invitation.organization.InvitationOrganizationService;
import com.vntana.core.service.invitation.organization.dto.AcceptInvitationOrganizationDto;
import com.vntana.core.service.invitation.organization.dto.CreateInvitationOrganizationDto;
import com.vntana.core.service.invitation.organization.dto.GetAllInvitationOrganizationsDto;
import com.vntana.core.service.invitation.organization.dto.UpdateInvitationOrganizationStatusDto;
import com.vntana.core.service.invitation.organization.exception.InvitationOrganizationNotFoundForUuidException;
import com.vntana.core.service.organization.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 3:15 PM
 */
@Service
public class InvitationOrganizationServiceImpl implements InvitationOrganizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationOrganizationServiceImpl.class);

    private final InvitationOrganizationRepository invitationOrganizationRepository;
    private final OrganizationService organizationService;

    public InvitationOrganizationServiceImpl(final InvitationOrganizationRepository invitationOrganizationRepository, final OrganizationService organizationService) {
        this.invitationOrganizationRepository = invitationOrganizationRepository;
        this.organizationService = organizationService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public InvitationOrganization create(final CreateInvitationOrganizationDto dto) {
        LOGGER.debug("Creating InvitationOrganization for dto - {}", dto);
        Assert.notNull(dto, "The CreateInvitationOrganizationDto should not be null");
        final InvitationOrganization invitationOrganization = new InvitationOrganization(
                dto.getOwnerFullName(),
                dto.getEmail(),
                dto.getOrganizationName(),
                dto.getSlug(),
                dto.getCustomerSubscriptionDefinitionUuid(),
                InvitationStatus.INVITED
        );
        final InvitationOrganization saved = invitationOrganizationRepository.save(invitationOrganization);
        LOGGER.debug("Successfully created InvitationOrganization for dto - {}", dto);
        return saved;
    }

    @Override
    public InvitationOrganization getByUuid(final String uuid) {
        LOGGER.debug("Retrieving InvitationOrganization having uuid - {}", uuid);
        Assert.hasText(uuid, "The InvitationOrganization uuid should not be null or empty");
        final InvitationOrganization invitationOrganization = invitationOrganizationRepository.findByUuid(uuid)
                .orElseThrow(() -> new InvitationOrganizationNotFoundForUuidException(uuid));
        LOGGER.debug("Successfully retrieved InvitationOrganization having uuid - {}", uuid);
        return invitationOrganization;
    }

    @Override
    public boolean existsByUuid(final String uuid) {
        LOGGER.debug("Checking existence of InvitationOrganization having uuid - {}", uuid);
        Assert.hasText(uuid, "The InvitationOrganization uuid should not be null or empty");
        final boolean existence = invitationOrganizationRepository.existsByUuid(uuid);
        LOGGER.debug("Successfully checked existence of InvitationOrganization having uuid - {}", uuid);
        return existence;
    }

    @Override
    public Page<InvitationOrganization> getAll(final GetAllInvitationOrganizationsDto dto) {
        LOGGER.debug("Retrieving organization invitations for dto - {}", dto);
        Assert.notNull(dto, "The GetAllInvitationOrganizationsDto should not be null");
        final Page<InvitationOrganization> page = invitationOrganizationRepository.findAll(PageRequest.of(dto.getPage(), dto.getSize()));
        LOGGER.debug("Successfully retrieved organization invitations for dto - {}", dto);
        return page;
    }

    @Transactional
    @Override
    public InvitationOrganization updateStatus(final UpdateInvitationOrganizationStatusDto dto) {
        LOGGER.debug("Update organization invitations for dto - {}", dto);
        Assert.notNull(dto, "The UpdateInvitationOrganizationStatusDto should not be null");
        final InvitationOrganization invitationOrganization = getByUuid(dto.getUuid());
        invitationOrganization.setStatus(dto.getStatus());
        final InvitationOrganization updatedInvitationOrganization = invitationOrganizationRepository.save(invitationOrganization);
        LOGGER.debug("Successfully updated organization invitations for dto - {}", dto);
        return updatedInvitationOrganization;
    }

    @Transactional
    @Override
    public InvitationOrganization accept(final AcceptInvitationOrganizationDto dto) {
        LOGGER.debug("Accept organization invitations for dto - {}", dto);
        Assert.notNull(dto, "The AcceptInvitationOrganizationDto should not be null");
        final InvitationOrganization invitationOrganization = updateStatus(
                new UpdateInvitationOrganizationStatusDto(dto.getUuid(), InvitationStatus.ACCEPTED)
        );
        invitationOrganization.setOrganization(organizationService.getByUuid(dto.getOrganizationUuid()));
        final InvitationOrganization updatedInvitationOrganization = invitationOrganizationRepository.save(invitationOrganization);
        LOGGER.debug("Successfully updated organization invitations for dto - {}", dto);
        return updatedInvitationOrganization;
    }

    @Override
    public Optional<InvitationOrganization> findByOrganizationUuid(final String organizationUuid) {
        LOGGER.debug("Retrieving InvitationOrganization having organizationUuid - {}", organizationUuid);
        Assert.hasText(organizationUuid, "The InvitationOrganization organizationUuid should not be null or empty");
        return invitationOrganizationRepository.findByOrganization_Uuid(organizationUuid);
    }
}
