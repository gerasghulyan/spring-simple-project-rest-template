package com.vntana.core.service.user.dto;

import com.vntana.commons.service.dto.ServiceDto;
import com.vntana.core.domain.client.ClientOrganization;
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
    private final ClientOrganization clientOrganization;
    private final String fullName;
    private final String email;

    public GetOrCreateExternalUserDto(
            final String externalUuid,
            final Organization organization,
            final ClientOrganization clientOrganization,
            final String fullName,
            final String email) {
        hasText(externalUuid, "External uuid cannot be null or empty");
        notNull(organization, "Organization cannot be null or empty");
        this.externalUuid = externalUuid;
        this.organization = organization;
        this.clientOrganization = clientOrganization;
        this.fullName = fullName;
        this.email = email;
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
                .append(clientOrganization, that.clientOrganization)
                .append(fullName, that.fullName)
                .append(email, that.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(externalUuid)
                .append(organization)
                .append(clientOrganization)
                .append(fullName)
                .append(email)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("externalUuid", externalUuid)
                .append("organization", organization)
                .append("clientOrganization", clientOrganization)
                .append("fullName", fullName)
                .append("email", email)
                .toString();
    }

    public String getExternalUuid() {
        return externalUuid;
    }

    public Organization getOrganization() {
        return organization;
    }

    public ClientOrganization getClientOrganization() {
        return clientOrganization;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }
}
