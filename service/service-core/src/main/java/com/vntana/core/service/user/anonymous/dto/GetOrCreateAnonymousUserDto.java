package com.vntana.core.service.user.anonymous.dto;

import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.anonymousUser.AnonymousUserSource;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

/**
 * Created by Diana Gevorgyan
 * Date: 7/9/2021
 * Time: 3:50 PM
 */
public class GetOrCreateAnonymousUserDto {

    private final String externalUuid;
    private final AnonymousUserSource source;
    private final Organization organization;

    public GetOrCreateAnonymousUserDto(final String externalUuid, final AnonymousUserSource source, final Organization organization) {
        hasText(externalUuid, "External uuid cannot be null or empty");
        notNull(source, "Anonymous User Source cannot be null");
        notNull(organization, "Organization cannot be null or empty");
        this.externalUuid = externalUuid;
        this.source = source;
        this.organization = organization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetOrCreateAnonymousUserDto)) {
            return false;
        }
        final GetOrCreateAnonymousUserDto that = (GetOrCreateAnonymousUserDto) o;
        return new EqualsBuilder()
                .append(externalUuid, that.externalUuid)
                .append(source, that.source)
                .append(organization, that.organization)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(externalUuid)
                .append(source)
                .append(organization)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("externalUuid", externalUuid)
                .append("source", source)
                .append("organizationUuid", organization.getUuid())
                .toString();
    }

    public String getExternalUuid() {
        return externalUuid;
    }

    public AnonymousUserSource getSource() {
        return source;
    }

    public Organization getOrganization() {
        return organization;
    }
}
