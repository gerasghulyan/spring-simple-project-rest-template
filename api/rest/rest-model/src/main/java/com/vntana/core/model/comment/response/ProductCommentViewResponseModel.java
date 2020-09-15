package com.vntana.core.model.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import com.vntana.core.model.user.response.get.model.UserViewResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:24 PM
 */
public class ProductCommentViewResponseModel extends AbstractUuidAwareResponseModel {

    @JsonProperty("productUuid")
    private String productUuid;

    @JsonProperty("message")
    private String message;

    @JsonProperty("owner")
    private UserViewResponseModel owner;

    @JsonProperty("taggedUsers")
    private List<UserViewResponseModel> taggedUsers;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("updated")
    private LocalDateTime updated;

    public ProductCommentViewResponseModel() {
        super();
    }

    public ProductCommentViewResponseModel(final String uuid, final String productUuid, final String message, final UserViewResponseModel owner, final List<UserViewResponseModel> taggedUsers, final LocalDateTime created, final LocalDateTime updated) {
        super(uuid);
        this.productUuid = productUuid;
        this.message = message;
        this.owner = owner;
        this.taggedUsers = taggedUsers;
        this.created = created;
        this.updated = updated;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCommentViewResponseModel)) {
            return false;
        }
        final ProductCommentViewResponseModel that = (ProductCommentViewResponseModel) o;
        return new EqualsBuilder()
                .append(productUuid, that.productUuid)
                .append(message, that.message)
                .append(owner, that.owner)
                .append(taggedUsers, that.taggedUsers)
                .append(created, that.created)
                .append(updated, that.updated)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(productUuid)
                .append(message)
                .append(owner)
                .append(taggedUsers)
                .append(created)
                .append(updated)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("productUuid", productUuid)
                .append("message", message)
                .append("owner", owner)
                .append("taggedUsers", taggedUsers)
                .append("created", created)
                .append("updated", updated)
                .toString();
    }

    public String getProductUuid() {
        return productUuid;
    }

    public void setProductUuid(final String productUuid) {
        this.productUuid = productUuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public UserViewResponseModel getOwner() {
        return owner;
    }

    public void setOwner(final UserViewResponseModel owner) {
        this.owner = owner;
    }

    public List<UserViewResponseModel> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(final List<UserViewResponseModel> taggedUsers) {
        this.taggedUsers = taggedUsers;
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
