package com.vntana.core.model.organization.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel;
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
public class CreateOrganizationRequest extends AbstractRequestModel implements ValidatableRequest<OrganizationErrorResponseModel> {

    @JsonProperty("name")
    private String name;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("userUuid")
    private String userUuid;

    @JsonProperty("imageId")
    private String imageId;

    public CreateOrganizationRequest() {
    }

    public CreateOrganizationRequest(final String name, final String slug, final String userUuid, final String imageId) {
        this.name = name;
        this.slug = slug;
        this.userUuid = userUuid;
        this.imageId = imageId;
    }

    @Override
    public List<OrganizationErrorResponseModel> validate() {
        final List<OrganizationErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(name)) {
            errors.add(OrganizationErrorResponseModel.MISSING_NAME);
        }
        if (StringUtils.isBlank(slug)) {
            errors.add(OrganizationErrorResponseModel.MISSING_SLUG);
        }
        if (StringUtils.isBlank(userUuid)) {
            errors.add(OrganizationErrorResponseModel.MISSING_USER_UUID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateOrganizationRequest)) {
            return false;
        }
        final CreateOrganizationRequest that = (CreateOrganizationRequest) o;
        return new EqualsBuilder()
                .append(name, that.name)
                .append(slug, that.slug)
                .append(userUuid, that.userUuid)
                .append(imageId, that.imageId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(slug)
                .append(userUuid)
                .append(imageId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("slug", slug)
                .append("userUuid", userUuid)
                .append("imageId", imageId)
                .toString();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(final String slug) {
        this.slug = slug;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(final String userUuid) {
        this.userUuid = userUuid;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(final String imageId) {
        this.imageId = imageId;
    }
}
