package com.vntana.core.persistence.catalog;

import com.vntana.core.domain.catalog.FacebookCatalogSetting;
import com.vntana.core.domain.organization.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 9:28 AM
 */
@Repository
public interface FacebookCatalogSettingRepository extends JpaRepository<FacebookCatalogSetting, Long> {

    Page<FacebookCatalogSetting> getAllByOrganizationAndRemovedIsNull(final Organization organization, final Pageable pageable);

    Optional<FacebookCatalogSetting> findByUuid(final String uuid);
}
