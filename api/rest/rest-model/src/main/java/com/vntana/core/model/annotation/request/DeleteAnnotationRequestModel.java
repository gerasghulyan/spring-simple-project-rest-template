package com.vntana.core.model.annotation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.annotation.AnnotationErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 11:55
 */
public class DeleteAnnotationRequestModel extends AbstractRequestModel implements ValidatableRequest<AnnotationErrorResponseModel> {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("userUuid")
    private String userUuid;

    public DeleteAnnotationRequestModel() {
        super();
    }

    public DeleteAnnotationRequestModel(final String uuid, final String userUuid) {
        this.uuid = uuid;
        this.userUuid = userUuid;
    }

    @Override
    public List<AnnotationErrorResponseModel> validate() {
        final List<AnnotationErrorResponseModel> errors = new ArrayList<>();
        if (StringUtils.isBlank(uuid)) {
            errors.add(AnnotationErrorResponseModel.MISSING_UUID);
        }
        if (StringUtils.isBlank(userUuid)) {
            errors.add(AnnotationErrorResponseModel.MISSING_USER_UUID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeleteAnnotationRequestModel)) {
            return false;
        }
        final DeleteAnnotationRequestModel that = (DeleteAnnotationRequestModel) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(userUuid, that.userUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(userUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("uuid", uuid)
                .append("userUuid", userUuid)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(final String userUuid) {
        this.userUuid = userUuid;
    }
}
