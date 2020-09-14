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
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:07 PM
 */
public class CreateProductCommentRequestModel extends AbstractRequestModel implements ValidatableRequest<CommentErrorResponseModel> {

    @JsonProperty("userUuid")
    private String userUuid;

    @JsonProperty("productUuid")
    private String productUuid;

    @JsonProperty("message")
    private String message;

    public CreateProductCommentRequestModel() {
        super();
    }

    public CreateProductCommentRequestModel(final String userUuid, final String productUuid, final String message) {
        this.userUuid = userUuid;
        this.productUuid = productUuid;
        this.message = message;
    }

    @Override
    public List<CommentErrorResponseModel> validate() {
        final List<CommentErrorResponseModel> errors = new ArrayList<>();
        if (StringUtils.isEmpty(userUuid)) {
            errors.add(CommentErrorResponseModel.MISSING_USER_UUID);
        }
        if (StringUtils.isEmpty(productUuid)) {
            errors.add(CommentErrorResponseModel.MISSING_PRODUCT_UUID);
        }
        if (StringUtils.isEmpty(message)) {
            errors.add(CommentErrorResponseModel.MISSING_MESSAGE);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateProductCommentRequestModel)) {
            return false;
        }
        final CreateProductCommentRequestModel that = (CreateProductCommentRequestModel) o;
        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .append(productUuid, that.productUuid)
                .append(message, that.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(productUuid)
                .append(message)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userUuid", userUuid)
                .append("productUuid", productUuid)
                .append("message", message)
                .toString();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(final String userUuid) {
        this.userUuid = userUuid;
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
}
