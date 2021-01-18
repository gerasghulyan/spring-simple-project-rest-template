package com.vntana.core.model.organization.response.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import com.vntana.core.model.organization.response.get.model.OrganizationStatusModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

import static com.vntana.commons.utils.DataSanitizerUtils.mask;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 3:14 PM
 */
public class GetOrganizationByUuidResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("name")
    private String name;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("email")
    private String email;

    @JsonProperty("imageBlobId")
    private String imageBlobId;

    @JsonProperty("status")
    private OrganizationStatusModel status;

    @JsonProperty("created")
    private LocalDateTime created;

    public GetOrganizationByUuidResponseModel() {
        super();
    }

    public GetOrganizationByUuidResponseModel(
            final String uuid,
            final String name,
            final String slug,
            final String email,
            final String imageBlobId,
            final OrganizationStatusModel status,
            final LocalDateTime created) {
        super(uuid);
        this.name = name;
        this.slug = slug;
        this.email = email;
        this.imageBlobId = imageBlobId;
        this.status = status;
        this.created = created;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetOrganizationByUuidResponseModel)) {
            return false;
        }
        final GetOrganizationByUuidResponseModel that = (GetOrganizationByUuidResponseModel) o;
        return new EqualsBuilder()
                .append(name, that.name)
                .append(slug, that.slug)
                .append(email, that.email)
                .append(imageBlobId, that.imageBlobId)
                .append(status, that.status)
                .append(created, that.created)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(slug)
                .append(email)
                .append(imageBlobId)
                .append(status)
                .append(created)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("slug", slug)
                .append("email", email)
                .append("imageBlobId", mask(imageBlobId))
                .append("status", status)
                .append("created", created)
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

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getImageBlobId() {
        return imageBlobId;
    }

    public void setImageBlobId(final String imageBlobId) {
        this.imageBlobId = imageBlobId;
    }

    public OrganizationStatusModel getStatus() {
        return status;
    }

    public void setStatus(final OrganizationStatusModel status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(final LocalDateTime created) {
        this.created = created;
    }
}
