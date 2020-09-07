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
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ProductCommentCreateDto that = (ProductCommentCreateDto) o;

        return new EqualsBuilder()
                .append(userUuid, that.userUuid)
                .append(productUuid, that.productUuid)
                .append(message, that.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userUuid)
                .append(productUuid)
                .append(message)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "ProductCommentCreateDto{" +
                "userUuid='" + userUuid + '\'' +
                ", productUuid='" + productUuid + '\'' +
                ", message='" + message + '\'' +
                '}';
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
