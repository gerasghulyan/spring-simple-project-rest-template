package com.vntana.core.model.user.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.ResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 5:08 PM
 */
public class FindUserByEmailResponseModel implements ResponseModel {

    @JsonProperty("exists")
    private boolean exists;

    @JsonProperty("username")
    private String username;

    @JsonProperty("uuid")
    private String uuid;

    public FindUserByEmailResponseModel() {
    }

    public FindUserByEmailResponseModel(final boolean exists, final String username, final String uuid) {
        this.exists = exists;
        this.username = username;
        this.uuid = uuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FindUserByEmailResponseModel)) {
            return false;
        }
        final FindUserByEmailResponseModel that = (FindUserByEmailResponseModel) o;
        return new EqualsBuilder()
                .append(exists, that.exists)
                .append(username, that.username)
                .append(uuid, that.uuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(exists)
                .append(username)
                .append(uuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("exists", exists)
                .append("username", username)
                .append("uuid", uuid)
                .toString();
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(final boolean exists) {
        this.exists = exists;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }
}
