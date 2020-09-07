package com.vntana.core.service.comment.product.dto;

import com.vntana.commons.service.dto.AbstractPaginationAwareDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 12:19
 */
public class ProductCommentFindByProductUuidDto extends AbstractPaginationAwareDto {

    private final String productUuid;

    public ProductCommentFindByProductUuidDto(int size, String productUuid) {
        super(size);
        this.productUuid = productUuid;
    }

    public ProductCommentFindByProductUuidDto(int page, int size, String productUuid) {
        super(page, size);
        Assert.hasText(productUuid, "The product uuid should not be null or empty");
        this.productUuid = productUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCommentFindByProductUuidDto)) {
            return false;
        }
        final ProductCommentFindByProductUuidDto that = (ProductCommentFindByProductUuidDto) o;
        return new EqualsBuilder()
                .append(productUuid, that.productUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
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
}
