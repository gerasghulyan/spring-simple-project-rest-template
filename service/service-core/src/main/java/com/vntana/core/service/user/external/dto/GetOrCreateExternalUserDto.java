package com.vntana.core.service.user.external.dto;

import com.vntana.commons.service.dto.ServiceDto;
import com.vntana.core.domain.organization.Organization;
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
public class GetOrCreateExternalUserDto implements ServiceDto {

    private final String externalUuid;
    private final Organization organization;

    public GetOrCreateExternalUserDto(final String externalUuid, final Organization organization) {
        hasText(externalUuid, "External uuid cannot be null or empty");
        notNull(organization, "Organization cannot be null or empty");
        this.externalUuid = externalUuid;
        this.organization = organization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetOrCreateExternalUserDto)) {
            return false;
        }
        final GetOrCreateExternalUserDto that = (GetOrCreateExternalUserDto) o;
        return new EqualsBuilder()
                .append(externalUuid, that.externalUuid)
                .append(organization, that.organization)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(externalUuid)
                .append(organization)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("externalUuid", externalUuid)
                .append("organizationUuid", organization.getUuid())
                .toString();
    }

    public String getExternalUuid() {
        return externalUuid;
    }
    
    public Organization getOrganization() {
        return organization;
    }
}
