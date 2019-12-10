package com.vntana.core.rest.facade.organization;

import com.vntana.core.model.organization.request.CheckAvailableOrganizationSlugRequest;
import com.vntana.core.model.organization.request.CreateOrganizationRequest;
import com.vntana.core.model.organization.response.CheckAvailableOrganizationSlugResultResponse;
import com.vntana.core.model.organization.response.CreateOrganizationResultResponse;
import com.vntana.core.model.organization.response.get.GetOrganizationResultResponse;
import com.vntana.core.model.user.response.UserOrganizationResponse;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:03 PM
 */
public interface OrganizationServiceFacade {
    CheckAvailableOrganizationSlugResultResponse checkSlugAvailability(final CheckAvailableOrganizationSlugRequest request);

    CreateOrganizationResultResponse create(final CreateOrganizationRequest request);

    UserOrganizationResponse getUserOrganizations(final String userUuid);

    GetOrganizationResultResponse getBySlug(final String slug);

    GetOrganizationResultResponse getByUuid(final String uuid);
}
