package com.project.sample.service.organization.impl;

import com.project.sample.commons.persistence.domain.organization.Organization;
import com.project.sample.persistence.organization.OrganizationRepository;
import com.project.sample.service.organization.OrganizationService;
import com.project.sample.service.organization.dto.CreateOrganizationDto;
import com.project.sample.service.organization.dto.GetAllOrganizationDto;
import com.project.sample.service.organization.dto.UpdateOrganizationDto;
import com.project.sample.service.organization.exception.OrganizationNotFoundForUuidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created by Geras Ghulyan.
 * Date: 10/8/19
 * Time: 5:16 PM
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(final OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Transactional
    @Override
    public Organization create(final CreateOrganizationDto dto) {
        assertCreateOrganizationDto(dto);
        return organizationRepository.save(new Organization(
                dto.getName()
        ));
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

    @Transactional
    @Override
    public Organization update(final UpdateOrganizationDto dto) {
        Assert.notNull(dto, "The UpdateOrganizationDto should not be null");
        LOGGER.debug("Updating organization for dto - {}", dto);
        final Organization organization = getByUuid(dto.getUuid());
        organization.setName(dto.getName());
        dto.getStatus().ifPresent(organization::setStatus);
        organizationRepository.save(organization);
        LOGGER.debug("Successfully updating organization for dto - {}", dto);
        return organization;
    }

    @Override
    public Optional<Organization> findByUuid(final String uuid) {
        Assert.hasText(uuid, "The organization uuid should not be null");
        LOGGER.debug("Trying to find organization with uuid - {}", uuid);
        return organizationRepository.findByUuid(uuid);
    }

    private void assertCreateOrganizationDto(final CreateOrganizationDto dto) {
        Assert.notNull(dto, "The CreateOrganizationDto should not be null");
    }
}
