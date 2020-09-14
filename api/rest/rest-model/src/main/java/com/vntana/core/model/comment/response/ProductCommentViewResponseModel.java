package com.vntana.core.model.comment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;
import com.vntana.core.model.user.response.get.model.UserViewResponseModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    public ProductCommentViewResponseModel() {
        super();
    }

    public ProductCommentViewResponseModel(final String uuid,
                                           final String productUuid,
                                           final String message,
                                           final UserViewResponseModel owner,
                                           final List<UserViewResponseModel> taggedUsers) {
        super(uuid);
        this.productUuid = productUuid;
        this.message = message;
        this.owner = owner;
        this.taggedUsers = taggedUsers;
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
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(productUuid)
                .append(message)
                .append(owner)
                .append(taggedUsers)
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
}
