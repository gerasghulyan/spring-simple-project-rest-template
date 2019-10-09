package com.vntana.core.service.client;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.service.client.dto.CreateClientOrganizationDto;

import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:15 PM
 */
public interface ClientOrganizationService {
    ClientOrganization create(final CreateClientOrganizationDto dto);

    Optional<ClientOrganization> findByUuid(final String uuid);

    Optional<ClientOrganization> findBySlug(final String slug);
}
