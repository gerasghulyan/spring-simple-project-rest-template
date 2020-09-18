package com.vntana.core.model.comment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.ValidatableRequest;
import com.vntana.commons.api.model.request.impl.AbstractPaginationAwareRequestModel;
import com.vntana.core.model.comment.CommentErrorResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:22 PM
 */
public class FindProductCommentByFilterRequestModel extends AbstractPaginationAwareRequestModel implements ValidatableRequest<CommentErrorResponseModel> {

    @JsonProperty("productUuid")
    private String productUuid;

    public FindProductCommentByFilterRequestModel() {
        super();
    }

    public FindProductCommentByFilterRequestModel(final Integer page, final Integer size, final String productUuid) {
        super(page, size);
        this.productUuid = productUuid;
    }

    @Override
    public List<CommentErrorResponseModel> validate() {
        final List<CommentErrorResponseModel> errors = new LinkedList<>();
        if (Objects.isNull(getPage())) {
            errors.add(CommentErrorResponseModel.MISSING_PAGE);
        }
        if (Objects.isNull(getSize())) {
            errors.add(CommentErrorResponseModel.MISSING_SIZE);
        }
        if (StringUtils.isEmpty(productUuid)) {
            errors.add(CommentErrorResponseModel.MISSING_PRODUCT_UUID);
        }
        return errors;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FindProductCommentByFilterRequestModel)) {
            return false;
        }
        final FindProductCommentByFilterRequestModel that = (FindProductCommentByFilterRequestModel) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(productUuid, that.productUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(productUuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("productUuid", productUuid)
                .toString();
    }

    public String getProductUuid() {
        return productUuid;
    }

    public void setProductUuid(final String productUuid) {
        this.productUuid = productUuid;
    }
}
