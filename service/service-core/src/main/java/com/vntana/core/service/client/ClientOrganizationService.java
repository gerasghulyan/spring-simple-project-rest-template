package com.vntana.core.service.client;

import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.service.client.dto.CreateClientOrganizationDto;
import com.vntana.core.service.client.dto.UpdateClientOrganizationDto;

import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:15 PM
 */
public interface ClientOrganizationService {
    ClientOrganization create(final CreateClientOrganizationDto dto);

    Optional<ClientOrganization> findByUuid(final String uuid);
    
    boolean existsByUuid(final String uuid);

    Optional<ClientOrganization> findBySlugAndOrganization(final String slug, final String organizationUuid);

    ClientOrganization getByUuid(final String uuid);

    ClientOrganization update(final UpdateClientOrganizationDto dto);
}
