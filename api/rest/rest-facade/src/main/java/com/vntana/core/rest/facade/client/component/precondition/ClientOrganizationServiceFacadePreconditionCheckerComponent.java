package com.vntana.core.rest.facade.client.component.precondition;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/18/2020
 * Time: 1:02 PM
 */
public interface ClientOrganizationServiceFacadePreconditionCheckerComponent {

    SingleErrorWithStatus<ClientOrganizationErrorResponseModel> checkGetUserClientOrganizations(final String userUuid, final String organizationUuid);

    SingleErrorWithStatus<ClientOrganizationErrorResponseModel> checkGetByUserAndBulkOrganizations(final String userUuid, final List<String> organizationsUuids);
}