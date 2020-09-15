package com.vntana.core.service.annotation.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 10:12
 */
public class AnnotationCreateDto implements ServiceDto {
    
    private final String userUuid;
    private final String productUuid;
    private final String text;
    private final Integer number;
    private final Double d1;
    private final Double d2;
    private final Double d3;

    public AnnotationCreateDto(final String userUuid, final String productUuid, final String text, final Integer number, final Double d1, final Double d2, final Double d3) {
        Assert.hasText(userUuid, "The user uuid should not be null or empty");
        Assert.hasText(productUuid, "The product uuid should not be null or empty");
        Assert.hasText(text, "The annotation text should not be null or empty");
        Assert.notNull(d1, "The annotation dimension d1 should not be null");
        Assert.notNull(d2, "The annotation dimension d2 should not be null");
        Assert.notNull(d3, "The annotation dimension d3 should not be null");
        Assert.notNull(number, "The annotation number should not be null");
        Assert.isTrue(number > 0, "The annotation number must be positive");
        this.userUuid = userUuid;
        this.productUuid = productUuid;
        this.text = text;
        this.number = number;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnnotationCreateDto)) {
            return false;
        }
        final AnnotationCreateDto that = (AnnotationCreateDto) o;
        return new EqualsBuilder()
                .append(number, that.number)
                .append(d1, that.d1)
                .append(d2, that.d2)
                .append(d3, that.d3)
                .append(userUuid, that.userUuid)
                .append(productUuid, that.productUuid)
                .append(text, that.text)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(userUuid)
                .append(productUuid)
                .append(text)
                .append(number)
                .append(d1)
                .append(d2)
                .append(d3)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("userUuid", userUuid)
                .append("productUuid", productUuid)
                .append("text", text)
                .append("number", number)
                .append("d1", d1)
                .append("d2", d2)
                .append("d3", d3)
                .toString();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public String getProductUuid() {
        return productUuid;
    }

    public String getText() {
        return text;
    }

    public Integer getNumber() {
        return number;
    }

    public Double getD1() {
        return d1;
    }

    public Double getD2() {
        return d2;
    }

    public Double getD3() {
        return d3;
    }
}
