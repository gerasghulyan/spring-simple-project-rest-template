package com.vntana.core.model.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * Created by Vardan Aivazian
 * Date: 10.09.2020
 * Time: 15:28
 **/
public class UpdateCommentResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("message")
    private String message;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("updated")
    private LocalDateTime updated;

    public UpdateCommentResponseModel() {
        super();
    }

    public UpdateCommentResponseModel(final String uuid, final String message, final LocalDateTime created, final LocalDateTime updated) {
        super(uuid);
        this.message = message;
        this.created = created;
        this.updated = updated;
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
                .append(created, that.created)
                .append(updated, that.updated)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(message)
                .append(created)
                .append(updated)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("message", message)
                .append("created", created)
                .append("updated", updated)
                .toString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(final LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(final LocalDateTime updated) {
        this.updated = updated;
    }
}
