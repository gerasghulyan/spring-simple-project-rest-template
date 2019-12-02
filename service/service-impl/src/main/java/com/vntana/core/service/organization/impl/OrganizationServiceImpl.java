package com.vntana.core.service.organization.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.persistence.organization.OrganizationRepository;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.organization.dto.CreateOrganizationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public OrganizationServiceImpl(final OrganizationRepository organizationRepository) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationRepository = organizationRepository;
    }

    @Transactional
    @Override
    public Organization create(final CreateOrganizationDto dto) {
        assertCreateOrganizationDto(dto);
        assertNotExistsForSlug(dto.getSlug());
        return organizationRepository.save(new Organization(dto.getName(), dto.getSlug()));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Organization> findBySlug(final String slug) {
        Assert.hasText(slug, "The organization slug should not be null");
        LOGGER.debug("Trying to find organization for slug - {}", slug);
        return organizationRepository.findBySlug(slug);
    }

    @Transactional(readOnly = true)
    public List<Organization> getAll() {
        LOGGER.debug("Trying to find all organization");
        return organizationRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Organization getByUuid(final String uuid) {
        return findByUuid(uuid).orElseThrow(() -> {
            LOGGER.error("Can not find organization for uuid - {}", uuid);
            return new IllegalStateException(format("Can not find organization for uuid - %s", uuid));
        });
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUuid(final String uuid) {
        Assert.hasText(uuid, "The organization uuid should not be null or empty");
        LOGGER.debug("Checking existence of organization having uuid - {}", uuid);
        return organizationRepository.existsByUuid(uuid);
    }

    private Optional<Organization> findByUuid(final String uuid) {
        Assert.hasText(uuid, "The organization uuid should not be null");
        LOGGER.debug("Trying to find organization with uuid - {}", uuid);
        return organizationRepository.findByUuid(uuid);
    }

    private void assertCreateOrganizationDto(final CreateOrganizationDto dto) {
        Assert.notNull(dto, "The CreateOrganizationDto should not be null");
        Assert.hasText(dto.getName(), "The name should contain text");
        Assert.hasText(dto.getSlug(), "The slug should contain text");
    }

    private void assertNotExistsForSlug(final String slug) {
        findBySlug(slug).ifPresent(it -> {
            LOGGER.error("Organization with slug - {} already exists", slug);
            throw new IllegalStateException(format("Organization with slug - %s already exists", slug));
        });
    }
}
