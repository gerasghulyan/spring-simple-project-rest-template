package com.vntana.core.service.invitation.user.impl;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.persistence.invitation.user.InvitationOrganizationClientUserRepository;
import com.vntana.core.service.client.OrganizationClientService;
import com.vntana.core.service.invitation.user.InvitationUserToClientService;
import com.vntana.core.service.invitation.user.dto.CreateInvitationForClientsUserDto;
import com.vntana.core.service.invitation.user.dto.GetAllByOrganizationUuidAndStatusInvitationUsersDto;
import com.vntana.core.service.invitation.user.dto.UpdateInvitationUserStatusDto;
import com.vntana.core.service.invitation.user.exception.InvitationUserNotFoundForTokenException;
import com.vntana.core.service.invitation.user.exception.InvitationUserNotFoundForUuidException;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Diana Gevorgyan
 * Date: 11/19/20
 * Time: 12:32 PM
 */
@Service
public class InvitationUserToClientServiceImpl implements InvitationUserToClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationUserToClientServiceImpl.class);

    private final UserService userService;
    private final OrganizationClientService clientOrganizationService;
    private final InvitationOrganizationClientUserRepository invitationOrganizationClientUserRepository;
    private final OrganizationService organizationService;

    public InvitationUserToClientServiceImpl(
            final UserService userService,
            final OrganizationClientService clientOrganizationService,
            final InvitationOrganizationClientUserRepository invitationOrganizationClientUserRepository,
            final OrganizationService organizationService) {
        this.userService = userService;
        this.clientOrganizationService = clientOrganizationService;
        this.invitationOrganizationClientUserRepository = invitationOrganizationClientUserRepository;
        this.organizationService = organizationService;
    }

    @Transactional
    @Override
    public List<InvitationOrganizationClientUser> create(final CreateInvitationForClientsUserDto dto) {
        Assert.notNull(dto, "The CreateInvitationToClientUserDto should not be null");
        LOGGER.debug("Creating user invitation to client for dto - {}", dto);
        final User inviterUser = userService.getByUuid(dto.getInviterUserUuid());
        final List<InvitationOrganizationClientUser> result = dto.getInvitations()
                .stream()
                .map(entry -> {
                    final ClientOrganization client = clientOrganizationService.getByUuid(entry.getClientUuid());
                    return invitationOrganizationClientUserRepository.save(new InvitationOrganizationClientUser(
                            entry.getRole(),
                            dto.getEmail(),
                            InvitationStatus.INVITED,
                            inviterUser,
                            client));
                }).collect(Collectors.toList());
        LOGGER.debug("Successfully created user invitation for clients for dto - {}", dto);
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public InvitationOrganizationClientUser getByUuid(final String uuid) {
        assertUuid(uuid);
        LOGGER.debug("Retrieving user invitation by uuid - {}", uuid);
        final InvitationOrganizationClientUser invitationUser = invitationOrganizationClientUserRepository.findByUuid(uuid)
                .orElseThrow(() -> new InvitationUserNotFoundForUuidException(uuid));
        LOGGER.debug("Successfully retrieved user invitation by uuid - {}", uuid);
        return invitationUser;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUuid(final String uuid) {
        assertUuid(uuid);
        LOGGER.debug("Checking existence of user invitation by uuid - {}", uuid);
        final boolean existence = invitationOrganizationClientUserRepository.existsByUuid(uuid);
        LOGGER.debug("Successfully checked existence of user invitation by uuid - {}", uuid);
        return existence;
    }

    @Transactional(readOnly = true)
    @Override
    public List<InvitationOrganizationClientUser> getAllByOrganizationUuidAndStatus(final GetAllByOrganizationUuidAndStatusInvitationUsersDto dto) {
        Assert.notNull(dto, "The GetAllByOrganizationUuidAndStatusInvitationUsersDto should not be null");
        LOGGER.debug("Retrieving user invitations for clients for dto - {}", dto);
        final Organization organization = organizationService.getByUuid(dto.getOrganizationUuid());
        final List<ClientOrganization> clientOrganizations = organization.getClientOrganizations();
        return clientOrganizations
                .stream()
                .map(clientOrganization ->
                        invitationOrganizationClientUserRepository
                                .findAllByClientOrganizationUuidAndStatus(
                                        clientOrganization.getUuid(),
                                        dto.getStatus(),
                                        PageRequest.of(dto.getPage(), dto.getSize())).getContent()
                )
                .flatMap(List::stream)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    @Transactional
    @Override
    public InvitationOrganizationClientUser updateStatus(final UpdateInvitationUserStatusDto dto) {
        Assert.notNull(dto, "The UpdateInvitationUserStatusDto should not be null");
        LOGGER.debug("Update user invitations for dto - {}", dto);
        final InvitationOrganizationClientUser userInvitation = getByUuid(dto.getUuid());
        userInvitation.setStatus(dto.getStatus());
        final InvitationOrganizationClientUser updatedUserInvitation = invitationOrganizationClientUserRepository.save(userInvitation);
        LOGGER.debug("Successfully updated user invitations for dto - {}", dto);
        return updatedUserInvitation;
    }

    @Transactional(readOnly = true)
    @Override
    public InvitationOrganizationClientUser getByToken(final String token) {
        assertToken(token);
        LOGGER.debug("Retrieving user invitation by user invitation token");
        final InvitationOrganizationClientUser userInvitation = invitationOrganizationClientUserRepository.findUserInvitationByToken(token)
                .orElseThrow(() -> new InvitationUserNotFoundForTokenException(token));
        LOGGER.debug("Successfully retrieved user invitations by user invitation token");
        return userInvitation;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByToken(final String token) {
        assertToken(token);
        LOGGER.debug("Checking user invitation existence by user invitation token");
        final boolean exists = invitationOrganizationClientUserRepository.findUserInvitationByToken(token).isPresent();
        LOGGER.debug("Successfully checked user invitation existence by user invitation token");
        return exists;
    }

    private void assertUuid(final String uuid) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
    }

    private void assertToken(final String token) {
        Assert.hasText(token, "The token should not be null or empty");
    }
}
