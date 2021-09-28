package com.vntana.core.service.catalog;

import com.vntana.core.domain.catalog.FacebookCatalogSetting;
import com.vntana.core.service.catalog.dto.CreateFacebookCatalogSettingDto;
import com.vntana.core.service.catalog.dto.GetByOrganizationFacebookCatalogSettingDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 9:21 AM
 */
public interface FacebookCatalogSettingService {

    FacebookCatalogSetting create(final CreateFacebookCatalogSettingDto dto);

    Page<FacebookCatalogSetting> getByOrganization(final GetByOrganizationFacebookCatalogSettingDto dto);

    void delete(final FacebookCatalogSetting facebookCatalogSetting);

    Optional<FacebookCatalogSetting> findByUuid(final String uuid);
}
