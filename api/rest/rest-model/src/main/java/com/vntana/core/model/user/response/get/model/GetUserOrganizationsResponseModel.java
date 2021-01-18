package com.vntana.core.model.user.response.get.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

import static com.vntana.commons.utils.DataSanitizerUtils.mask;

/**
 * Created by Geras Ghulyan
 * Date: 04.11.19
 * Time: 16:33
 */
public class GetUserOrganizationsResponseModel implements ResponseModel {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("slug")
    private String slug;

    @JsonProperty("name")
    private String name;

    @JsonProperty("role")
    private UserRoleModel role;

    @JsonProperty("imageBlobId")
    private String imageBlobId;

    @JsonProperty("created")
    private LocalDateTime created;

    public GetUserOrganizationsResponseModel() {
    }

    public GetUserOrganizationsResponseModel(final String uuid, final String slug, final String name, final UserRoleModel role, final String imageBlobId, final LocalDateTime created) {
        this.uuid = uuid;
        this.slug = slug;
        this.name = name;
        this.role = role;
        this.imageBlobId = imageBlobId;
        this.created = created;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetUserOrganizationsResponseModel)) {
            return false;
        }
        final GetUserOrganizationsResponseModel that = (GetUserOrganizationsResponseModel) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(slug, that.slug)
                .append(name, that.name)
                .append(role, that.role)
                .append(imageBlobId, that.imageBlobId)
                .append(created, that.created)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(slug)
                .append(name)
                .append(role)
                .append(imageBlobId)
                .append(created)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("slug", slug)
                .append("name", name)
                .append("role", role)
                .append("imageBlobId", mask(imageBlobId))
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

    public UserRoleModel getRole() {
        return role;
    }

    public void setRole(final UserRoleModel role) {
        this.role = role;
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
