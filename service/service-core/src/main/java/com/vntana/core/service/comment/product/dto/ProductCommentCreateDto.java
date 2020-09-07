package com.vntana.core.service.comment.product.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 16:53
 */
public class ProductCommentCreateDto implements ServiceDto {
    
    private final String userUuid;
    private final String productUuid;
    private final String message;

    public ProductCommentCreateDto(final String userUuid, final String productUuid, final String message) {
        Assert.hasText(userUuid, "The user uuid should not be null or empty");
        Assert.hasText(productUuid, "The product uuid should not be null or empty");
        Assert.hasText(message, "The comment should not be null or empty");
        this.message = message;
        this.userUuid = userUuid;
        this.productUuid = productUuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCommentCreateDto)) {
            return false;
        }
        final ProductCommentCreateDto that = (ProductCommentCreateDto) o;
        return new EqualsBuilder()
                .append(message, that.message)
                .append(userUuid, that.userUuid)
                .append(productUuid, that.productUuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(message)
                .append(userUuid)
                .append(productUuid)
                .toHashCode();
    }

    public String getMessage() {
        return message;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getProductUuid() {
        return productUuid;
    }
}
