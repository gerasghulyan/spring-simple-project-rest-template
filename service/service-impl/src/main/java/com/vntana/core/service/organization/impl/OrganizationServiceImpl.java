package com.vntana.core.service.organization.impl;

import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.organization.status.OrganizationStatus;
import com.vntana.core.persistence.organization.OrganizationRepository;
import com.vntana.core.service.common.component.SlugValidationComponent;
import com.vntana.core.service.invitation.organization.InvitationOrganizationService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.organization.dto.*;
import com.vntana.core.service.organization.exception.OrganizationNotFoundForUuidException;
import com.vntana.core.service.organization.exception.OrganizationOwnerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:16 PM
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;
    private final SlugValidationComponent slugValidationComponent;
    private final InvitationOrganizationService invitationOrganizationService;

    public OrganizationServiceImpl(
            final OrganizationRepository organizationRepository,
            final SlugValidationComponent slugValidationComponent,
            final InvitationOrganizationService invitationOrganizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.slugValidationComponent = slugValidationComponent;
        this.organizationRepository = organizationRepository;
        this.invitationOrganizationService = invitationOrganizationService;
    }

    @Transactional
    @Override
    public Organization create(final CreateOrganizationDto dto) {
        assertCreateOrganizationDto(dto);
        assertSlug(dto.getSlug());
        return organizationRepository.save(new Organization(
                dto.getName(),
                dto.getSlug(),
                dto.getImageBlobId(),
                OrganizationStatus.ACTIVE
        ));
    }

    @Transactional
    @Override
    public Organization createWithInvitation(final CreateOrganizationFromInvitationDto dto) {
        Assert.notNull(dto, "The CreateOrganizationFromInvitationDto should not be null");
        assertSlug(dto.getSlug());
        final InvitationOrganization invitationOrganization = invitationOrganizationService.getByUuid(dto.getOrganizationInvitationUuid());
        return organizationRepository.save(new Organization(
                dto.getName(),
                dto.getSlug(),
                invitationOrganization
        ));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Organization> findBySlug(final String slug) {
        Assert.hasText(slug, "The organization slug should not be null");
        LOGGER.debug("Trying to find organization for slug - {}", slug);
        return organizationRepository.findBySlug(slug);
    }

    @Transactional(readOnly = true)
    public Page<Organization> getAll(final GetAllOrganizationDto dto) {
        Assert.notNull(dto, "The GetAllOrganizationDto should not be null");
        LOGGER.debug("Trying to find all organization");
        return organizationRepository.findAll(PageRequest.of(dto.getPage(), dto.getSize()));
    }

    @Transactional(readOnly = true)
    @Override
    public Organization getByUuid(final String uuid) {
        return findByUuid(uuid).orElseThrow(() -> {
            LOGGER.error("Can not find organization for uuid - {}", uuid);
            return new OrganizationNotFoundForUuidException(uuid);
        });
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUuid(final String uuid) {
        Assert.hasText(uuid, "The organization uuid should not be null or empty");
        LOGGER.debug("Checking existence of organization having uuid - {}", uuid);
        return organizationRepository.existsByUuid(uuid);
    }

    @Transactional
    @Override
    public Organization update(final UpdateOrganizationDto dto) {
        Assert.notNull(dto, "The UpdateOrganizationDto should not be null");
        LOGGER.debug("Updating organization for dto - {}", dto);
        final Organization organization = getByUuid(dto.getUuid());
        organization.setImageBlobId(dto.getImageBlobId());
        organization.setName(dto.getName());
        dto.getStatus().ifPresent(organization::setStatus);
        organizationRepository.save(organization);
        LOGGER.debug("Successfully updating organization for dto - {}", dto);
        return organization;
    }

    @Transactional(readOnly = true)
    @Override
    public String getOrganizationOwnerEmail(final String organizationUuid) {
        Assert.hasText(organizationUuid, "The organizationUuid should not be null or empty");
        LOGGER.debug("Retrieving organization owner email having uuid - {}", organizationUuid);
        final Optional<String> emailOptional = organizationRepository.findOrganizationOwnerEmail(organizationUuid);
        LOGGER.debug("Successfully processed organization owner email retrieval having uuid - {}", organizationUuid);
        return emailOptional.orElseThrow(() -> new OrganizationOwnerNotFoundException(organizationUuid));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Organization> getUserOrganizationsByUserUuidAndRole(final GetUserOrganizationsByUserUuidAndRoleDto dto) {
        Assert.notNull(dto, "The 'GetUserOrganizationsByUserUuidAndRoleDto' should not be null");
        LOGGER.debug("Retrieving organizations of user having uuid - {} and role - {}", dto.getUserUuid(), dto.getUserRole());
        final List<Organization> organizations = organizationRepository.findUserOrganizationsByUserUuidAndRole(dto.getUserUuid(), dto.getUserRole());
        LOGGER.debug("Successfully processed retrieving organizations of user having uuid - {} and role - {}", dto.getUserUuid(), dto.getUserRole());
        return organizations;
    }

    @Override
    public Long count() {
        LOGGER.debug("Retrieving organizations records count");
        return organizationRepository.count();
    }

    private Optional<Organization> findByUuid(final String uuid) {
        Assert.hasText(uuid, "The organization uuid should not be null");
        LOGGER.debug("Trying to find organization with uuid - {}", uuid);
        return organizationRepository.findByUuid(uuid);
    }

    private void assertCreateOrganizationDto(final CreateOrganizationDto dto) {
        Assert.notNull(dto, "The CreateOrganizationDto should not be null");
    }

    private void assertSlug(final String slug) {
        Assert.isTrue(slugValidationComponent.validate(slug), "The slug must be valid");
        findBySlug(slug).ifPresent(it -> {
            LOGGER.error("Organization with slug - {} already exists", slug);
            throw new IllegalStateException(format("Organization with slug - %s already exists", slug));
        });
    }
}
