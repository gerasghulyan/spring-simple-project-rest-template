package com.vntana.core.model.annotation.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vntana.commons.api.model.request.impl.AbstractPaginationAwareRequestModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 11:57
 */
public class FindAnnotationByFilterRequestModel extends AbstractPaginationAwareRequestModel {

    @JsonProperty("productUuid")
    private String productUuid;

    public FindAnnotationByFilterRequestModel() {
        super();
    }

    public FindAnnotationByFilterRequestModel(final Integer page, final Integer size, final String productUuid) {
        super(page, size);
        this.productUuid = productUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FindAnnotationByFilterRequestModel)) {
            return false;
        }
        final FindAnnotationByFilterRequestModel that = (FindAnnotationByFilterRequestModel) o;
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
