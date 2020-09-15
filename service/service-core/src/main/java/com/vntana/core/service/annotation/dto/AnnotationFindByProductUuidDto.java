package com.vntana.core.service.annotation.dto;

import com.vntana.commons.service.dto.AbstractPaginationAwareDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 10:12
 */
public class AnnotationFindByProductUuidDto extends AbstractPaginationAwareDto {

    private final String productUuid;

    public AnnotationFindByProductUuidDto(final int size, final String productUuid) {
        super(size);
        Assert.hasText(productUuid, "The product uuid should not be null or empty");
        this.productUuid = productUuid;
    }

    public AnnotationFindByProductUuidDto(final int page, final int size, final String productUuid) {
        super(page, size);
        Assert.hasText(productUuid, "The product uuid should not be null or empty");
        this.productUuid = productUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnnotationFindByProductUuidDto)) {
            return false;
        }
        final AnnotationFindByProductUuidDto that = (AnnotationFindByProductUuidDto) o;
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
}
