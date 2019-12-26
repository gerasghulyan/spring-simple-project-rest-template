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

    @JsonProperty("imageId")
    private String imageId;

    public UpdateOrganizationRequest() {
        super();
    }

    public UpdateOrganizationRequest(final String uuid, final String name, final String imageId) {
        super(uuid);
        this.name = name;
        this.imageId = imageId;
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
                .append(imageId, that.imageId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(imageId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("imageId", imageId)
                .toString();
    }

    public String getName() {
        return name;
    }

    public String getImageId() {
        return imageId;
    }
}
