package com.vntana.core.service.comment.product.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 16:54
 */
public class ProductCommentUpdateDto implements ServiceDto {

    private final String uuid;
    private final String message;

    public ProductCommentUpdateDto(final String uuid, final String message) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        Assert.hasText(message, "The comment should not be null or empty");
        this.uuid = uuid;
        this.message = message;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCommentUpdateDto)) {
            return false;
        }
        final ProductCommentUpdateDto that = (ProductCommentUpdateDto) o;
        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(message, that.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(message)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("uuid", uuid)
                .append("message", message)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public String getMessage() {
        return message;
    }
}
