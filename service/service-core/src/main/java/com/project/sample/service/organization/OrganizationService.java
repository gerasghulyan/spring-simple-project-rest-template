package com.project.sample.service.organization;

import com.project.sample.commons.persistence.domain.organization.Organization;
import com.project.sample.service.organization.dto.CreateOrganizationDto;
import com.project.sample.service.organization.dto.GetAllOrganizationDto;
import com.project.sample.service.organization.dto.UpdateOrganizationDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Created by Geras Ghulyan.
 * Date: 10/8/19
 * Time: 5:15 PM
 */
public interface OrganizationService {

    Organization create(final CreateOrganizationDto dto);

    Organization getByUuid(final String uuid);

    Organization update(final UpdateOrganizationDto dto);

    Page<Organization> getAll(final GetAllOrganizationDto dto);

    Optional<Organization> findByUuid(final String uuid);
}
