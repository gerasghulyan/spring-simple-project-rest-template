package com.vntana.core.model.organization.request;

import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractPaginationAwareRequestModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/13/2020
 * Time: 3:30 PM
 */
public class GetAllOrganizationsRequest extends AbstractPaginationAwareRequestModel implements ValidatableRequest<InvitationUserErrorResponseModel> {

    public GetAllOrganizationsRequest() {
        super();
    }

    public GetAllOrganizationsRequest(final Integer page, final Integer size) {
        super(page, size);
    }

    @Override
    public List<InvitationUserErrorResponseModel> validate() {
        return Collections.emptyList();
    }
}