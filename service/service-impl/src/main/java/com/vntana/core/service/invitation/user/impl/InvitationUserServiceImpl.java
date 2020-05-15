package com.vntana.core.service.invitation.user.impl;

import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.invitation.user.InvitationUser;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.persistence.invitation.user.InvitationUserRepository;
import com.vntana.core.service.invitation.user.InvitationUserService;
import com.vntana.core.service.invitation.user.dto.CreateInvitationUserDto;
import com.vntana.core.service.invitation.user.dto.GetAllByStatusInvitationUsersDto;
import com.vntana.core.service.invitation.user.dto.GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto;
import com.vntana.core.service.invitation.user.dto.UpdateInvitationUserStatusDto;
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

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:11 PM
 */
@Service
public class InvitationUserServiceImpl implements InvitationUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationUserServiceImpl.class);
    private final InvitationUserRepository invitationUserRepository;
    private final UserService userService;
    private final OrganizationService organizationService;

    public InvitationUserServiceImpl(final InvitationUserRepository invitationUserRepository, final UserService userService, final OrganizationService organizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.invitationUserRepository = invitationUserRepository;
        this.userService = userService;
        this.organizationService = organizationService;
    }

    @Transactional
    @Override
    public InvitationUser create(final CreateInvitationUserDto dto) {
        Assert.notNull(dto, "The CreateInvitationUserDto should not be null");
        LOGGER.debug("Creating user invitation for dto - {}", dto);
        if (UserRole.ORGANIZATION_OWNER == dto.getUserRole()) {
            throw new IncorrectUserInvitedRoleOnOrganizationException(dto.getUserRole());
        }
        final User inviterUser = userService.getByUuid(dto.getInviterUserUuid());
        final Organization organization = organizationService.getByUuid(dto.getOrganizationUuid());
        final InvitationUser invitationUser = invitationUserRepository.save(new InvitationUser(
                dto.getUserRole(),
                dto.getEmail(),
                InvitationStatus.INVITED,
                inviterUser,
                organization));
        LOGGER.debug("Successfully created user invitation for dto - {}", dto);
        return invitationUser;
    }

    @Transactional(readOnly = true)
    @Override
    public InvitationUser getByUuid(final String uuid) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        LOGGER.debug("Retrieving user invitation by uuid - {}", uuid);
        final InvitationUser invitationUser = invitationUserRepository.findByUuid(uuid)
                .orElseThrow(() -> new InvitationUserNotFoundForUuidException(uuid));
        LOGGER.debug("Successfully retrieved user invitation by uuid - {}", uuid);
        return invitationUser;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUuid(final String uuid) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        LOGGER.debug("Checking existence of user invitation by uuid - {}", uuid);
        final boolean existence = invitationUserRepository.existsByUuid(uuid);
        LOGGER.debug("Successfully checked existence of user invitation by uuid - {}", uuid);
        return existence;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<InvitationUser> getAllByStatus(final GetAllByStatusInvitationUsersDto dto) {
        Assert.notNull(dto, "The GetAllInvitationUsersDto should not be null");
        LOGGER.debug("Retrieving user invitations for dto - {}", dto);
        final Page<InvitationUser> page = invitationUserRepository.findAllByStatus(dto.getStatus(), PageRequest.of(dto.getPage(), dto.getSize()));
        LOGGER.debug("Successfully retrieved user invitations for dto - {}", dto);
        return page;
    }

    @Transactional
    @Override
    public InvitationUser updateStatus(final UpdateInvitationUserStatusDto dto) {
        Assert.notNull(dto, "The UpdateInvitationUserStatusDto should not be null");
        LOGGER.debug("Update user invitations for dto - {}", dto);
        final InvitationUser invitationUser = getByUuid(dto.getUuid());
        invitationUser.setStatus(dto.getStatus());
        final InvitationUser updatedInvitationUser = invitationUserRepository.save(invitationUser);
        LOGGER.debug("Successfully updated user invitations for dto - {}", dto);
        return updatedInvitationUser;
    }

    @Transactional(readOnly = true)
    @Override
    public List<InvitationUser> getAllByInviterUserUuid(final String userUuid) {
        Assert.hasText(userUuid, "The userUuid should not be null or empty");
        LOGGER.debug("Retrieving all user invitations by inviter user uuid - {}", userUuid);
        final List<InvitationUser> invitations = invitationUserRepository.findByInviterUserUuid(userUuid);
        LOGGER.debug("Successfully retrieved all user invitations by inviter user uuid - {}", userUuid);
        return invitations;
    }

    @Transactional(readOnly = true)
    @Override
    public List<InvitationUser> getAllByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(final GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto dto) {
        Assert.notNull(dto, "The GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto should not be null");
        LOGGER.debug("Retrieving all user invitations by dto - {}", dto);
        final List<InvitationUser> invitationUsers = invitationUserRepository.findByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(
                dto.getEmail(),
                dto.getOrganizationUuid(),
                dto.getStatus()
        );
        LOGGER.debug("Successfully retrieved all user invitations by dto - {}", dto);
        return invitationUsers;
    }

    @Override
    public InvitationUser getByToken(final String token) {
        Assert.hasText(token, "The token should not be null or empty");
        LOGGER.debug("Retrieving user invitation by user invitation token");
        final InvitationUser invitationUser = invitationUserRepository.findByTokenInvitationUser(token)
                .orElseThrow(() -> new InvitationUserNotFoundForTokenException(token));
        LOGGER.debug("Successfully retrieved user invitations by user invitation token");
        return invitationUser;
    }

    @Override
    public boolean existsByToken(final String token) {
        Assert.hasText(token, "The token should not be null or empty");
        LOGGER.debug("Checking user invitation existence by user invitation token");
        final boolean exists = invitationUserRepository.findByTokenInvitationUser(token).isPresent();
        LOGGER.debug("Successfully checked user invitation existence by user invitation token");
        return exists;
    }
}