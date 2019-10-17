package com.vntana.core.service.organization;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.service.organization.dto.CreateOrganizationDto;

import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:15 PM
 */
public interface OrganizationService {
    Organization create(final CreateOrganizationDto dto);

    Optional<Organization> findByUuid(final String uuid);

    Optional<Organization> findBySlug(final String slug);
}
