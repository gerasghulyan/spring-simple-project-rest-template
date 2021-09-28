package com.vntana.core.service.catalog.dto;

import com.vntana.commons.service.dto.AbstractPaginationAwareDto;
import com.vntana.core.domain.organization.Organization;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.springframework.util.Assert.notNull;

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 9:38 AM
 */
public class GetByOrganizationFacebookCatalogSettingDto extends AbstractPaginationAwareDto {

    private final Organization organization;

    public GetByOrganizationFacebookCatalogSettingDto(final int page, final int size, final Organization organization) {
        super(page, size);
        notNull(organization, "The organization should not be null");
        this.organization = organization;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GetByOrganizationFacebookCatalogSettingDto)) {
            return false;
        }
        final GetByOrganizationFacebookCatalogSettingDto that = (GetByOrganizationFacebookCatalogSettingDto) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(organization, that.organization)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(organization)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("organization", organization)
                .toString();
    }

    public Organization getOrganization() {
        return organization;
    }
}
