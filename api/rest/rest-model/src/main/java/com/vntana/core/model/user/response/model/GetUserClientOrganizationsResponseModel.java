package com.vntana.core.model.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import com.vntana.core.model.auth.response.UserRoleModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2019
 * Time: 2:58 PM
 */
public class GetUserClientOrganizationsResponseModel implements ResponseModel {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("imageId")
    private String imageId;

    @JsonProperty("role")
    private UserRoleModel role;

    public GetUserClientOrganizationsResponseModel() {
        super();
    }

    public GetUserClientOrganizationsResponseModel(final String uuid,
                                                   final String name,
                                                   final String imageId,
                                                   final UserRoleModel role) {
        this.uuid = uuid;
        this.name = name;
        this.imageId = imageId;
        this.role = role;
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
                .append(name, that.name)
                .append(imageId, that.imageId)
                .append(role, that.role)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(name)
                .append(imageId)
                .append(role)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("name", name)
                .append("imageId", imageId)
                .append("role", role)
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(final String imageId) {
        this.imageId = imageId;
    }

    public UserRoleModel getRole() {
        return role;
    }

    public void setRole(final UserRoleModel role) {
        this.role = role;
    }
}
