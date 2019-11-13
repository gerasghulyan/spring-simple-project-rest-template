package com.vntana.core.service.client.impl;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.persistence.client.ClientOrganizationRepository;
import com.vntana.core.service.client.ClientOrganizationService;
import com.vntana.core.service.client.dto.CreateClientOrganizationDto;
import com.vntana.core.service.organization.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

import static java.lang.String.format;

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:16 PM
 */
@Service
public class ClientOrganizationServiceImpl implements ClientOrganizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientOrganizationServiceImpl.class);

    private final OrganizationService organizationService;

    private final ClientOrganizationRepository clientOrganizationRepository;

    public ClientOrganizationServiceImpl(final OrganizationService organizationService, final ClientOrganizationRepository clientOrganizationRepository) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationService = organizationService;
        this.clientOrganizationRepository = clientOrganizationRepository;
    }

    @Transactional
    @Override
    public ClientOrganization create(final CreateClientOrganizationDto dto) {
        assertCreateClientOrganizationDto(dto);
        assertNotExistsForSlugAndOrganization(dto.getSlug(), dto.getOrganizationUuid());
        final Organization organization = organizationService.getByUuid(dto.getOrganizationUuid());
        return clientOrganizationRepository.save(new ClientOrganization(dto.getName(), dto.getSlug(), organization));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ClientOrganization> findByUuid(final String uuid) {
        Assert.hasText(uuid, "The client organization uuid should not be null");
        LOGGER.debug("Trying to find client organization with uuid - {}", uuid);
        return clientOrganizationRepository.findByUuid(uuid);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ClientOrganization> findBySlugAndOrganization(final String slug, final String organizationUuid) {
        Assert.hasText(slug, "The client organization slug should not be null");
        Assert.hasText(organizationUuid, "The client organization uuid should not be null");
        LOGGER.debug("Trying to find client organization for slug - {}", slug);
        return clientOrganizationRepository.findBySlugAndOrganizationUuid(slug, organizationUuid);
    }

    private void assertCreateClientOrganizationDto(final CreateClientOrganizationDto dto) {
        Assert.notNull(dto, "The CreateClientOrganizationDto should not be null");
        Assert.hasText(dto.getName(), "The client name should contain text");
        Assert.hasText(dto.getSlug(), "The client slug should contain text");
    }

    private void assertNotExistsForSlugAndOrganization(final String slug, final String organizationUuid) {
        findBySlugAndOrganization(slug, organizationUuid).ifPresent(it -> {
            LOGGER.error("Client with slug - {} already exists", slug);
            throw new IllegalStateException(format("Client with slug - %s already exists", slug));
        });
    }
}
