package com.vntana.core.service.whitelist.impl;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.whitelist.WhitelistIp;
import com.vntana.core.persistence.whitelist.WhitelistIpRepository;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.whitelist.WhitelistIpService;
import com.vntana.core.service.whitelist.dto.CreateWhitelistIpDto;
import com.vntana.core.service.whitelist.dto.UpdateWhitelistIpDto;
import com.vntana.core.service.whitelist.exception.WhitelistIpNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 1:35 PM
 */
@Service
public class WhitelistIpServiceImpl implements WhitelistIpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhitelistIpServiceImpl.class);

    private final OrganizationService organizationService;
    private final WhitelistIpRepository whitelistIpRepository;

    public WhitelistIpServiceImpl(final OrganizationService organizationService, final WhitelistIpRepository whitelistIpRepository) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationService = organizationService;
        this.whitelistIpRepository = whitelistIpRepository;
    }

    @Transactional
    @Override
    public WhitelistIp create(final CreateWhitelistIpDto createDto) {
        Assert.notNull(createDto, "The CreateWhitelistIpDto should not be null");
        LOGGER.debug("Creating WhitelistIp using create dto - {}", createDto);
        final Organization organization = organizationService.getByUuid(createDto.getOrganizationUuid());
        final WhitelistIp whitelistIp = new WhitelistIp(createDto.getLabel(), createDto.getIp(), organization);
        final WhitelistIp savedWhitelistIp = whitelistIpRepository.save(whitelistIp);
        LOGGER.debug("Successfully created WhitelistIp using create dto - {}", createDto);
        return savedWhitelistIp;
    }

    @Override
    public Optional<WhitelistIp> findByUuid(final String uuid) {
        Assert.hasText(uuid, "The whitelist ip should not be null or empty");
        LOGGER.debug("Retrieving WhitelistIp for uuid - {}", uuid);
        final Optional<WhitelistIp> whitelistIpOptional = whitelistIpRepository.findByUuid(uuid);
        LOGGER.debug("Successfully retrieved optional of WhitelistIp for uuid - {}", uuid);
        return whitelistIpOptional;
    }

    @Override
    public List<WhitelistIp> getByOrganization(final String organizationUuid) {
        Assert.hasText(organizationUuid, "The organizationUuid should not be null or empty");
        LOGGER.debug("Retrieving whitelist ips by organizationUuid - {}", organizationUuid);
        final List<WhitelistIp> whitelistIps = whitelistIpRepository.findByOrganization_Uuid(organizationUuid);
        LOGGER.debug("Successfully retrieved whitelist ips by organizationUuid - {}", organizationUuid);
        return whitelistIps;
    }

    @Transactional
    @Override
    public WhitelistIp update(final UpdateWhitelistIpDto updateDto) {
        Assert.notNull(updateDto, "The UpdateWhitelistIpDto should not be null");
        LOGGER.debug("Updating WhitelistIp using update dto - {}", updateDto);
        final WhitelistIp updatedWhitelistIp = findByUuid(updateDto.getUuid()).map(whitelistIp -> {
            whitelistIp.setLabel(updateDto.getLabel());
            whitelistIp.setIp(updateDto.getIp());
            return whitelistIpRepository.save(whitelistIp);
        }).orElseThrow(() -> new WhitelistIpNotFoundException(updateDto.getUuid(), WhitelistIp.class));
        LOGGER.debug("Successfully updated WhitelistIp using update dto - {}", updateDto);
        return updatedWhitelistIp;
    }

    @Transactional
    @Override
    public void delete(final List<String> uuids) {
        Assert.notEmpty(uuids, "The whitelist ip uuids list should not be empty or null");
        LOGGER.debug("Deleting whitelist ips having uuids - {}", uuids);
        whitelistIpRepository.deleteByUuid(uuids);
        LOGGER.debug("Successfully deleted whitelist ips having uuids - {}", uuids);
    }
}