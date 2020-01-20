package com.vntana.core.model.organization.response.update.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractUuidAwareRequestModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 4:05 PM
 */
public class UpdateOrganizationRequest extends AbstractUuidAwareRequestModel implements ValidatableRequest<OrganizationErrorResponseModel> {

    @JsonProperty("name")
    private String name;

    @JsonProperty("imageBlobId")
    private String imageBlobId;

    public UpdateOrganizationRequest() {
        super();
    }

    public UpdateOrganizationRequest(final String uuid, final String name, final String imageBlobId) {
        super(uuid);
        this.name = name;
        this.imageBlobId = imageBlobId;
    }

    @Override
    public List<OrganizationErrorResponseModel> validate() {
        if (StringUtils.isEmpty(getUuid())) {
            return Collections.singletonList(OrganizationErrorResponseModel.MISSING_UUID);
        }
        if (StringUtils.isEmpty(name)) {
            return Collections.singletonList(OrganizationErrorResponseModel.MISSING_NAME);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpdateOrganizationRequest)) {
            return false;
        }
        final UpdateOrganizationRequest that = (UpdateOrganizationRequest) o;
        return new EqualsBuilder()
                .append(name, that.name)
                .append(imageBlobId, that.imageBlobId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(imageBlobId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("imageBlobId", imageBlobId)
                .toString();
    }

    public String getName() {
        return name;
    }

    public String getImageBlobId() {
        return imageBlobId;
    }
}
