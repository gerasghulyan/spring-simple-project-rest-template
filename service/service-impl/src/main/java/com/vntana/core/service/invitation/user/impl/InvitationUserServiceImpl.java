package com.vntana.core.service.invitation.user.impl;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser;
import com.vntana.core.domain.invitation.user.InvitationOrganizationUser;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.persistence.invitation.user.InvitationOrganizationClientUserRepository;
import com.vntana.core.persistence.invitation.user.InvitationOrganizationUserRepository;
import com.vntana.core.service.client.OrganizationClientService;
import com.vntana.core.service.invitation.user.InvitationUserService;
import com.vntana.core.service.invitation.user.dto.*;
import com.vntana.core.service.invitation.user.exception.IncorrectUserInvitedRoleOnOrganizationException;
import com.vntana.core.service.invitation.user.exception.InvitationUserNotFoundForTokenException;
import com.vntana.core.service.invitation.user.exception.InvitationUserNotFoundForUuidException;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:11 PM
 */
@Service
public class InvitationUserServiceImpl implements InvitationUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationUserServiceImpl.class);
    private final InvitationOrganizationUserRepository invitationOrganizationUserRepository;
    private final InvitationOrganizationClientUserRepository invitationOrganizationClientUserRepository;
    private final UserService userService;
    private final OrganizationService organizationService;
    private final OrganizationClientService clientOrganizationService;

    public InvitationUserServiceImpl(
            final InvitationOrganizationUserRepository invitationOrganizationUserRepository,
            final InvitationOrganizationClientUserRepository invitationOrganizationClientUserRepository,
            final UserService userService,
            final OrganizationService organizationService,
            final OrganizationClientService clientOrganizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.invitationOrganizationUserRepository = invitationOrganizationUserRepository;
        this.invitationOrganizationClientUserRepository = invitationOrganizationClientUserRepository;
        this.userService = userService;
        this.organizationService = organizationService;
        this.clientOrganizationService = clientOrganizationService;
    }

    @Transactional
    @Override
    public InvitationOrganizationUser createInvitationForOrganization(final CreateInvitationForOrganizationUserDto dto) {
        Assert.notNull(dto, "The CreateInvitationToOrganizationUserDto should not be null");
        LOGGER.debug("Creating user invitation for dto - {}", dto);
        if (UserRole.ORGANIZATION_OWNER == dto.getUserRole()) {
            throw new IncorrectUserInvitedRoleOnOrganizationException(dto.getUserRole());
        }
        final User inviterUser = userService.getByUuid(dto.getInviterUserUuid());
        final Organization organization = organizationService.getByUuid(dto.getOrganizationUuid());
        final InvitationOrganizationUser invitationUser = invitationOrganizationUserRepository.save(new InvitationOrganizationUser(
                dto.getUserRole(),
                dto.getEmail(),
                InvitationStatus.INVITED,
                inviterUser,
                organization));
        LOGGER.debug("Successfully created user invitation for dto - {}", dto);
        return invitationUser;
    }

    @Transactional
    @Override
    public List<InvitationOrganizationClientUser> creteInvitationForClients(final CreateInvitationForClientsUserDto dto) {
        Assert.notNull(dto, "The CreateInvitationToClientUserDto should not be null");
        LOGGER.debug("Creating user invitation to client for dto - {}", dto);
        final User inviterUser = userService.getByUuid(dto.getInviterUserUuid());
        final List<InvitationOrganizationClientUser> result = dto.getUserRoles()
                .entrySet()
                .stream()
                .map(entry -> {
                    final String clientUuid = entry.getKey();
                    final UserRole role = entry.getValue();
                    final ClientOrganization client = clientOrganizationService.getByUuid(clientUuid);
                    return invitationOrganizationClientUserRepository.save(new InvitationOrganizationClientUser(
                            role,
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
    public InvitationOrganizationUser getByUuid(final String uuid) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        LOGGER.debug("Retrieving user invitation by uuid - {}", uuid);
        final InvitationOrganizationUser invitationUser = invitationOrganizationUserRepository.findByUuid(uuid)
                .orElseThrow(() -> new InvitationUserNotFoundForUuidException(uuid));
        LOGGER.debug("Successfully retrieved user invitation by uuid - {}", uuid);
        return invitationUser;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUuid(final String uuid) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        LOGGER.debug("Checking existence of user invitation by uuid - {}", uuid);
        final boolean existence = invitationOrganizationUserRepository.existsByUuid(uuid);
        LOGGER.debug("Successfully checked existence of user invitation by uuid - {}", uuid);
        return existence;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<InvitationOrganizationUser> getAllByOrganizationUuidAndStatus(final GetAllByOrganizationUuidAndStatusInvitationUsersDto dto) {
        Assert.notNull(dto, "The GetAllByOrganizationUuidAndStatusInvitationUsersDto should not be null");
        LOGGER.debug("Retrieving user invitations for dto - {}", dto);
        final Page<InvitationOrganizationUser> page = invitationOrganizationUserRepository.findAllByOrganizationUuidAndStatus(
                dto.getOrganizationUuid(),
                dto.getStatus(),
                PageRequest.of(dto.getPage(), dto.getSize())
        );
        LOGGER.debug("Successfully retrieved user invitations for dto - {}", dto);
        return page;
    }

    @Transactional
    @Override
    public InvitationOrganizationUser updateStatus(final UpdateInvitationUserStatusDto dto) {
        Assert.notNull(dto, "The UpdateInvitationUserStatusDto should not be null");
        LOGGER.debug("Update user invitations for dto - {}", dto);
        final InvitationOrganizationUser invitationUser = getByUuid(dto.getUuid());
        invitationUser.setStatus(dto.getStatus());
        final InvitationOrganizationUser updatedInvitationUser = invitationOrganizationUserRepository.save(invitationUser);
        LOGGER.debug("Successfully updated user invitations for dto - {}", dto);
        return updatedInvitationUser;
    }

    @Transactional(readOnly = true)
    @Override
    public List<InvitationOrganizationUser> getAllByInviterUserUuid(final String userUuid) {
        Assert.hasText(userUuid, "The userUuid should not be null or empty");
        LOGGER.debug("Retrieving all user invitations by inviter user uuid - {}", userUuid);
        final List<InvitationOrganizationUser> invitations = invitationOrganizationUserRepository.findByInviterUserUuid(userUuid);
        LOGGER.debug("Successfully retrieved all user invitations by inviter user uuid - {}", userUuid);
        return invitations;
    }

    @Transactional(readOnly = true)
    @Override
    public List<InvitationOrganizationUser> getAllByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(final GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto dto) {
        Assert.notNull(dto, "The GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto should not be null");
        LOGGER.debug("Retrieving all user invitations by dto - {}", dto);
        final List<InvitationOrganizationUser> invitationUsers = invitationOrganizationUserRepository.findByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(
                dto.getEmail(),
                dto.getOrganizationUuid(),
                dto.getStatus()
        );
        LOGGER.debug("Successfully retrieved all user invitations by dto - {}", dto);
        return invitationUsers;
    }

    @Override
    public InvitationOrganizationUser getByToken(final String token) {
        Assert.hasText(token, "The token should not be null or empty");
        LOGGER.debug("Retrieving user invitation by user invitation token");
        final InvitationOrganizationUser invitationUser = invitationOrganizationUserRepository.findByTokenInvitationUser(token)
                .orElseThrow(() -> new InvitationUserNotFoundForTokenException(token));
        LOGGER.debug("Successfully retrieved user invitations by user invitation token");
        return invitationUser;
    }

    @Override
    public boolean existsByToken(final String token) {
        Assert.hasText(token, "The token should not be null or empty");
        LOGGER.debug("Checking user invitation existence by user invitation token");
        final boolean exists = invitationOrganizationUserRepository.findByTokenInvitationUser(token).isPresent();
        LOGGER.debug("Successfully checked user invitation existence by user invitation token");
        return exists;
    }
}