package com.vntana.core.service.annotation.dto;

import com.vntana.commons.service.dto.ServiceDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 10:11
 */
public class AnnotationUpdateDto implements ServiceDto {
    
    private final String uuid;
    private final String text;
    private final Double d1;
    private final Double d2;
    private final Double d3;

    public AnnotationUpdateDto(final String uuid, final String text, final Double d1, final Double d2, final Double d3) {
        Assert.hasText(uuid, "The annotation uuid should not be null or empty");
        Assert.hasText(text, "The annotation text should not be null or empty");
        Assert.notNull(d1, "The annotation dimension d1 should not be null");
        Assert.notNull(d2, "The annotation dimension d2 should not be null");
        Assert.notNull(d3, "The annotation dimension d3 should not be null");
        this.uuid = uuid;
        this.text = text;
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnnotationUpdateDto)) {
            return false;
        }
        final AnnotationUpdateDto that = (AnnotationUpdateDto) o;
        return new EqualsBuilder()
                .append(d1, that.d1)
                .append(d2, that.d2)
                .append(d3, that.d3)
                .append(uuid, that.uuid)
                .append(text, that.text)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .append(text)
                .append(d1)
                .append(d2)
                .append(d3)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("uuid", uuid)
                .append("text", text)
                .append("d1", d1)
                .append("d2", d2)
                .append("d3", d3)
                .toString();
    }

    public String getUuid() {
        return uuid;
    }

    public String getText() {
        return text;
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
