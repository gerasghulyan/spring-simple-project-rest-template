package com.vntana.core.model.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 6:43 PM
 */
public class CreateUserResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    private CreateUserResponseModel() {
        super();
    }

    public CreateUserResponseModel(final String uuid, final String organizationUuid) {
        super(uuid);
        this.organizationUuid = organizationUuid;
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
    }
}
