package com.vntana.core.service.catalog.dto;

import com.vntana.commons.service.dto.ServiceDto;
import com.vntana.core.domain.organization.Organization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 9:23 AM
 */
public class CreateFacebookCatalogSettingDto implements ServiceDto {

    private final String systemUserToken;
    private final String name;
    private final String catalogId;
    private final Organization organization;

    public CreateFacebookCatalogSettingDto(
            final String systemUserToken,
            final String name,
            final String catalogId, final Organization organization) {
        hasText(systemUserToken, "The systemUserToken should not be null or empty");
        hasText(name, "The name should not be null or empty");
        hasText(catalogId, "The catalogId should not be null or empty");
        notNull(organization, "The organization should not be null");
        this.systemUserToken = systemUserToken;
        this.name = name;
        this.catalogId = catalogId;
        this.organization = organization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateFacebookCatalogSettingDto)) {
            return false;
        }
        final CreateFacebookCatalogSettingDto that = (CreateFacebookCatalogSettingDto) o;
        return new EqualsBuilder()
                .append(systemUserToken, that.systemUserToken)
                .append(name, that.name)
                .append(catalogId, that.catalogId)
                .append(organization, that.organization)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(systemUserToken)
                .append(name)
                .append(catalogId)
                .append(organization)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("systemUserToken", systemUserToken)
                .append("name", name)
                .append("catalogId", catalogId)
                .append("organization", organization)
                .toString();
    }

    public String getSystemUserToken() {
        return systemUserToken;
    }

    public String getName() {
        return name;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public Organization getOrganization() {
        return organization;
    }
}