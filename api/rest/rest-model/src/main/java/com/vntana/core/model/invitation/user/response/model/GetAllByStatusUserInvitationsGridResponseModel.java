package com.vntana.core.model.invitation.user.response.model;

import com.vntana.commons.api.model.response.impl.AbstractGridResponseModel;

import java.util.List;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/13/2020
 * Time: 3:24 PM
 */
public class GetAllByStatusUserInvitationsGridResponseModel extends AbstractGridResponseModel<GetAllByStatusUserInvitationsResponseModel> {

    public GetAllByStatusUserInvitationsGridResponseModel() {
        super();
    }

    public GetAllByStatusUserInvitationsGridResponseModel(final int totalCount, final List<GetAllByStatusUserInvitationsResponseModel> items) {
        super(totalCount, items);
    }
}