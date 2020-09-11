package com.vntana.core.model.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Vardan Aivazian
 * Date: 10.09.2020
 * Time: 15:28
 **/
public class UpdateCommentResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("message")
    private String message;

    public UpdateCommentResponseModel() {
    }

    public UpdateCommentResponseModel(String uuid, String message) {
        super(uuid);
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UpdateCommentResponseModel that = (UpdateCommentResponseModel) o;

        return new EqualsBuilder()
                .append(message, that.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(message)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "UpdateCommentResponseModel{" +
                "message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
