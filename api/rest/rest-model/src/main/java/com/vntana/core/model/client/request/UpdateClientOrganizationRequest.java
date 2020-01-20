package com.vntana.core.model.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 10:40 AM
 */
public class UpdateClientOrganizationRequest extends AbstractRequestModel implements ValidatableRequest<ClientOrganizationErrorResponseModel> {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("imageBlobId")
    private String imageBlobId;

    public UpdateClientOrganizationRequest() {
        super();
    }

    public UpdateClientOrganizationRequest(
            final String uuid,
            final String name,
            final String imageBlobId) {
        super();
        this.uuid = uuid;
        this.name = name;
        this.imageBlobId = imageBlobId;
    }

    @Override
    public List<ClientOrganizationErrorResponseModel> validate() {
        final List<ClientOrganizationErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(getName())) {
            errors.add(ClientOrganizationErrorResponseModel.MISSING_NAME);
        }
        if (StringUtils.isBlank(getUuid())) {
            errors.add(ClientOrganizationErrorResponseModel.MISSING_UUID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpdateClientOrganizationRequest)) {
            return false;
        }
        final UpdateClientOrganizationRequest that = (UpdateClientOrganizationRequest) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(name, that.name)
                .append(imageBlobId, that.imageBlobId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(name)
                .append(imageBlobId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("organizationUuid", uuid)
                .append("name", name)
                .append("imageBlobId", imageBlobId)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getImageBlobId() {
        return imageBlobId;
    }

    public void setImageBlobId(final String imageBlobId) {
        this.imageBlobId = imageBlobId;
    }
}
