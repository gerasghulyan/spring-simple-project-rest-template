package com.vntana.core.model.invitation.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;

import java.util.List;

/**
 * Created by Diana Gevorgyan
 * Date: 11/10/20
 * Time: 1:52 PM
 */
public class CreateInvitationUserForClientsResponseModel implements ResponseModel {

    @JsonProperty("uuid")
    private List<String> uuids;

    public CreateInvitationUserForClientsResponseModel() {
    }

    public CreateInvitationUserForClientsResponseModel(final List<String> uuids) {
        this.uuids = uuids;
    }

    public List<String> getUuids() {
        return uuids;
    }

    public void setUuids(final List<String> uuids) {
        this.uuids = uuids;
    }
}
