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
public class CreateClientOrganizationRequest extends AbstractRequestModel implements ValidatableRequest<ClientOrganizationErrorResponseModel> {

    @JsonProperty("organizationUuid")
    private String organizationUuid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("imageBlobId")
    private String imageBlobId;

    public CreateClientOrganizationRequest() {
        super();
    }

    public CreateClientOrganizationRequest(final String organizationUuid,
                                           final String name,
                                           final String slug,
                                           final String imageBlobId) {
        super();
        this.organizationUuid = organizationUuid;
        this.name = name;
        this.slug = slug;
        this.imageBlobId = imageBlobId;
    }

    @Override
    public List<ClientOrganizationErrorResponseModel> validate() {
        final List<ClientOrganizationErrorResponseModel> errors = initializeNew();
        if (StringUtils.isBlank(name)) {
            errors.add(ClientOrganizationErrorResponseModel.MISSING_NAME);
        }
        if (StringUtils.isBlank(slug)) {
            errors.add(ClientOrganizationErrorResponseModel.MISSING_SLUG);
        }
        if (StringUtils.isBlank(getOrganizationUuid())) {
            errors.add(ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateClientOrganizationRequest)) {
            return false;
        }
        final CreateClientOrganizationRequest that = (CreateClientOrganizationRequest) o;
        return new EqualsBuilder()
                .append(organizationUuid, that.organizationUuid)
                .append(name, that.name)
                .append(slug, that.slug)
                .append(imageBlobId, that.imageBlobId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(organizationUuid)
                .append(name)
                .append(slug)
                .append(imageBlobId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("organizationUuid", organizationUuid)
                .append("name", name)
                .append("slug", slug)
                .append("imageBlobId", imageBlobId)
                .toString();
    }

    public String getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final String organizationUuid) {
        this.organizationUuid = organizationUuid;
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

    public String getImageBlobId() {
        return imageBlobId;
    }

    public void setImageBlobId(final String imageBlobId) {
        this.imageBlobId = imageBlobId;
    }
}
