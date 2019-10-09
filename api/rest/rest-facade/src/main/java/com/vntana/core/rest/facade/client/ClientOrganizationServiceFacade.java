package com.vntana.core.rest.facade.client;

import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest;
import com.vntana.core.model.client.response.CheckAvailableClientOrganizationSlugResultResponse;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:03 PM
 */
public interface ClientOrganizationServiceFacade {
    CheckAvailableClientOrganizationSlugResultResponse checkSlugAvailability(final CheckAvailableClientOrganizationSlugRequest request);
}
