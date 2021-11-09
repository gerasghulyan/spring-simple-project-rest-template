package com.project.sample.commons.service.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Geras Ghulyan
 * Date: 8/17/20
 * Time: 7:12 PM
 */
public class AbstractPaginationAwareDto implements ServiceDto {

    private final int page;
    private final int size;

    public AbstractPaginationAwareDto(final int size) {
        this(0, size);
    }

    public AbstractPaginationAwareDto(final int page, final int size) {
        if (page < 0)
            throw new IllegalArgumentException("The pagination page should not be negative");
        if (size < 0)
            throw new IllegalArgumentException("The pagination size should not be negative");
        this.page = page;
        this.size = size;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractPaginationAwareDto)) {
            return false;
        }
        final AbstractPaginationAwareDto that = (AbstractPaginationAwareDto) o;
        return new EqualsBuilder()
                .append(page, that.page)
                .append(size, that.size)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(page)
                .append(size)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("page", page)
                .append("size", size)
                .toString();
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
