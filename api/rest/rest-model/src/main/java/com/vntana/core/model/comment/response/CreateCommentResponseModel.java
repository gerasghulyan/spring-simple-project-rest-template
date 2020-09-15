package com.vntana.core.model.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:08 PM
 */
public class CreateCommentResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("created")
    private LocalDateTime created;
    
    public CreateCommentResponseModel() {
        super();
    }

    public CreateCommentResponseModel(final String uuid, final LocalDateTime created) {
        super(uuid);
        this.created = created;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateCommentResponseModel)) {
            return false;
        }
        final CreateCommentResponseModel that = (CreateCommentResponseModel) o;
        return new EqualsBuilder()
                .append(created, that.created)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(created)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("created", created)
                .toString();
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(final LocalDateTime created) {
        this.created = created;
    }
}
