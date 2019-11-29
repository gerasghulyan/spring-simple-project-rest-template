package com.vntana.core.rest.facade.client;

import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest;
import com.vntana.core.model.client.request.CreateClientOrganizationRequest;
import com.vntana.core.model.client.response.CheckAvailableClientOrganizationSlugResultResponse;
import com.vntana.core.model.client.response.CreateClientOrganizationResultResponse;
import com.vntana.core.model.client.response.get.GetClientOrganizationResultResponse;
import com.vntana.core.model.user.response.UserClientOrganizationResponse;

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
}
