package com.vntana.core.service.organization;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.service.organization.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/8/19
 * Time: 5:15 PM
 */
public interface OrganizationService {
    Organization create(final CreateOrganizationDto dto);

    Organization createWithInvitation(final CreateOrganizationFromInvitationDto dto);

    Optional<Organization> findBySlug(final String slug);

    Page<Organization> getAll(final GetAllOrganizationDto dto);

    Organization getByUuid(final String uuid);

    boolean existsByUuid(final String uuid);

    Organization update(final UpdateOrganizationDto dto);

    String getOrganizationOwnerEmail(final String organizationUuid);

    List<Organization> getUserOrganizationsByUserUuidAndRole(final GetUserOrganizationsByUserUuidAndRoleDto dto);

    Long count();
}
