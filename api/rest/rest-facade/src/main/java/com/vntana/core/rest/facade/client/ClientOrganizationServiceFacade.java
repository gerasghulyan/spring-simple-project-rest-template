package com.vntana.core.rest.facade.client;

import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest;
import com.vntana.core.model.client.request.CreateClientOrganizationRequest;
import com.vntana.core.model.client.request.UpdateClientOrganizationRequest;
import com.vntana.core.model.client.response.CheckAvailableClientOrganizationSlugResultResponse;
import com.vntana.core.model.client.response.CreateClientOrganizationResultResponse;
import com.vntana.core.model.client.response.UpdateClientOrganizationResultResponse;
import com.vntana.core.model.client.response.get.GetAllOrganizationsResultResponse;
import com.vntana.core.model.client.response.get.GetClientOrganizationBySlugResultResponse;
import com.vntana.core.model.client.response.get.GetClientOrganizationResultResponse;
import com.vntana.core.model.user.response.UserClientBulkOrganizationResponse;
import com.vntana.core.model.user.response.UserClientOrganizationResponse;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:03 PM
 */
public interface ClientOrganizationServiceFacade {
    CheckAvailableClientOrganizationSlugResultResponse checkSlugAvailability(final CheckAvailableClientOrganizationSlugRequest request);

    CreateClientOrganizationResultResponse create(final CreateClientOrganizationRequest request);

    UserClientOrganizationResponse getUserClientOrganizations(final String userUuid, final String userOrganizationUuid);

    GetClientOrganizationResultResponse getByUuid(final String uuid);

    GetClientOrganizationBySlugResultResponse getBySlug(final String organizationUuid, final String slug);

    GetAllOrganizationsResultResponse getAll();

    UpdateClientOrganizationResultResponse update(final UpdateClientOrganizationRequest request);

    UserClientBulkOrganizationResponse getByUserAndBulkOrganizations(final String userUuid, final List<String> organizationsUuids);
}
