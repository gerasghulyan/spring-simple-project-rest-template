package com.vntana.core.model.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Vardan Aivazian
 * Date: 10.09.2020
 * Time: 15:28
 **/
public class UpdateCommentResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("message")
    private String message;

    public UpdateCommentResponseModel() {
        super();
    }

    public UpdateCommentResponseModel(final String uuid, final String message) {
        super(uuid);
        this.message = message;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpdateCommentResponseModel)) {
            return false;
        }
        final UpdateCommentResponseModel that = (UpdateCommentResponseModel) o;
        return new EqualsBuilder()
                .append(message, that.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(message)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("message", message)
                .toString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
