package com.vntana.core.model.user.response.get.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2019
 * Time: 2:58 PM
 */
public class GetUserClientOrganizationsResponseModel implements ResponseModel {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("name")
    private String name;

    @JsonProperty("imageBlobId")
    private String imageBlobId;

    @JsonProperty("role")
    private UserRoleModel role;

    @JsonProperty("created")
    private LocalDateTime created;

    public GetUserClientOrganizationsResponseModel() {
        super();
    }

    public GetUserClientOrganizationsResponseModel(final String uuid, final String slug, final String name, final String imageBlobId, final UserRoleModel role, final LocalDateTime created) {
        this.uuid = uuid;
        this.slug = slug;
        this.name = name;
        this.imageBlobId = imageBlobId;
        this.role = role;
        this.created = created;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetUserClientOrganizationsResponseModel)) {
            return false;
        }
        final GetUserClientOrganizationsResponseModel that = (GetUserClientOrganizationsResponseModel) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(slug, that.slug)
                .append(name, that.name)
                .append(imageBlobId, that.imageBlobId)
                .append(role, that.role)
                .append(created, that.created)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(slug)
                .append(name)
                .append(imageBlobId)
                .append(role)
                .append(created)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("slug", slug)
                .append("name", name)
                .append("imageBlobId", imageBlobId)
                .append("role", role)
                .append("created", created)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(final String slug) {
        this.slug = slug;
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

    public UserRoleModel getRole() {
        return role;
    }

    public void setRole(final UserRoleModel role) {
        this.role = role;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(final LocalDateTime created) {
        this.created = created;
    }
}
