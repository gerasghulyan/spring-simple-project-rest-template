package com.vntana.core.model.organization.response.get;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

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

    @JsonProperty("imageBlobId")
    private String imageBlobId;

    @JsonProperty("created")
    private LocalDateTime created;

    public GetOrganizationByUuidResponseModel() {
        super();
    }

    public GetOrganizationByUuidResponseModel(final String uuid,
                                              final String name,
                                              final String slug,
                                              final String imageBlobId,
                                              final LocalDateTime created) {
        super(uuid);
        this.name = name;
        this.slug = slug;
        this.imageBlobId = imageBlobId;
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
                .append(imageBlobId, that.imageBlobId)
                .append(created, that.created)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(slug)
                .append(imageBlobId)
                .append(created)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("slug", slug)
                .append("imageBlobId", imageBlobId)
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

    public String getImageBlobId() {
        return imageBlobId;
    }

    public void setImageBlobId(final String imageBlobId) {
        this.imageBlobId = imageBlobId;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(final LocalDateTime created) {
        this.created = created;
    }
}
