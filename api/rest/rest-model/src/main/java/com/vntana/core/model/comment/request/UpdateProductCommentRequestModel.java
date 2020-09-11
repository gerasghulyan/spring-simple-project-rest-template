package com.vntana.core.model.comment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractRequestModel;
import com.vntana.core.model.comment.CommentErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 10.09.2020
 * Time: 15:21
 **/
public class UpdateProductCommentRequestModel extends AbstractRequestModel implements ValidatableRequest<CommentErrorResponseModel> {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("userUuid")
    private String userUuid;

    @JsonProperty("message")
    private String message;

    public UpdateProductCommentRequestModel() {
        super();
    }

    public UpdateProductCommentRequestModel(final String uuid, final String userUuid, final String message) {
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.message = message;
    }

    @Override
    public List<CommentErrorResponseModel> validate() {
        List<CommentErrorResponseModel> errors = new ArrayList<>();
        if (StringUtils.isBlank(uuid)) {
            errors.add(CommentErrorResponseModel.MISSING_UUID);
        }
        if (StringUtils.isBlank(userUuid)) {
            errors.add(CommentErrorResponseModel.MISSING_USER_UUID);
        }
        if (StringUtils.isBlank(message)) {
            errors.add(CommentErrorResponseModel.MISSING_MESSAGE);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof UpdateProductCommentRequestModel)) return false;

        final UpdateProductCommentRequestModel that = (UpdateProductCommentRequestModel) o;

        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(userUuid, that.userUuid)
                .append(message, that.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(uuid)
                .append(userUuid)
                .append(message)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("userUuid", userUuid)
                .append("message", message)
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

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
