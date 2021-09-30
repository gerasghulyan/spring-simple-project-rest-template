package com.vntana.core.service.catalog.impl;

import com.vntana.core.domain.catalog.FacebookCatalogSetting;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.persistence.catalog.FacebookCatalogSettingRepository;
import com.vntana.core.service.catalog.FacebookCatalogSettingService;
import com.vntana.core.service.catalog.dto.CreateFacebookCatalogSettingDto;
import com.vntana.core.service.catalog.dto.GetByOrganizationFacebookCatalogSettingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 9:26 AM
 */
@Service
public class FacebookCatalogSettingServiceImpl implements FacebookCatalogSettingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookCatalogSettingServiceImpl.class);

    private final FacebookCatalogSettingRepository facebookCatalogSettingRepository;

    public FacebookCatalogSettingServiceImpl(final FacebookCatalogSettingRepository facebookCatalogSettingRepository) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.facebookCatalogSettingRepository = facebookCatalogSettingRepository;
    }

    @Transactional
    @Override
    public FacebookCatalogSetting create(final CreateFacebookCatalogSettingDto dto) {
        notNull(dto, "The CreateFacebookCatalogSettingDto should not be null");
        LOGGER.debug("Creating facebook catalog setting for dto - {}", dto);
        return facebookCatalogSettingRepository.save(
                new FacebookCatalogSetting(
                        dto.getSystemUserToken(),
                        dto.getName(),
                        dto.getCatalogId(),
                        dto.getOrganization()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<FacebookCatalogSetting> getByOrganization(final GetByOrganizationFacebookCatalogSettingDto dto) {
        notNull(dto, "The Organization should not be null");
        LOGGER.debug("Trying to find all facebook catalog settings for organization uuid - {}", dto.getOrganization().getUuid());
        return facebookCatalogSettingRepository.getAllByOrganizationAndRemovedIsNull(
                dto.getOrganization(),
                PageRequest.of(dto.getPage(), dto.getSize()));
    }

    @Transactional
    @Override
    public void delete(final FacebookCatalogSetting facebookCatalogSetting) {
        notNull(facebookCatalogSetting, "The facebook catalog setting should not be null");
        LOGGER.debug("Deleting facebook catalog setting for uuid - {}", facebookCatalogSetting.getUuid());
        facebookCatalogSettingRepository.delete(facebookCatalogSetting);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<FacebookCatalogSetting> findByUuid(final String uuid) {
        hasText(uuid, "The uuid cannot be null or empty");
        LOGGER.debug("Finding facebook catalog setting by uuid - {}", uuid);
        return facebookCatalogSettingRepository.findByUuid(uuid);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<FacebookCatalogSetting> getByCatalogId(final String catalogId, final Organization organization) {
        hasText(catalogId, "The catalog id cannot be null or empty");
        LOGGER.debug("Finding facebook catalog setting by catalogId - {}", catalogId);
        return facebookCatalogSettingRepository.findByCatalogIdAndOrganization(catalogId, organization);
    }
}
