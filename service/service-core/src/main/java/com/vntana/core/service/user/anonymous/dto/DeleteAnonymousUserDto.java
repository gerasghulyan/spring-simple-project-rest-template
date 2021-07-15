package com.vntana.core.service.user.anonymous.dto;

import com.vntana.core.domain.user.anonymousUser.AnonymousUserSource;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Diana Gevorgyan
 * Date: 7/9/2021
 * Time: 3:54 PM
 */
public class DeleteAnonymousUserDto {

    private final String externalUuid;
    private final AnonymousUserSource source;

    public DeleteAnonymousUserDto(final String externalUuid, final AnonymousUserSource source) {
        this.externalUuid = externalUuid;
        this.source = source;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeleteAnonymousUserDto)) {
            return false;
        }
        final DeleteAnonymousUserDto that = (DeleteAnonymousUserDto) o;
        return new EqualsBuilder()
                .append(externalUuid, that.externalUuid)
                .append(source, that.source)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(externalUuid)
                .append(source)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("externalUuid", externalUuid)
                .append("source", source)
                .toString();
    }

    public String getExternalUuid() {
        return externalUuid;
    }

    public AnonymousUserSource getSource() {
        return source;
    }
}
